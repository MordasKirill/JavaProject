package net.epam.study.service;

public interface DeleteTableInfoService {
    void deleteOrder(String id) throws ServiceException;
    void deleteUser(String id) throws ServiceException;
}
