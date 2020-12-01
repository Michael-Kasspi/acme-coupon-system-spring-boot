package com.acme.couponsystem.service.task;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("account.default.admin")
public class DefaultAdminConfig {

    private String firstName = "admin";
    private String lastName = "admin";
    private String email = "admin@acme.com";
    private String password = "1234";

    public DefaultAdminConfig()
            /* no-op */
    {
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }
}
