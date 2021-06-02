package net.epam.study.service.impl;

import net.epam.study.dao.CheckUserDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.CheckUserService;
import net.epam.study.service.ServiceException;

public class CheckUserImpl implements CheckUserService {
    @Override
    public boolean validateUser(String login, String password) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();
        try {
            return checkUserDAO.check(login, password);
        } catch (DAOException e){
            throw new ServiceException("Fail to check if user exist", e);
        }

    }

    @Override
    public boolean isAdmin(String login) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        CheckUserDAO checkUserDAO = provider.getCheckUserDAO();
        try {
            return checkUserDAO.isAdmin(login);
        } catch (DAOException e){
            throw new ServiceException("Fail to check isAdmin", e);
        }
    }
}
