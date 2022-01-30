package com.learn.entity;

import java.sql.Date;

/**
 * Class содержит методы для хранения значений Person
 *
 * @author Volodymyr Lopachak
 * @version 1.0 28 August 2021
 */
public class Person {
    private long id;
    private long roleId;
    private String firstName;
    private String lastName;
    private String login;
    private java.sql.Date dob;
    private String password;
    private String email;

    public Person(long id, long roleId, String firstName, String lastName, String login,
                  Date dob, String password, String email) {
        this.id = id;
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.dob = dob;
        this.password = password;
        this.email = email;
    }

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return roleId == 1 ? "admin" : "user";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Person person = (Person) o;

        if (id != person.id) return false;
        if (roleId != person.roleId) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (login != null ? !login.equals(person.login) : person.login != null) return false;
        if (dob != null ? !dob.equals(person.dob) : person.dob != null) return false;
        if (password != null ? !password.equals(person.password) : person.password != null) return false;
        return email != null ? email.equals(person.email) : person.email == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (dob != null ? dob.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person id= " + id + " roleId= " + roleId + " firstName= " + firstName + " lastName= " + lastName
                + " login='" + login + " dob= " + dob + " password= " + password + " email= " + email;
    }
}
