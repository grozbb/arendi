package pl.bank.gr.encoder;

public interface PasswordEncoder {
	String encode(String text);
	boolean check(String text, String hashed);
}
