package com.solutionwerk.qb.service;

import com.solutionwerk.qb.model.Profile;
import com.solutionwerk.qb.repository.ProfileRepository;
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
 * The ProfileServiceBean encapsulates all business behaviors operating on the
 * Profile entity model object.
 *
 * @author Piyush Ramavat
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProfileServiceBean implements ProfileService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * The Spring Data repository for Profile entities.
     */
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Collection<Profile> findAll() {
        LOGGER.info("> findAll");

        Collection<Profile> profiles = profileRepository.findAll();

        LOGGER.info("< findAll");
        return profiles;
    }

    @Override
    @Cacheable(
            value = "profiles",
            key = "#id")
    public Profile findOne(Long id) {
        LOGGER.info("> findOne id:{}", id);

        Profile profile = profileRepository.findOne(id);

        LOGGER.info("< findOne id:{}", id);
        return profile;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "profiles",
            key = "#result.id")
    public Profile create(Profile profile) {
        LOGGER.info("> create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (profile.getId() != null) {
            // Cannot create Profile with specified ID value
            LOGGER.error("Attempted to create a Profile, but id attribute was not null.");
            throw new EntityExistsException("The id attribute must be null to persist a new entity.");
        }

        Profile savedProfile = profileRepository.save(profile);

        LOGGER.info("< create");
        return savedProfile;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CachePut(
            value = "profiles",
            key = "#profile.id")
    public Profile update(Profile profile) {
        LOGGER.info("> update id:{}", profile.getId());

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        Profile profileToUpdate = findOne(profile.getId());
        if (profileToUpdate == null) {
            // Cannot update Profile that hasn't been persisted
            LOGGER.error("Attempted to update a Profile, but the entity does not exist.");
            throw new NoResultException("Requested entity not found.");
        }

        profileToUpdate.setText(profile.getText());
        Profile updatedProfile = profileRepository.save(profileToUpdate);

        LOGGER.info("< update id:{}", profile.getId());
        return updatedProfile;
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false)
    @CacheEvict(
            value = "profiles",
            key = "#id")
    public void delete(Long id) {
        LOGGER.info("> delete id:{}", id);

        profileRepository.delete(id);

        LOGGER.info("< delete id:{}", id);
    }

    @Override
    @CacheEvict(
            value = "profiles",
            allEntries = true)
    public void evictCache() {
        LOGGER.info("> evictCache");
        LOGGER.info("< evictCache");
    }
}
