package net.epam.study.service;

public interface OrderCreateService {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder, String login, double total) throws ServiceException;
    void payment(String login, double total, String status) throws ServiceException;
}
