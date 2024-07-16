package pl.bank.gr.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import pl.bank.gr.model.dto.ExchangeRateDto;
import pl.bank.gr.service.dto.ExternalExchangeRateDto;

@Service
@Slf4j
public class ExchangeRateProvider {

	private RestTemplate restTemplate = new RestTemplate();
	
	public ExchangeRateDto getExchangeRate() 
	{
		ExternalExchangeRateDto exchangeRateDto = restTemplate.getForObject("https://api.nbp.pl/api/exchangerates/rates/a/usd/", ExternalExchangeRateDto.class);
		log.info("Exchange rate call - CODE: " + exchangeRateDto.getCode() + " RATE: " + ( exchangeRateDto.getRates().isEmpty() ? "EMPTY" : exchangeRateDto.getRates().get(0).getMid() ) );
		return ExchangeRateDto.builder()
				.currencyCode(exchangeRateDto.getCode())
				.rateValue(exchangeRateDto.getRates().get(0).getMid())
				.build();
	}
}
