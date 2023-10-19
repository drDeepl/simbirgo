package ru.volgait.simbirgo.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String username;
    private Boolean isAdmin;
    private Double balance;
}
