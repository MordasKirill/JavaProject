package net.epam.study.service;

public interface ChangeOrderService {
    void deleteOrderItem(String item, String login) throws ServiceException;
    void addToOrder(String name, String price, String time);
    double getTotal(String login) throws ServiceException;
    StringBuilder getOrder();
}
