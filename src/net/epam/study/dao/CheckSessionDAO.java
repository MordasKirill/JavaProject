package net.epam.study.dao;

public interface CheckSessionDAO {
    boolean checkSession(Boolean auth, String role);
}
