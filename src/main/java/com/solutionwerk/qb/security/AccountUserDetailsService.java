package com.solutionwerk.qb.security;

import com.solutionwerk.qb.model.Account;
import com.solutionwerk.qb.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A Spring Security UserDetailsService implementation which creates UserDetails
 * objects from the Account and Role entities.
 *
 * @author Piyush Ramavat
 */
@Service
public class AccountUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The AccountService business service.
     */
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("> loadUserByUsername {}", username);

        Account account = accountService.findByUsername(username);
        if (account == null) {
            // Not found...
            throw new UsernameNotFoundException("User " + username + " not found.");
        }

        if (account.getRoles() == null || account.getRoles().isEmpty()) {
            // No Roles assigned to user...
            throw new UsernameNotFoundException("User not authorized.");
        }

        Collection<GrantedAuthority> grantedAuthorities = account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toCollection(ArrayList::new));

        User userDetails = new User(account.getUsername(), account.getPassword(), account.isEnabled(), !account.isExpired(), !account.isCredentialsExpired(),
                !account.isLocked(), grantedAuthorities);

        logger.debug("< loadUserByUsername {}", username);
        return userDetails;
    }
}
