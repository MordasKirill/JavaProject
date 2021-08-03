package net.epam.study.dao;

public interface RetrieveUserDAO {
    boolean isAuthenticated(Boolean auth, String role);
    boolean isAdmin(String role);
    boolean isUser(String role);
}
