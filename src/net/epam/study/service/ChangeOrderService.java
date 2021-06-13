package net.epam.study.service;

import java.math.BigDecimal;

public interface ChangeOrderService {
    void deleteOrderItem(String item, String login) throws ServiceException;
    void addToOrder(String name, String price, String time);
    BigDecimal getTotal(String login) throws ServiceException;
    StringBuilder getOrder();
}
