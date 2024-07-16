package pl.bank.gr.model.dto;

import pl.bank.gr.model.User;


public record CreateUserDto(String firstName, String lastName, String pesel, double initialBalancePLN, String password) {

	public User toUser() { 
		return User
				.builder()
				.userFirstName(firstName)
				.userLastName(lastName)
				.pesel(pesel)
				.balancePLN(initialBalancePLN)
				.password(password).build();
	}
}
