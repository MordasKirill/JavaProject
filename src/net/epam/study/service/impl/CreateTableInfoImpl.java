package net.epam.study.service.impl;

import net.epam.study.bean.Order;
import net.epam.study.dao.CreateTableInfoDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.service.CreateTableInfoService;
import net.epam.study.service.ServiceException;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;

public class CreateTableInfoImpl implements CreateTableInfoService {

    public static Order order;

    @Override
    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        CreateTableInfoDAO createTableInfoDAO = daoProvider.getCreateTableInfoDAO();

        try {
            createTableInfoDAO.createOrder(fullName, address, email, phone, stringBuilder);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Order create fail", e);
        }
    }

    @Override
    public void doPayment(String login, BigDecimal total, String status) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        CreateTableInfoDAO createTableInfoDAO = daoProvider.getCreateTableInfoDAO();

        try {
            createTableInfoDAO.doPayment(login, total, status);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Payment create fail", e);
        }
    }

    @Override
    public void createMenuItem(String itemName, String price, String waitTime, String category) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        CreateTableInfoDAO createTableInfoDAO = daoProvider.getCreateTableInfoDAO();

        try {
            createTableInfoDAO.createMenuItem(itemName, price, waitTime, category);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Menu item create fail", e);
        }
    }

    @Override
    public int getUserId(String login) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        CreateTableInfoDAO createTableInfoDAO = daoProvider.getCreateTableInfoDAO();

        int result = 0;

        try {

            result = createTableInfoDAO.getUserId(login);
        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Payment create fail", e);
        }
        return result;
    }
}
