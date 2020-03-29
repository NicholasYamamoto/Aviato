package com.example.aviato.Classes;

public class AccountClass {
    private int accountID;
    private String firstName;
    private String emailAddress;
    private String password;
    private String defaultCarrier;
    private String defaultDepart;

    public AccountClass(int accountID, String firstName, String emailAddress, String password, String defaultCarrier, String defaultDepart) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.defaultCarrier = defaultCarrier;
        this.defaultDepart = defaultDepart;
    }

    public AccountClass(String firstName, String emailAddress, String password, String defaultCarrier, String defaultDepart) {
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.defaultCarrier = defaultCarrier;
        this.defaultDepart = defaultDepart;
    }

    public int getAccount_id() {
        return accountID;
    }

    public void setAccount_id(int account_id) {
        this.accountID = account_id;
    }

    public String getEmail() {
        return emailAddress;
    }

    public void setEmail(String email) {
        this.emailAddress = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDefaultCarrier() {
        return defaultCarrier;
    }

    public void setDefaultCarrier(String defaultCarrier) {
        this.defaultCarrier = defaultCarrier;
    }

    public String getDefaultDepart() {
        return defaultDepart;
    }

    public void setDefaultDepart(String defaultDepart) {
        this.defaultDepart = defaultDepart;
    }

}
