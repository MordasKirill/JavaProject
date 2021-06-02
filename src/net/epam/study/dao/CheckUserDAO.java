package net.epam.study.dao;

public interface CheckUserDAO {
    boolean check(String login, String password) throws DAOException;
    boolean isAdmin(String login) throws DAOException;
}
