package com.marscolony.bankacc.query.api.repositories;

import com.marscolony.bankacc.core.api.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<BankAccount, String> {
}
