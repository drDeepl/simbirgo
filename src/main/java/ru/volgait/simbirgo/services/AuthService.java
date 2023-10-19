package ru.volgait.simbirgo.services;

import ru.volgait.simbirgo.dto.AccountDTO;
import ru.volgait.simbirgo.dto.SignupRequest;

public interface AuthService {
    AccountDTO createAccount(SignupRequest signupDTO);
}
