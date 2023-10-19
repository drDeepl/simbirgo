package ru.volgait.simbirgo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.volgait.simbirgo.models.Account;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByUsername(String username);



}
