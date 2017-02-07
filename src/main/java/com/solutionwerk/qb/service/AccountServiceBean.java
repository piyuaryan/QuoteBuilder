package com.solutionwerk.qb.service;

import com.solutionwerk.qb.model.Account;
import com.solutionwerk.qb.model.Profile;
import com.solutionwerk.qb.model.Role;
import com.solutionwerk.qb.model.network.User;
import com.solutionwerk.qb.repository.AccountRepository;
import com.solutionwerk.qb.repository.ProfileRepository;
import com.solutionwerk.qb.repository.RoleRepository;
import com.solutionwerk.qb.util.RequestContext;
import org.joda.time.DateTime;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProfileRepository profileRepository;

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

        if (RequestContext.getUsername() != null && RequestContext.getUsername().equals(accountRepository.findOne(id).getUsername())) {
            LOGGER.error("Attempted to Delete an Account through which user has logged in.");
            throw new UnsupportedOperationException("Cannot Delete Logged in User");
        }

        accountRepository.delete(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "accounts",
            key = "#result.id")
    public Account create(User user) {
        LOGGER.info("create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (user.getAccount().getId() != null) {
            // Cannot create Account with specified ID value
            LOGGER.error("Attempted to create an Account, but id attribute was not null.");
            throw new EntityExistsException("The id attribute must be null to persist a new entity.");
        }

        Account account = accountRepository.saveAndFlush(user.getAccount());

        account = updateUserRoleAndProfile(user, account);

        return account;
    }

    private Account updateUserRoleAndProfile(User user, Account account) {
        boolean update = false;
        if (user.getRoles() != null) {
            List<Role> userRoles = new ArrayList<>(user.getRoles().size());
            userRoles.addAll(user.getRoles().stream().map(role -> roleRepository.findByCodeAndEffective(role.getCode(), new DateTime())).collect(Collectors.toList()));
            account.setRoles(userRoles);
            update = true;
        }

        if (user.getProfile() != null) {
            Profile createdProfile = profileRepository.save(user.getProfile());
            account.setProfile(createdProfile);
            update = true;
        }

        if (update) {
            account = accountRepository.save(account);      // Saving account because if Profile or Role didn't exist earlier it would create linkages now.
        }

        return account;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "accounts",
            key = "#result.id")
    public Account update(User user) {
        LOGGER.info("update id:{}", user.getAccount().getId());

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Account accountToUpdate = findOne(user.getAccount().getId());
        if (accountToUpdate == null) {
            // Cannot update Account that hasn't been persisted
            LOGGER.error("Attempted to update a Account, but the entity does not exist.");
            throw new NoResultException("Requested entity not found.");
        }

        accountToUpdate = updateUserRoleAndProfile(user, accountToUpdate);

        return accountToUpdate;
    }

    @Override
    @CacheEvict(
            value = "accounts",
            allEntries = true)
    public void evictCache() {
        LOGGER.info("evictCache");
    }
}
