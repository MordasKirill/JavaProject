package net.epam.study.service;

public interface CheckUserService {
    boolean isUserDataCorrect(int userId, String password) throws ServiceException;
    String getUserRole(int userId) throws ServiceException;
    boolean isUserUniq(int userId, String login, String hashPassword, String role) throws ServiceException;
}
