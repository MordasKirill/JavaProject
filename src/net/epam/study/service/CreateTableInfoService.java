package net.epam.study.service;

import java.math.BigDecimal;

public interface CreateTableInfoService {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws ServiceException;
    void doPayment(String login, BigDecimal total, String status) throws ServiceException;
    int getUserId(String login) throws ServiceException;
    void createMenuItem(String itemName, String price, String waitTime, String category) throws ServiceException;
}
