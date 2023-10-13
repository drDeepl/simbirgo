package ru.volgait.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volgait.simbirgo.models.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByUsername(String username);
}
