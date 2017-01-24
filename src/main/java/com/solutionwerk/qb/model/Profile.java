package com.solutionwerk.qb.model;

import javax.persistence.Entity;

/**
 * The Profile class is an entity model object.
 *
 * @author Piyush Ramavat
 */
@Entity
public class Profile extends TransactionalEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String email;

    private String mobile;

    public Profile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
