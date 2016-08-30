package com.solutionwerk.qb.service;

import com.solutionwerk.qb.model.Account;
import com.solutionwerk.qb.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The AccountServiceBean encapsulates all business behaviors for operations on
 * the Account entity model and some related entities such as Role.
 *
 * @author Piyush Ramavat
 */
@Service
public class AccountServiceBean implements AccountService {

    /**
     * The Logger for this class.
     */
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * The Spring Data repository for Account entities.
     */
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findByUsername(String username) {
        LOGGER.info("> findByUsername");
        Account account = accountRepository.findByUsername(username);

        LOGGER.info("< findByUsername");
        return account;
    }

}
