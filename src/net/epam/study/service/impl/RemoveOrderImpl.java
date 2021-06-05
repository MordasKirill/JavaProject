package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.DeleteOrderDAO;
import net.epam.study.service.RemoveOrderService;
import net.epam.study.service.ServiceException;
import net.epam.study.dao.connection.ConnectionPoolException;

public class RemoveOrderImpl implements RemoveOrderService {
    @Override
    public void delete(String id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        DeleteOrderDAO deleteOrderDAO = daoProvider.getDeleteOrderDAO();
        try {
            deleteOrderDAO.delete(id);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to delete order", e);
        }
    }
}
