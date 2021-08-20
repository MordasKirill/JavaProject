package net.epam.study.dao;

import net.epam.study.bean.User;

import java.util.List;

public interface UserDAO {
    boolean isUserDataCorrect(User user) throws DAOException;

    String getUserRole(int userId) throws DAOException;

    boolean isUserUnique(String login) throws DAOException;

    int getUserId(String login) throws DAOException;

    List<User> getUsers(int limit) throws DAOException;

    List<User> getAllUsers() throws DAOException;

    void deleteUser(int id) throws DAOException;

    void changeUserRole(String status, int id) throws DAOException;

    void changeUserPassword(String password, int id) throws DAOException;

    int createNewUser(User user) throws DAOException;
}
