package net.epam.study.service.impl;

import net.epam.study.dao.ChangeDBTableFieldsDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.ChangeDBTableFieldsService;
import net.epam.study.service.ServiceException;

public class ChangeDBTableFieldsImpl implements ChangeDBTableFieldsService {

    @Override
    public void changeOrderStatus(String id, String status) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeDBTableFieldsDAO changeDBTableFieldsDAO = daoProvider.getChangeDBTableFieldsDAO();

        try {
            changeDBTableFieldsDAO.changeOrderStatus(id, status);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to change status", e);
        }

    }

    @Override
    public void changeRole(String id, String role) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeDBTableFieldsDAO changeDBTableFieldsDAO = daoProvider.getChangeDBTableFieldsDAO();

        try {
            changeDBTableFieldsDAO.changeRole(id, role);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to change role", e);
        }

    }

    @Override
    public void changePaymentStatus(String status, int userId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeDBTableFieldsDAO changeDBTableFieldsDAO = daoProvider.getChangeDBTableFieldsDAO();

        try {
            changeDBTableFieldsDAO.changePaymentStatus(status, userId);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to change role", e);
        }
    }
}
