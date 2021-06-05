package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface NewUserValidateDAO {
    boolean validate(String login, String hashPassword, String role) throws DAOException, ConnectionPoolException;
}
