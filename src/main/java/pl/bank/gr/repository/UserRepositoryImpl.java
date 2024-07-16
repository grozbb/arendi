package pl.bank.gr.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import pl.bank.gr.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final Map<String, User> users = new HashMap<>();

	@Override
	public User create(User user) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		if (!users.isEmpty() && users.containsKey(user.getPesel())) {
			throw new IllegalStateException("User with pesel: " + user.getPesel() + " already exists.");
		}
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
		
		
		users.put(user.getPesel(), user);
		return user;
	}
	
	public User update(User user) {

		users.put(user.getPesel(), user);
		return user;
	}

	@Override
	public Optional<User> findByPesel(String pesel) {
		return Optional.ofNullable(users.getOrDefault(pesel, null));
	}

	@Override
	public Collection<User> getAll() {
		return users.values();
	}

}
