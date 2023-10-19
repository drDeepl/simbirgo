package ru.volgait.simbirgo.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private Boolean isAdmin;
    private String password;
    private Double balance;
}
