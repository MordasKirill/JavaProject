package net.epam.study.dao;

public interface NewUserValidateDAO {
    boolean validate(String login, String hashPassword, String role);
}
