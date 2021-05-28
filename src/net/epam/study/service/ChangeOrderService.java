package net.epam.study.service;

public interface ChangeOrderService {
    void deleteOrderItem(String item);
    void addToOrder(String name, String price, String time);
    double getTotal();
    StringBuilder getOrder();
}
