package net.epam.study.dao;

import net.epam.study.bean.User;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.util.List;

public interface UsersDAO {
    boolean isUserDataCorrect(int userId, String password) throws DAOException, ConnectionPoolException;

    String getUserRole(int userId) throws DAOException, ConnectionPoolException;

    boolean isUserUnique(String login) throws DAOException, ConnectionPoolException;

    int getUserId(String login) throws DAOException, ConnectionPoolException;

    List<User> getUsers(int limit) throws DAOException, ConnectionPoolException;

    List<User> getAllUsers() throws DAOException, ConnectionPoolException;

    void deleteUser(String id) throws DAOException, ConnectionPoolException;

    void changeUserRole(String status, int id) throws DAOException, ConnectionPoolException;

    int createNewUser(User user) throws DAOException, ConnectionPoolException;
}
