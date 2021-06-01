package net.epam.study.service;

public interface CheckNewUserService {
    boolean check(String login, String hashPassword, String role);
}
