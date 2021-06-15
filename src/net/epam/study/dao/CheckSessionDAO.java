package net.epam.study.dao;

public interface CheckSessionDAO {
    boolean checkSession(Boolean auth, String role);
    boolean checkAdmin(String role);
    boolean checkUser(String role);
}
