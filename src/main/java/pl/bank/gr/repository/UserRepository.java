package pl.bank.gr.repository;

import java.util.Collection;
import java.util.Optional;

import pl.bank.gr.model.User;

public interface UserRepository {
	User create(User user);
	
	User update(User user);

	Optional<User> findByPesel(String pesel);
	
	Collection<User> getAll();
}
