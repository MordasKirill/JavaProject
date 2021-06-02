package net.epam.study.service;

public interface CheckUserService {
    boolean validateUser(String login, String password) throws ServiceException;
    boolean isAdmin(String login) throws ServiceException;
}
