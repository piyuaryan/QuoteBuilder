package com.solutionwerk.qb.model.network;

import com.solutionwerk.qb.model.Account;
import com.solutionwerk.qb.model.Profile;
import com.solutionwerk.qb.model.Role;

import java.util.List;

public class User {

    Account account;
    List<Role> roles;
    Profile profile;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
