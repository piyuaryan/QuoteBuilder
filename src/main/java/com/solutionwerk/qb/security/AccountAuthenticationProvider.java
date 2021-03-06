package com.solutionwerk.qb.security;

import com.solutionwerk.qb.util.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A Spring Security AuthenticationProvider which extends
 * <code>AbstractUserDetailsAuthenticationProvider</code>. This classes uses the
 * <code>AccountUserDetailsService</code> to retrieve a UserDetails instance.
 * <p/>
 * A PasswordEncoder compares the supplied authentication credentials to those
 * in the UserDetails.
 *
 * @author Piyush Ramavat
 */
@Component
public class AccountAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * The Logger for this class.
     */
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * A Spring Security UserDetailsService implementation based upon the
     * Account entity model.
     */
    @Autowired
    private AccountUserDetailsService userDetailsService;

    /**
     * A PasswordEncoder instance to hash clear test password values.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO: Override and modify this method to customize token and validate the token later in interceptor.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        LOGGER.debug("> additionalAuthenticationChecks");

        if (token.getCredentials() == null || userDetails.getPassword() == null) {
            LOGGER.error("Credentials NULL.");
            throw new BadCredentialsException("Credentials may not be null.");
        }

        //TODO: Send Encrypted password and Match
        if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())) {
            LOGGER.error("Invalid credentials.");
            throw new BadCredentialsException("Invalid credentials.");
        }

        RequestContext.setUsername(userDetails.getUsername());

        LOGGER.debug("< additionalAuthenticationChecks");
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        LOGGER.debug("> retrieveUser");

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        LOGGER.debug("< retrieveUser");
        return userDetails;
    }

}
