package com.solutionwerk.qb.web.api;

import com.solutionwerk.qb.model.Profile;
import com.solutionwerk.qb.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * The ProfileController class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>
 * which, by default, contains a ResponseEntity converted into JSON with an
 * associated HTTP status code.
 *
 * @author Piyush Ramavat
 */
@RestController
public class ProfileController extends BaseController {

    /**
     * The ProfileService business service.
     */
    @Autowired
    private ProfileService profileService;

    /**
     * Web service endpoint to fetch all Profile entities. The service returns
     * the collection of Profile entities as JSON.
     *
     * @return A ResponseEntity containing a Collection of Profile objects.
     */
    @RequestMapping(
            value = "/api/profiles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Profile>> getProfiles() {
        LOGGER.info("> getProfiles");

        Collection<Profile> profiles = profileService.findAll();

        LOGGER.info("< getProfiles");
        return new ResponseEntity<>(profiles,
                HttpStatus.OK);
    }

    /**
     * Web service endpoint to fetch a single Profile entity by primary key
     * identifier.
     * <p/>
     * If found, the Profile is returned as JSON with HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body with HTTP status
     * 404.
     *
     * @param id A Long URL path variable containing the Profile primary key
     *           identifier.
     * @return A ResponseEntity containing a single Profile object, if found,
     * and a HTTP status code as described in the method comment.
     */
    @RequestMapping(
            value = "/api/profiles/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> getProfile(@PathVariable("id") Long id) {
        LOGGER.info("> getProfile id:{}", id);

        Profile profile = profileService.findOne(id);
        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("< getProfile id:{}", id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    /**
     * Web service endpoint to create a single Profile entity. The HTTP request
     * body is expected to contain a Profile object in JSON format. The
     * Profile is persisted in the data repository.
     * <p/>
     * If created successfully, the persisted Profile is returned as JSON with
     * HTTP status 201.
     * <p/>
     * If not created successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param profile The Profile object to be created.
     * @return A ResponseEntity containing a single Profile object, if created
     * successfully, and a HTTP status code as described in the method
     * comment.
     */
    @RequestMapping(
            value = "/api/profiles",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> createProfile(
            @RequestBody Profile profile) {
        LOGGER.info("> createProfile");

        Profile savedProfile = profileService.create(profile);

        LOGGER.info("< createProfile");
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    /**
     * Web service endpoint to update a single Profile entity. The HTTP request
     * body is expected to contain a Profile object in JSON format. The
     * Profile is updated in the data repository.
     * <p/>
     * If updated successfully, the persisted Profile is returned as JSON with
     * HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body and HTTP status
     * 404.
     * <p/>
     * If not updated successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param profile The Profile object to be updated.
     * @return A ResponseEntity containing a single Profile object, if updated
     * successfully, and a HTTP status code as described in the method
     * comment.
     */
    @RequestMapping(
            value = "/api/profiles/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
        LOGGER.info("> updateProfile id:{}", profile.getId());

        Profile updatedProfile = profileService.update(profile);
        if (updatedProfile == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("< updateProfile id:{}", profile.getId());
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    /**
     * Web service endpoint to delete a single Profile entity. The HTTP request
     * body is empty. The primary key identifier of the Profile to be deleted
     * is supplied in the URL as a path variable.
     * <p/>
     * If deleted successfully, the service returns an empty response body with
     * HTTP status 204.
     * <p/>
     * If not deleted successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param id A Long URL path variable containing the Profile primary key
     *           identifier.
     * @return A ResponseEntity with an empty response body and a HTTP status
     * code as described in the method comment.
     */
    @RequestMapping(
            value = "/api/profiles/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Profile> deleteProfile(@PathVariable("id") Long id) {
        LOGGER.info("> deleteProfile id:{}", id);

        profileService.delete(id);

        LOGGER.info("< deleteProfile id:{}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
