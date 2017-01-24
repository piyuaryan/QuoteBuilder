package com.solutionwerk.qb.service;


import com.solutionwerk.qb.model.Account;

import java.util.Collection;

/**
 * The AccountService interface defines all public business behaviors for
 * operations on the Account entity model and some related entities such as
 * Role.
 * <p/>
 * This interface should be injected into AccountService clients, not the
 * implementation bean.
 *
 * @author Piyush Ramavat
 */
public interface AccountService {

    /**
     * Find an Account by the username attribute value.
     *
     * @param username A String username to query the repository.
     * @return An Account instance or <code>null</code> if none found.
     */
    Account findByUsername(String username);

    /**
     * Find all Account entities.
     *
     * @return A Collection of Account objects.
     */
    Collection<Account> findAll();

    /**
     * Find a single Account entity by primary key identifier.
     *
     * @param id A Long primary key identifier.
     * @return A Account or <code>null</code> if none found.
     */
    Account findOne(Long id);

    /**
     * Persists a Account entity in the data store.
     *
     * @param account A Account object to be persisted.
     * @return The persisted Account entity.
     */
    Account create(Account account);

    /**
     * Updates a previously persisted Account entity in the data store.
     *
     * @param account A Account object to be updated.
     * @return The updated Account entity.
     */
    Account update(Account account);

    /**
     * Removes a previously persisted Account entity from the data store.
     *
     * @param id A Long primary key identifier.
     */
    void delete(Long id);

    /**
     * Evicts all members of the "profiles" cache.
     */
    void evictCache();

}
