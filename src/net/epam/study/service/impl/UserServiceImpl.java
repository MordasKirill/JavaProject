package net.epam.study.service.impl;

import net.epam.study.bean.User;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.UsersDAO;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.ServiceException;
import net.epam.study.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public boolean isUserDataCorrect(int userId, String password) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UsersDAO usersDAO = provider.getUsersDAO();

        try {
            return usersDAO.isUserDataCorrect(userId, password);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Fail to check if user exist", e);
        }
    }


    @Override
    public String getUserRole(int userId) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UsersDAO usersDAO = provider.getUsersDAO();

        try {
            return usersDAO.getUserRole(userId);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Fail to check role", e);
        }
    }


    @Override
    public boolean isUserUnique(String login) throws ServiceException {
        DAOProvider provider = DAOProvider.getInstance();
        UsersDAO usersDAO = provider.getUsersDAO();

        try {
            return usersDAO.isUserUnique(login);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Check user fail", e);
        }
    }

    @Override
    public List<User> getUsers(int limit) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UsersDAO usersDAO = daoProvider.getUsersDAO();
        try {
            return usersDAO.getUsers(limit);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Get orders fail", e);
        }

    }

    @Override
    public List<User> getAllUsers() throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        UsersDAO usersDAO = daoProvider.getUsersDAO();

        List<User> users;

        try {
            users = usersDAO.getAllUsers();
            return users;

        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Get all orders fail", e);
        }

    }

    @Override
    public int getUserId(String login) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UsersDAO usersDAO = daoProvider.getUsersDAO();

        int result = 0;

        try {

            result = usersDAO.getUserId(login);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Payment create fail", e);
        }
        return result;
    }

    @Override
    public void deleteUser(String id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UsersDAO usersDAO = daoProvider.getUsersDAO();

        try {
            usersDAO.deleteUser(id);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Fail to delete user", e);
        }
    }

    public void changeUserRole(String role, int id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UsersDAO usersDAO = daoProvider.getUsersDAO();
        try {
            usersDAO.changeUserRole(role, id);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Change user role fail", e);
        }
    }

    @Override
    public int createNewUser(String login, String hashPassword, String role) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        UsersDAO usersDAO = daoProvider.getUsersDAO();
        int userId = 0;
        try {
            userId = usersDAO.createNewUser(new User(login, hashPassword, role));
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Fail to get user id", e);
        }
        return userId;
    }
}
