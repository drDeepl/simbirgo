package ru.volgait.simbirgo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import ru.volgait.simbirgo.models.Account;
import ru.volgait.simbirgo.models.Role;
import ru.volgait.simbirgo.repositories.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
public class SimbirgoApplication {
	@Autowired
	Environment env;
	@Autowired
	private AccountRepository accountRepository;



	public static void main(String[] args) {
		SpringApplication.run(SimbirgoApplication.class, args);

	}
}
