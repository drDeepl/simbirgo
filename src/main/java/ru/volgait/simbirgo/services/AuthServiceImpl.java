package ru.volgait.simbirgo.services;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.volgait.simbirgo.dto.AccountDTO;
import ru.volgait.simbirgo.dto.SignupRequest;
import ru.volgait.simbirgo.models.Account;
import ru.volgait.simbirgo.repositories.AccountRepository;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public AccountDTO createAccount(SignupRequest signupRequest) {
        Account account = new Account();
        account.setAdmin(signupRequest.getIsAdmin());
        account.setUsername(signupRequest.getUsername());
        account.setBalance(signupRequest.getBalance());
        account.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        Account createdAccount  = accountRepository.save(account);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(createdAccount.getUsername());
        accountDTO.setIsAdmin(createdAccount.isAdmin());
        accountDTO.setBalance(createdAccount.getBalance());
        return accountDTO;
    }
}
