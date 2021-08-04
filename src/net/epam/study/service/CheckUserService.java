package net.epam.study.service;

public interface CheckUserService {
    boolean isUserExists(int userId, String password) throws ServiceException;
    String getUserRole(int userId) throws ServiceException;
    boolean isUserNew(String login, String hashPassword, String role) throws ServiceException;
}
