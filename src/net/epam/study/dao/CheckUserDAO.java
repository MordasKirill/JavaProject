package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

public interface CheckUserDAO {
    boolean isUserExists(int userId, String password) throws DAOException, ConnectionPoolException;
    String getUserRole(int userId) throws DAOException, ConnectionPoolException;
    boolean isUserNew(String login, String hashPassword, String role) throws DAOException, ConnectionPoolException;
}
