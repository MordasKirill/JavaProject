package net.epam.study.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String login;
    private String password;
    private String role;
    private String id;

    public User(){
        super();
    }

    public User (String login, String password, String role){
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getLogin().equals(user.getLogin()) &&
                getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole())&&
                getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getRole(), getId());
    }

    @Override
    public String toString() {
        return "User[" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", id='" + id + '\''+
                ']';
    }
}
