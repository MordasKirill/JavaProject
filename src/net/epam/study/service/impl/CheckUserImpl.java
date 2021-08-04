package net.epam.study.service.impl;

import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.CheckUserService;
import net.epam.study.service.ServiceException;

public class CheckUserImpl implements CheckUserService {

    @Override
    public boolean isUserExists(int userId, String password) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();

        try {
            return checkUserDAO.isUserExists(userId, password);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to check if user exist", e);
        }

    }


    @Override
    public String getUserRole(int userId) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();

        try {
            return checkUserDAO.getUserRole(userId);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to check role", e);
        }
    }


    @Override
    public boolean isUserNew(String login, String hashPassword, String role) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();

        try {
            return checkUserDAO.isUserNew(login, hashPassword, role);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Check user fail", e);
        }
    }
}
