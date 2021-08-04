package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface CheckUserDAO {
    boolean isUserDataCorrect(int userId, String password) throws DAOException, ConnectionPoolException;
    String getUserRole(int userId) throws DAOException, ConnectionPoolException;
    boolean isUserUniq(int userId, String login, String hashPassword, String role) throws DAOException, ConnectionPoolException;
}
