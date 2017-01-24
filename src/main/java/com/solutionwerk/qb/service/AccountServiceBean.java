package com.solutionwerk.qb.service;

import com.solutionwerk.qb.model.Account;
import com.solutionwerk.qb.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * The AccountServiceBean encapsulates all business behaviors for operations on
 * the Account entity model and some related entities such as Role.
 *
 * @author Piyush Ramavat
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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
    public Collection<Account> findAll() {
        LOGGER.info("findAll");
        return accountRepository.findAll();
    }

    @Override
    @Cacheable(
            value = "accounts",
            key = "#username")
    public Account findByUsername(String username) {
        LOGGER.info("findByUsername");
        return accountRepository.findByUsername(username);
    }

    @Override
    @Cacheable(
            value = "accounts",
            key = "#id")
    public Account findOne(Long id) {
        LOGGER.info("findOne id:{}", id);
        return accountRepository.findOne(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "accounts",
            key = "#result.id")
    public Account create(Account account) {
        LOGGER.info("create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (account.getId() != null) {
            // Cannot create Account with specified ID value
            LOGGER.error("Attempted to create an Account, but id attribute was not null.");
            throw new EntityExistsException("The id attribute must be null to persist a new entity.");
        }

        return accountRepository.save(account);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "accounts",
            key = "#result.id")
    public Account update(Account account) {
        LOGGER.info("update id:{}", account.getId());

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Account accountToUpdate = findOne(account.getId());
        if (accountToUpdate == null) {
            // Cannot update Account that hasn't been persisted
            LOGGER.error("Attempted to update a Account, but the entity does not exist.");
            throw new NoResultException("Requested entity not found.");
        }

        // TODO: Try to save Account passed in parameter directly. keep the check and ensure other dependent entities like Role and Profiles are intact or updated according to new one.
        accountToUpdate.setUsername(account.getUsername());
        return accountRepository.save(accountToUpdate);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CacheEvict(
            value = "accounts",
            key = "#id")
    public void delete(Long id) {
        LOGGER.info("delete id:{}", id);

        accountRepository.delete(id);
    }

    @Override
    @CacheEvict(
            value = "accounts",
            allEntries = true)
    public void evictCache() {
        LOGGER.info("evictCache");
    }
}
