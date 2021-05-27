package net.epam.study.dao;

public interface NewUserValidateDAO {
    boolean validate(String sqlCommand, String login);
}
