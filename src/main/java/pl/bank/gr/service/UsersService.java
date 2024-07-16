package pl.bank.gr.service;

import java.util.Collection;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.bank.gr.encoder.PasswordEncoder;
import pl.bank.gr.model.User;
import pl.bank.gr.model.dto.CreateUserDto;
import pl.bank.gr.model.dto.ExchangeRateDto;
import pl.bank.gr.repository.UserRepository;
import pl.bank.gr.utils.Const;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	@Autowired
	private ExchangeRateProvider exchangeRateProvider;
	
	public User register(CreateUserDto user) 
	{
		return userRepository
				.create(
						user
						.toUser()
						.withPassword(passwordEncoder.encode(user.password())));
	}
	
	public Collection<User> getAll()
	{
		return userRepository.getAll();
	}
	
	public Optional<User> getByPesel(String pesel)
	{
		return userRepository.findByPesel(pesel);
	}
	
	public User exchangeAll(String pesel, String srcCurrency, String dstCurrency)
	{
		User user = userRepository.findByPesel(pesel).get();
		
		if( StringUtils.isNotBlank(srcCurrency) && StringUtils.isNotBlank(dstCurrency) && Const.CURRENCIES.contains(srcCurrency.toUpperCase()) && Const.CURRENCIES.contains(dstCurrency.toUpperCase()))
		{
			ExchangeRateDto dto = exchangeRateProvider.getExchangeRate();
			if ( Const.CURRENCY_PLN.equals(srcCurrency) && Const.CURRENCY_USD.equals(dstCurrency) && user.getBalancePLN() > 0.0 ) 
			{
				double newBalanceUSD = user.getBalancePLN() / dto.getRateValue();
				log.info("NEW USD: " + newBalanceUSD + "=" + user.getBalancePLN() + " / " + dto.getRateValue() );
				userRepository.update(user
						.withBalancePLN(0.0)
						.withBalanceUSD(user.getBalanceUSD() + newBalanceUSD));
			}
			else if ( Const.CURRENCY_USD.equals(srcCurrency) && Const.CURRENCY_PLN.equals(dstCurrency) && user.getBalanceUSD() > 0.0 ) 
			{
				double newBalancePLN = user.getBalanceUSD() * dto.getRateValue();
				log.info("NEW PLN: " + newBalancePLN + "=" + user.getBalanceUSD() + " * " + dto.getRateValue() );
				userRepository.update(user
						.withBalanceUSD(0.0)
						.withBalancePLN(user.getBalancePLN() + newBalancePLN));
			}
		}
		else 
		{
			throw new IllegalStateException("Bad currency values: " + srcCurrency + " " + dstCurrency);
		}
		
		return userRepository.findByPesel(user.getPesel()).get();
	}
	
	public User exchangeAmount(String pesel, String srcCurrency, double srcAmount, String dstCurrency )
	{
		User user = userRepository.findByPesel(pesel).get();
		if( StringUtils.isNotBlank(srcCurrency) && StringUtils.isNotBlank(dstCurrency) && Const.CURRENCIES.contains(srcCurrency.toUpperCase()) && Const.CURRENCIES.contains(dstCurrency.toUpperCase()))
		{
			ExchangeRateDto dto = exchangeRateProvider.getExchangeRate();
			if (Const.CURRENCY_PLN.equals(srcCurrency) && Const.CURRENCY_USD.equals(dstCurrency) && user.getBalancePLN() > 0.0) 
			{
				if( srcAmount <= user.getBalancePLN() )
				{	
					double newBalanceUSD = srcAmount / dto.getRateValue();
					log.info("NEW USD: " + newBalanceUSD + "=" + srcAmount + " / " + dto.getRateValue() );
					userRepository.update(user
							.withBalancePLN(user.getBalancePLN() - srcAmount)
							.withBalanceUSD(user.getBalanceUSD() + newBalanceUSD));
				}
				else
				{
					throw new IllegalStateException("Not enough amount to exchange: " + srcAmount + " " + user.getBalancePLN());
				}
			}
			else if ( Const.CURRENCY_USD.equals(srcCurrency) && Const.CURRENCY_PLN.equals(dstCurrency) && user.getBalanceUSD() > 0.0) 
			{
				if( srcAmount <= user.getBalanceUSD() )
				{
					double newBalancePLN = srcAmount * dto.getRateValue();
					log.info("NEW PLN: " + newBalancePLN + "=" + srcAmount + " * " + dto.getRateValue() );
					userRepository.update(user
							.withBalanceUSD(user.getBalanceUSD() - srcAmount)
							.withBalancePLN(user.getBalancePLN() + newBalancePLN));
				}
				else
				{
					throw new IllegalStateException("Not enough amount to exchange: " + srcAmount + " " + user.getBalanceUSD());
				}
			}
		}
		return userRepository.findByPesel(user.getPesel()).get();
	}
}
