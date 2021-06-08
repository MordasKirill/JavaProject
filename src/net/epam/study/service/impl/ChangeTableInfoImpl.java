package net.epam.study.service.impl;

import net.epam.study.dao.ChangeTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.ChangeTableInfoService;
import net.epam.study.service.ServiceException;

public class ChangeTableInfoImpl implements ChangeTableInfoService {

    @Override
    public void changeStatus(String id, String status) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeTableInfoDAO changeTableInfoDAO = daoProvider.getChangeTableInfoDAO();

        try {
            changeTableInfoDAO.changeStatus(id, status);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to change status", e);
        }

    }

    @Override
    public void changeRole(String id, String role) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeTableInfoDAO changeTableInfoDAO = daoProvider.getChangeTableInfoDAO();

        try {
            changeTableInfoDAO.changeRole(id, role);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to change role", e);
        }

    }
}
