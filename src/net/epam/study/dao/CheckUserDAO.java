package net.epam.study.dao;

public interface CheckUserDAO {
    boolean isUserExists(String login, String password) throws DAOException;
    String getUserRole(String login) throws DAOException;
}
