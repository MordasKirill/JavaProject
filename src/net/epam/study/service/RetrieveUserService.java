package net.epam.study.service;

public interface RetrieveUserService {
    boolean isAuthenticated(Boolean auth, String role);
    boolean checkAdmin(String role);
    boolean checkUser(String role);
}
