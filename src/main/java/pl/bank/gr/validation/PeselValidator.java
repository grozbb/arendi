package pl.bank.gr.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeselValidator implements ConstraintValidator<PESEL, String>{

	@Override
	public boolean isValid(String pesel, ConstraintValidatorContext context) {
		
		return PeselUtils.isPeselValid(pesel);
	}
}
