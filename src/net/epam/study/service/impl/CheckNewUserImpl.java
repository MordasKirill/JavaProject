package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.NewUserValidateDAO;
import net.epam.study.service.CheckNewUserService;
import net.epam.study.service.ServiceException;
import net.epam.study.dao.connection.ConnectionPoolException;

public class CheckNewUserImpl implements CheckNewUserService {
    @Override
    public boolean check(String login, String hashPassword, String role) throws ServiceException{
        DAOProvider provider = DAOProvider.getInstance();
        NewUserValidateDAO newUserValidateDAO = provider.getNewUserValidateDAO();
        try {
            return newUserValidateDAO.validate(login, hashPassword, role);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Check user fail", e);
        }
    }
}
