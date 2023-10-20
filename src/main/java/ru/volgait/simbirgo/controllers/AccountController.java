package ru.volgait.simbirgo.controllers;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.volgait.simbirgo.dto.AccountDTO;
import ru.volgait.simbirgo.dto.AuthenticationRequest;
import ru.volgait.simbirgo.dto.AuthenticationResponse;
import ru.volgait.simbirgo.dto.SignupRequest;
import ru.volgait.simbirgo.filters.JwtRequestFilter;
import ru.volgait.simbirgo.services.AuthService;
import ru.volgait.simbirgo.services.jwt.AccountDetailsServiceImpl;
import ru.volgait.simbirgo.utils.JwtUtil;

import java.io.IOException;
import java.security.Principal;



@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private final AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountDetailsServiceImpl accountDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@RequestBody SignupRequest signupRequest){

            try {
                AccountDTO createdAccount = authService.createAccount(signupRequest);
                if (createdAccount == null) {
                    return new ResponseEntity<>("не удалось создать пользователя. попробуйте снова чуть позже", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
            }
            catch(DataIntegrityViolationException e){
                return new ResponseEntity<>("Полльзователь с таким именем уже существует", HttpStatus.CREATED);
            }
    }

    @PostMapping("/signin")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                                            HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Неправильныое имя пользователя или пароль");
        }catch(DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Пользователя с таким именем не существует");
            return null;
        }
        final UserDetails accountDetails = accountDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        boolean isAdminAccount = accountDetails.getAuthorities().toArray()[0].toString() == "ROLE_ADMIN";

        final String jwt = jwtUtil.generateToken(accountDetails.getUsername(), isAdminAccount);
        return new AuthenticationResponse(jwt);

    }




    @GetMapping("/me")
    public String userData(Principal principal){
        LOGGER.info("api/acccount/me/userData");
        return principal.getName();
    }

}
