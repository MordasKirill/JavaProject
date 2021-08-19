package net.epam.study.service;

import net.epam.study.bean.User;

import java.util.List;

public interface UserService {
    boolean isUserDataCorrect(String login, String password) throws ServiceException;

    String getUserRole(int userId) throws ServiceException;

    boolean isUserUnique(String login) throws ServiceException;

    void deleteUser(String id) throws ServiceException;

    List<User> getUsers(int limit) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    int getUserId(String login) throws ServiceException;

    void changeUserRole(String role, int id) throws ServiceException;

    void changeUserPassword(String password, int id) throws ServiceException;

    int createNewUser(String login, String hashPassword, String role) throws ServiceException;
}
