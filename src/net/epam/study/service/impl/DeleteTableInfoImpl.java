package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.DeleteTableInfoDAO;
import net.epam.study.service.DeleteTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.dao.connection.ConnectionPoolException;

public class DeleteTableInfoImpl implements DeleteTableInfoService {
    @Override
    public void deleteOrder(String id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        DeleteTableInfoDAO deleteTableInfoDAO = daoProvider.getDeleteTableInfoDAO();

        try {
            deleteTableInfoDAO.deleteOrder(id);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to delete order", e);
        }
    }

    @Override
    public void deleteUser(String id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        DeleteTableInfoDAO deleteTableInfoDAO = daoProvider.getDeleteTableInfoDAO();

        try {
            deleteTableInfoDAO.deleteUser(id);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to delete user", e);
        }
    }

    @Override
    public void deleteMenuItem(String itemName, String category) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        DeleteTableInfoDAO deleteTableInfoDAO = daoProvider.getDeleteTableInfoDAO();

        try {
            deleteTableInfoDAO.deleteMenuItem(itemName, category);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to delete user", e);
        }
    }

    @Override
    public boolean isMenuItemExists(String itemName, String category) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        DeleteTableInfoDAO deleteTableInfoDAO = daoProvider.getDeleteTableInfoDAO();

        boolean result;

        try {
            result = deleteTableInfoDAO.isMenuItemExists(itemName, category);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to delete user", e);
        }
        return result;
    }
}
