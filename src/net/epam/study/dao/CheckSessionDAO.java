package net.epam.study.dao;

public interface CheckSessionDAO {
    boolean isSession(Boolean auth, String role);
    boolean isAdmin(String role);
    boolean isUser(String role);
}
