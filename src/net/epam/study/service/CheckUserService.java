package net.epam.study.service;

public interface CheckUserService {
    boolean validateUser(String login, String password);
    boolean isAdmin(String login);
}
