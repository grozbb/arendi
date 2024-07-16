package pl.bank.gr;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pl.bank.gr.model.User;
import pl.bank.gr.model.dto.CreateUserDto;
import pl.bank.gr.service.UsersService;

@RestController
@RequestMapping("api/bankacc")
public class BankgrApplicationApi {

	@Autowired
	private UsersService userService;

	@PostMapping("/register")
	User newUser(@Valid @RequestBody CreateUserDto newUserDto) {
		User newUser = userService.register(newUserDto);
		return newUser;
	}

	@GetMapping("/all")
	public Collection<User> getAll() {
		return userService.getAll();
	}

	@GetMapping("")
	public User getByPesel(@RequestParam String pesel) {
		return userService.getByPesel(pesel).orElseThrow();
	}

	@GetMapping("/exchangeall")
	public User exchangeAll(@RequestParam String pesel, @RequestParam String srcCurrency, @RequestParam String dstCurrency) {
		return userService.exchangeAll(pesel, srcCurrency, dstCurrency);
	}

	@GetMapping("/exchangeamount")
	public User exchangeAmount(@RequestParam String pesel, @RequestParam String srcCurrency, @RequestParam double srcAmount, @RequestParam String dstCurrency) {
		return userService.exchangeAmount(pesel, srcCurrency, srcAmount, dstCurrency);
	}
}
