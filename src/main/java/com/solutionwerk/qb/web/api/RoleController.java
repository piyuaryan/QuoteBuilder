package com.solutionwerk.qb.web.api;

import com.solutionwerk.qb.model.Role;
import com.solutionwerk.qb.repository.RoleRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * The RoleController class is a RESTful web service controller. The
 * <code>@RestController</code> annotation informs Spring that each
 * <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>.
 * <p/>
 * Note: This controller class was created for demonstration and testing
 * purposes. Typically, a Repository is not wired directly into a Controller,
 * but rather into a Service component which encapsulates the Repository
 * behaviors.
 *
 * @author Piyush Ramavat
 */
@RestController
public class RoleController extends BaseController {

    /**
     * The Spring Data JPA Repository for the Role entity model class.
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Web service endpoint to fetch all Role entities. The service returns the
     * collection of entities as JSON.
     *
     * @return A ResponseEntity containing a collection of Role objects.
     */
    @RequestMapping(
            value = "/api/roles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Role>> getRoles() {

        Collection<Role> roles = roleRepository.findAllEffective(new DateTime());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
