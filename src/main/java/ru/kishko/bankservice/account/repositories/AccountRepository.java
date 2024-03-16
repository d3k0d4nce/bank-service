package ru.kishko.bankservice.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.bankservice.account.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
