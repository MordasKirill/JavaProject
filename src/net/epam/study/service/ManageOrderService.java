package net.epam.study.service;

import java.math.BigDecimal;

public interface ManageOrderService {
    void deleteOrderItem(String item, String login) throws ServiceException;
    void addToOrder(String name, String price, String time);
    BigDecimal getTotal(int userId) throws ServiceException;
    int getDiscount(int userId) throws ServiceException;
    StringBuilder getOrder();
}
