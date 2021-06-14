package net.epam.study.service;

import java.math.BigDecimal;

public interface OrderCreateService {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws ServiceException;
    void payment(String login, BigDecimal total, String status) throws ServiceException;
    int getUserId(String login) throws ServiceException;
}
