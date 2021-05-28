package net.epam.study.service;

public interface ChangeOrderService {
    void delete(String item);
    void addToList(String name, String price, String time);
    double getTotal();
}
