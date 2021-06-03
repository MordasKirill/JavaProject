package net.epam.study.service;

public interface CheckUserService {
    boolean validateUser(String login, String password) throws ServiceException;
    String getUserRole(String login) throws ServiceException;
}
