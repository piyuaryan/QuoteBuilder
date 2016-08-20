package com.solutionwerk.qb.repository;

import com.solutionwerk.qb.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The AccountRepository interface is a Spring Data JPA data repository for
 * Account entities. The AccountRepository provides all the data access
 * behaviors exposed by <code>JpaRepository</code> and additional custom
 * behaviors may be defined in this interface.
 *
 * @author Piyush Ramavat
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Query for a single Account entity by username.
     *
     * @param username A String username value to query the repository.
     * @return An Account or <code>null</code> if none found.
     */
    Account findByUsername(String username);

}
