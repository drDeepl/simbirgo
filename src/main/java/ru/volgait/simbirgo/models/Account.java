package ru.volgait.simbirgo.models;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String passwordHash;

    private boolean isAdmin;

    private Double balance;

    public String getUsername(){
        return username;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    public Double getBalance(){
        return balance;
    }



}
