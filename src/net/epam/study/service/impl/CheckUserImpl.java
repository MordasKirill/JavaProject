package net.epam.study.service.impl;

import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.CheckUserService;
import net.epam.study.service.ServiceException;
import net.epam.study.dao.connection.ConnectionPoolException;

public class CheckUserImpl implements CheckUserService {
    @Override
    public boolean validateUser(String login, String password) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();
        try {
            return checkUserDAO.isUserExists(login, password);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to check if user exist", e);
        }

    }

    @Override
    public String getUserRole(String login) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();
        try {
            return checkUserDAO.getUserRole(login);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to check role", e);
        }
    }
}
