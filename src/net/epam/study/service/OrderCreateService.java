package net.epam.study.service;

public interface OrderCreateService {
    void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws ServiceException;
}
