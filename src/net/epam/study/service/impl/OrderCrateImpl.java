package net.epam.study.service.impl;

import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.service.OrderCreateService;

public class OrderCrateImpl implements OrderCreateService {
    @Override
    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderCreateDAO orderCreateDAO = daoProvider.getOrderCreateDAO();
        orderCreateDAO.create(fullName, address, email, phone, stringBuilder);
    }
}
