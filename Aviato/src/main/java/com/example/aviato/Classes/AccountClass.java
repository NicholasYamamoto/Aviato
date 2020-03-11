package com.example.aviato.Classes;

public class AccountClass {
    private int account_id;
    private String email;
    private String password;
    private String name;
    private String defaultCarrier;
    private String defaultDepart;

    public AccountClass(int account_id, String name, String email, String password, String defaultCarrier, String defaultDepart) {
        this.account_id = account_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.defaultCarrier = defaultCarrier;
        this.defaultDepart = defaultDepart;
    }

    public AccountClass(String name, String email, String password, String defaultCarrier, String defaultDepart) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.defaultCarrier = defaultCarrier;
        this.defaultDepart = defaultDepart;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
