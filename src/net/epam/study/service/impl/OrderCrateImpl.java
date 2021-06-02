package net.epam.study.service.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.service.OrderCreateService;
import net.epam.study.service.ServiceException;

public class OrderCrateImpl implements OrderCreateService {
    @Override
    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderCreateDAO orderCreateDAO = daoProvider.getOrderCreateDAO();
        try {
            orderCreateDAO.create(fullName, address, email, phone, stringBuilder);
        } catch (DAOException e){
            throw new ServiceException("Order create fail", e);
        }
    }
}
