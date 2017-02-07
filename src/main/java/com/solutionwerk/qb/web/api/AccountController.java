package com.solutionwerk.qb.web.api;

import com.solutionwerk.qb.model.Account;
import com.solutionwerk.qb.model.network.User;
import com.solutionwerk.qb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * The AccountController class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>
 * which, by default, contains a ResponseEntity converted into JSON with an
 * associated HTTP status code.
 *
 * @author Piyush Ramavat
 */
@RestController
public class AccountController extends BaseController {

    /**
     * The AccountService business service.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Web service endpoint to fetch all Account entities. The service returns the collection of Account entities as JSON.
     *
     * @return A ResponseEntity containing a Collection of Account objects.
     */
    @RequestMapping(
            value = "/api/accounts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Account>> getAccounts() {
        LOGGER.info("> getAccounts");

        Collection<Account> accounts = accountService.findAll();

        LOGGER.info("< getAccounts");
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Web service endpoint to fetch a single Account entity by username.
     * <p/>
     * If found, the Account is returned as JSON with HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body with HTTP status 404.
     *
     * @param username A Long URL path variable containing the Account primary key identifier.
     * @return A ResponseEntity containing a single Account object, if found, and a HTTP status code as described in the method comment.
     */
    @RequestMapping(
            value = "/api/accounts/{username}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("username") String username) {
        LOGGER.info("> getAccount username:{}", username);

        Account account = accountService.findByUsername(username);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("< getAccount username:{}", username);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * Web service endpoint to fetch a single Account entity by primary key identifier.
     * <p/>
     * If found, the Account is returned as JSON with HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body with HTTP status 404.
     *
     * @param id A Long URL path variable containing the Account primary key identifier.
     * @return A ResponseEntity containing a single Account object, if found, and a HTTP status code as described in the method comment.
     */
    @RequestMapping(
            value = "/api/accounts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
        LOGGER.info("> getAccount id:{}", id);

        Account account = accountService.findOne(id);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("< getAccount id:{}", id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * Web service endpoint to create a single Account entity. The HTTP request
     * body is expected to contain a Account object in JSON format. The
     * Account is persisted in the data repository.
     * <p/>
     * If created successfully, the persisted Account is returned as JSON with
     * HTTP status 201.
     * <p/>
     * If not created successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param account The Account object to be created.
     * @return A ResponseEntity containing a single Account object, if created
     * successfully, and a HTTP status code as described in the method
     * comment.
     */
    @RequestMapping(
            value = "/api/accounts",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(
            @RequestBody Account account) {
        LOGGER.info("> createAccount");

        Account savedAccount = accountService.create(account);

        LOGGER.info("< createAccount");
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    /**
     * Web service endpoint to update a single Account entity. The HTTP request
     * body is expected to contain a Account object in JSON format. The
     * Account is updated in the data repository.
     * <p/>
     * If updated successfully, the persisted Account is returned as JSON with
     * HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body and HTTP status
     * 404.
     * <p/>
     * If not updated successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param account The Account object to be updated.
     * @return A ResponseEntity containing a single Account object, if updated
     * successfully, and a HTTP status code as described in the method
     * comment.
     */
    @RequestMapping(
            value = "/api/accounts",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        LOGGER.info("> updateAccount id:{}", account.getId());

        Account updatedAccount = accountService.update(account);
        if (updatedAccount == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LOGGER.info("< updateAccount id:{}", account.getId());
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    /**
     * Web service endpoint to delete a single Account entity. The HTTP request
     * body is empty. The primary key identifier of the Account to be deleted
     * is supplied in the URL as a path variable.
     * <p/>
     * If deleted successfully, the service returns an empty response body with
     * HTTP status 204.
     * <p/>
     * If not deleted successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param id A Long URL path variable containing the Account primary key
     *           identifier.
     * @return A ResponseEntity with an empty response body and a HTTP status
     * code as described in the method comment.
     */
    @RequestMapping(
            value = "/api/accounts/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Long id) {
        LOGGER.info("> deleteAccount id:{}", id);

        accountService.delete(id);

        LOGGER.info("< deleteAccount id:{}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Web service endpoint to create a User with Account and Profile entity with Role. The HTTP request
     * body is expected to contain a Account object in JSON format. The
     * Account is persisted in the data repository.
     * <p/>
     * If created successfully, the persisted Account is returned as JSON with
     * HTTP status 201.
     * <p/>
     * If not created successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param user The User object with Account, Roles and Profile object to be created.
     * @return A ResponseEntity containing a single Account object, if created
     * successfully, and a HTTP status code as described in the method
     * comment.
     */
    @RequestMapping(
            value = "/api/accounts/createUser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createUser(
            @RequestBody User user) {
        LOGGER.info("> createAccount");

        Account savedAccount = accountService.create(user);

        LOGGER.info("< createAccount");
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }
}
