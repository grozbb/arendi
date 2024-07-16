package pl.bank.gr.validation;

import java.time.*;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<ADULT, String> {

	@Override
	public boolean isValid(String pesel, ConstraintValidatorContext context) {

		String birthDayString = PeselUtils.getBirthDayString(pesel);
		if ( birthDayString != null )
		{
			LocalDate dateOfBirth = LocalDate.parse(birthDayString);
			return calcAge(dateOfBirth) >= 18;
		}
		else
		{
			return false;
		}
	}

	static int calcAge(LocalDate dateOfBirth) {
		LocalDate currentDate = LocalDate.now();
		int age = Period.between(dateOfBirth, currentDate).getYears();
		return age;
	}
}