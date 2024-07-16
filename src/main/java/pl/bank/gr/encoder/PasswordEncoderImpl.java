package pl.bank.gr.encoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.password4j.Password;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

	@Value("bankgrpepper")
	private String pepper;

	@Override
	public String encode(String text) {
		return Password
				.hash(text)
				.addPepper()
				.withBcrypt()
				.getResult();
	}

	@Override
	public boolean check(String text, String hashed) {
		return Password
				.check(text, hashed)
				.addPepper()
				.withBcrypt();
	}

}
