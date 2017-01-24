package com.solutionwerk.qb.api;

import com.solutionwerk.qb.AbstractControllerTest;
import com.solutionwerk.qb.model.Profile;
import com.solutionwerk.qb.service.ProfileService;
import com.solutionwerk.qb.web.api.ProfileController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProfileController using Mockito mocks and spies.
 * <p/>
 * These tests utilize the Mockito framework objects to simulate interaction
 * with back-end components. The controller methods are invoked directly
 * bypassing the Spring MVC mappings. Back-end components are mocked and
 * injected into the controller. Mockito spies and verifications are performed
 * ensuring controller behaviors.
 *
 * @author Piyush Ramavat
 */
@Transactional
public class ProfileControllerMocksTest extends AbstractControllerTest {

    /**
     * A mocked ProfileService
     */
    @Mock
    private ProfileService profileService;

    /**
     * A ProfileController instance with <code>@Mock</code> components injected
     * into it.
     */
    @InjectMocks
    private ProfileController profileController;

    /**
     * Setup each test method. Initialize Mockito mock and spy objects. Scan for
     * Mockito annotations.
     */
    @Before
    public void setUp() {
        // Initialize Mockito annotated components
        MockitoAnnotations.initMocks(this);
        // Prepare the Spring MVC Mock components for standalone testing
        setUp(profileController);
    }

    @Test
    public void testGetProfiles() throws Exception {
        // Create some test data
        Collection<Profile> list = getEntityListStubData();

        // Stub the ProfileService.findAll method return value
        when(profileService.findAll()).thenReturn(list);

        // Perform the behavior being tested
        String uri = "/api/profiles";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProfileService.findAll method was invoked once
        verify(profileService, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }


    @Test
    public void testGetProfile() throws Exception {
        // Create some test data
        Long id = 1L;
        Profile entity = getEntityStubData();

        // Stub the ProfileService.findOne method return value
        when(profileService.findOne(id)).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/profiles/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProfileService.findOne method was invoked once
        verify(profileService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    @Test
    public void testGetProfileNotFound() throws Exception {
        // Create some test data
        Long id = Long.MAX_VALUE;

        // Stub the ProfileService.findOne method return value
        when(profileService.findOne(id)).thenReturn(null);

        // Perform the behavior being tested
        String uri = "/api/profiles/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProfileService.findOne method was invoked once
        verify(profileService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
    }

    @Test
    public void testCreateProfile() throws Exception {
        // Create some test data
        Profile entity = getEntityStubData();

        // Stub the ProfileService.create method return value
        when(profileService.create(any(Profile.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/profiles";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProfileService.create method was invoked once
        verify(profileService, times(1)).create(any(Profile.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        Profile createdEntity = super.mapFromJson(content, Profile.class);

        Assert.assertNotNull("failure - expected entity not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getName(), createdEntity.getName());
    }

    @Test
    public void testUpdateProfile() throws Exception {
        // Create some test data
        Profile entity = getEntityStubData();
        entity.setName(entity.getName() + " test");
        Long id = 1L;

        // Stub the ProfileService.update method return value
        when(profileService.update(any(Profile.class))).thenReturn(entity);

        // Perform the behavior being tested
        String uri = "/api/profiles/{id}";
        String inputJson = super.mapToJson(entity);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProfileService.update method was invoked once
        verify(profileService, times(1)).update(any(Profile.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", content.trim().length() > 0);

        Profile updatedEntity = super.mapFromJson(content, Profile.class);

        Assert.assertNotNull("failure - expected entity not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(), updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getName(), updatedEntity.getName());
    }

    @Test
    public void testDeleteProfile() throws Exception {
        // Create some test data
        Long id = 1L;

        // Perform the behavior being tested
        String uri = "/api/profiles/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        // Verify the ProfileService.delete method was invoked once
        verify(profileService, times(1)).delete(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
    }

    private Collection<Profile> getEntityListStubData() {
        Collection<Profile> list = new ArrayList<>();
        list.add(getEntityStubData());
        return list;
    }

    private Profile getEntityStubData() {
        Profile entity = new Profile();
        entity.setId(1L);
        entity.setName("hello");
        return entity;
    }
}
