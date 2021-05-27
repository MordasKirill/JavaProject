package net.epam.study.dao;

public interface UserLoginValidateDAO {
    boolean validate (String login, String password);
    boolean isAdmin(String login);
}
