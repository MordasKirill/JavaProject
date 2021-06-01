package net.epam.study.dao;

public interface CheckUserDAO {
    boolean check(String login, String password);
    boolean isAdmin(String login);
}
