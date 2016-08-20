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

    private String text;

    public Profile() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
