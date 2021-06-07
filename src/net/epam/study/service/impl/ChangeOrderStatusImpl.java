package net.epam.study.service.impl;

import net.epam.study.dao.ChangeOrderStatusDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.ChangeOrderStatusService;
import net.epam.study.service.ServiceException;

public class ChangeOrderStatusImpl implements ChangeOrderStatusService {

    @Override
    public void accept(String id, String status) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeOrderStatusDAO changeOrderStatusDAO = daoProvider.getChangeOrderStatusDAO();

        try {
            changeOrderStatusDAO.accept(id, status);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Fail to change status", e);
        }

    }
}
