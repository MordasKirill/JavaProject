package net.epam.study.service;

public interface CheckSessionService {
    boolean checkSession(Boolean auth, String role);
}
