package com.learn.entity;

/**
 * Class содержит методы для хранения значений Role
 *
 * @author Volodymyr Lopachak
 * @version 1.0 28 August 2021
 */
public class Role {
    private long id;
    private String role;

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Role role1 = (Role) o;

        if (id != role1.id) return false;
        return role.equals(role1.role);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Role id= " + id + " role= " + role;
    }
}
