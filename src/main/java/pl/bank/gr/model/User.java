package pl.bank.gr.model;

import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.bank.gr.validation.ADULT;
import pl.bank.gr.validation.PESEL;

@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
public class User {
	
	@NotBlank(message = "UserFirstName cannot be blank")
	@Size(min = 2, max = 64, message = "UserFirstName must be between 2-64 characters")
	private final String userFirstName;
	@NotBlank(message = "UserLastName cannot be blank")
	@Size(min = 2, max = 64, message = "UserLastName must be between 2-64 characters")
	private final String userLastName;
	@PESEL
	@ADULT
	private final String pesel;
	@NotNull
	@PositiveOrZero
	@NumberFormat
	private final double balancePLN;
	@NotNull
	@PositiveOrZero
	@NumberFormat
	private final double balanceUSD;
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 64, message = "Password must be between 8-64 characters")
	private final String password;
	
	public User withPassword( String newpassword )
	{
		return User
				.builder()
				.userFirstName(userFirstName)
				.userLastName(userLastName)
				.pesel(pesel)
				.balancePLN(balancePLN)
				.balanceUSD(balanceUSD)
				.password(newpassword)
				.build();
	}
	
	public User withBalancePLN( double newbalancePLN )
	{
		return User
				.builder()
				.userFirstName(userFirstName)
				.userLastName(userLastName)
				.pesel(pesel)
				.balancePLN(newbalancePLN)
				.balanceUSD(balanceUSD)
				.password(password)
				.build();
	}
	
	public User withBalanceUSD( double newbalanceUSD )
	{
		return User
				.builder()
				.userFirstName(userFirstName)
				.userLastName(userLastName)
				.pesel(pesel)
				.balancePLN(balancePLN)
				.balanceUSD(newbalanceUSD)
				.password(password)
				.build();
	}
}