package edu.iis.mto.blog;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

public class UserBuilder {
    private String firstName = "Jan";
    private String lastName = "Better";
    private String email = "jan@domain.com";
    private AccountStatus accountStatus = AccountStatus.NEW;

    public UserBuilder withAccountStatus(AccountStatus accountStatus){
        this.accountStatus = accountStatus;
        return this;
    }

    public UserBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public User build(){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAccountStatus(accountStatus);
        return user;
    }

}
