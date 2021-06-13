package net.epam.study.service;

public interface ChangeTableInfoService {
    void changeStatus(String id, String status) throws ServiceException;
    void changeRole(String id, String role) throws ServiceException;
    void changePaymentStatus(String status) throws ServiceException;
}
