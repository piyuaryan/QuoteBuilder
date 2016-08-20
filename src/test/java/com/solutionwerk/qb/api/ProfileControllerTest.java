package com.solutionwerk.qb.api;

import com.solutionwerk.qb.AbstractControllerTest;
import com.solutionwerk.qb.model.Profile;
import com.solutionwerk.qb.service.ProfileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit tests for the ProfileController using Spring MVC Mocks.
 * <p/>
 * These tests utilize the Spring MVC Mock objects to simulate sending actual
 * HTTP requests to the Controller component. This test ensures that the
 * RequestMappings are configured correctly. Also, these tests ensure that the
 * request and response bodies are serialized as expected.
 *
 * @author Piyush Ramavat
 */
@Transactional
public class ProfileControllerTest extends AbstractControllerTest {

    @Autowired
    private ProfileService profileService;

    @Before
    public void setUp() {
        super.setUp();
        profileService.evictCache();
    }

    @Test
    public void testGetProfiles() throws Exception {

        String uri = "/api/profiles";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetProfile() throws Exception {

        String uri = "/api/profiles/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetProfileNotFound() throws Exception {

        String uri = "/api/profiles/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateProfile() throws Exception {

        String uri = "/api/profiles";
        Profile profile = new Profile();
        profile.setText("test");
        String inputJson = super.mapToJson(profile);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Profile createdProfile = super.mapFromJson(content, Profile.class);

        Assert.assertNotNull("failure - expected profile not null",
                createdProfile);
        Assert.assertNotNull("failure - expected profile.id not null",
                createdProfile.getId());
        Assert.assertEquals("failure - expected profile.text match", "test",
                createdProfile.getText());

    }

    @Test
    public void testUpdateProfile() throws Exception {

        String uri = "/api/profiles/{id}";
        Long id = new Long(1);
        Profile profile = profileService.findOne(id);
        String updatedText = profile.getText() + " test";
        profile.setText(updatedText);
        String inputJson = super.mapToJson(profile);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Profile updatedProfile = super.mapFromJson(content, Profile.class);

        Assert.assertNotNull("failure - expected profile not null",
                updatedProfile);
        Assert.assertEquals("failure - expected profile.id unchanged",
                profile.getId(), updatedProfile.getId());
        Assert.assertEquals("failure - expected updated profile text match",
                updatedText, updatedProfile.getText());

    }

    @Test
    public void testDeleteProfile() throws Exception {

        String uri = "/api/profiles/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

        Profile deletedProfile = profileService.findOne(id);

        Assert.assertNull("failure - expected profile to be null",
                deletedProfile);

    }

}