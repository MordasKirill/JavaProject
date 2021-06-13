package net.epam.study.service;

public interface CheckUserService {
    boolean isUser(String login, String password) throws ServiceException;
    String getUserRole(String login) throws ServiceException;
    boolean isUserNew(String login, String hashPassword, String role) throws ServiceException;
}
