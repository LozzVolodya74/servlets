package com.learn.form;

/**
 * Class содержит поля для хранения данных, внесённых пользователем
 *
 * @author Volodymyr Lopachak
 */
public class UserForm {
    private String firstName;
    private String lastName;
    private String login;
    private String dob;
    private String password;
    private String password_again;
    private String email;

    public UserForm() {
        this(null, null, null, null, null, null, null);
    }

    public UserForm(String firstName, String lastName, String login, String dob, String password, String password_again,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.dob = dob;
        this.password = password;
        this.password_again = password_again;
        this.email = email;
    }

    public boolean isFully() {
        return firstName != null && lastName != null && login != null && dob != null && password != null && password_again != null && email != null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_again() {
        return password_again;
    }

    public void setPassword_again(String password_again) {
        this.password_again = password_again;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
