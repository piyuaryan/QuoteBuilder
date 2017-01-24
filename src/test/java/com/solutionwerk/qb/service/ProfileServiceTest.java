package com.solutionwerk.qb.service;

import com.solutionwerk.qb.AbstractTest;
import com.solutionwerk.qb.model.Profile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Unit test methods for the ProfileService and ProfileServiceBean.
 *
 * @author Piyush Ramavat
 */
@Transactional
public class ProfileServiceTest extends AbstractTest {

    @Autowired
    private ProfileService service;

    @Before
    public void setUp() {
        super.setUp();
        service.evictCache();
    }

    @After
    public void tearDown() {
        // clean up after each test method
    }

    @Test
    public void testFindAll() {
        Collection<Profile> list = service.findAll();

        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected list size", 4, list.size());
    }

    @Test
    public void testFindOne() {
        Long id = 1L;

        Profile entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id, entity.getId());
    }

    @Test
    public void testFindOneNotFound() {
        Long id = Long.MAX_VALUE;

        Profile entity = service.findOne(id);

        Assert.assertNull("failure - expected null", entity);
    }

    @Test
    public void testCreate() {
        Profile entity = new Profile();
        entity.setName("test");

        Profile createdEntity = service.create(entity);

        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.getName());

        Collection<Profile> list = service.findAll();

        Assert.assertEquals("failure - expected size", 5, list.size());
    }

    @Test
    public void testCreateWithId() {
        Exception exception = null;

        Profile entity = new Profile();
        entity.setId(Long.MAX_VALUE);
        entity.setName("test");

        try {
            service.create(entity);
        } catch (EntityExistsException e) {
            exception = e;
        }

        Assert.assertNotNull("failure - expected exception", exception);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;

        Profile entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        String updatedText = entity.getName() + " test";
        entity.setName(updatedText);
        Profile updatedEntity = service.update(entity);

        Assert.assertNotNull("failure - expected not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute match", id, updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", updatedText, updatedEntity.getName());
    }

    @Test
    public void testUpdateNotFound() {
        Exception exception = null;

        Profile entity = new Profile();
        entity.setId(Long.MAX_VALUE);
        entity.setName("test");

        try {
            service.update(entity);
        } catch (NoResultException e) {
            exception = e;
        }

        Assert.assertNotNull("failure - expected exception", exception);
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        Profile entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);

        service.delete(id);

        Collection<Profile> list = service.findAll();

        Assert.assertEquals("failure - expected size", 3, list.size());

        Profile deletedEntity = service.findOne(id);

        Assert.assertNull("failure - expected null", deletedEntity);
    }
}
