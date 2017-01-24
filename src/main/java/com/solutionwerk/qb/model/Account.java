package com.solutionwerk.qb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * The Account class is an entity model object. An Account describes the
 * security credentials and authentication flags that permit access to
 * application functionality.
 *
 * @author Piyush Ramavat
 */
@Entity
public class Account extends TransactionalEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private boolean enabled = true;

    @NotNull
    private boolean credentialsExpired = false;

    @NotNull
    private boolean expired = false;

    @NotNull
    private boolean locked = false;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "AccountRole",
            joinColumns = @JoinColumn(
                    name = "accountId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleId",
                    referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "AccountProfile",
            joinColumns = @JoinColumn(name = "accountId",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profileId", referencedColumnName = "id"))
    private List<Profile> profiles;

    public Account() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
