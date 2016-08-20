package com.solutionwerk.qb.service;

import com.solutionwerk.qb.model.Profile;

import java.util.Collection;

/**
 * The ProfileService interface defines all public business behaviors for
 * operations on the Profile entity model.
 * <p/>
 * This interface should be injected into ProfileService clients, not the
 * implementation bean.
 *
 * @author Piyush Ramavat
 */
public interface ProfileService {

    /**
     * Find all Profile entities.
     *
     * @return A Collection of Profile objects.
     */
    Collection<Profile> findAll();

    /**
     * Find a single Profile entity by primary key identifier.
     *
     * @param id A Long primary key identifier.
     * @return A Profile or <code>null</code> if none found.
     */
    Profile findOne(Long id);

    /**
     * Persists a Profile entity in the data store.
     *
     * @param profile A Profile object to be persisted.
     * @return The persisted Profile entity.
     */
    Profile create(Profile profile);

    /**
     * Updates a previously persisted Profile entity in the data store.
     *
     * @param profile A Profile object to be updated.
     * @return The updated Profile entity.
     */
    Profile update(Profile profile);

    /**
     * Removes a previously persisted Profile entity from the data store.
     *
     * @param id A Long primary key identifier.
     */
    void delete(Long id);

    /**
     * Evicts all members of the "profiles" cache.
     */
    void evictCache();

}
