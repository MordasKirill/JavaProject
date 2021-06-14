package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.service.OrderCreateService;
import net.epam.study.service.ServiceException;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;

public class OrderCreateImpl implements OrderCreateService {
    @Override
    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderCreateDAO orderCreateDAO = daoProvider.getOrderCreateDAO();

        try {
            orderCreateDAO.create(fullName, address, email, phone, stringBuilder);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Order create fail", e);
        }
    }

    @Override
    public void payment(String login, BigDecimal total, String status) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderCreateDAO orderCreateDAO = daoProvider.getOrderCreateDAO();

        try {
            orderCreateDAO.payment(login, total, status);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Payment create fail", e);
        }
    }

    @Override
    public int getUserId(String login) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderCreateDAO orderCreateDAO = daoProvider.getOrderCreateDAO();

        int result = 0;

        try {

            result = orderCreateDAO.getUserId(login);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Payment create fail", e);
        }
        return result;
    }
}
