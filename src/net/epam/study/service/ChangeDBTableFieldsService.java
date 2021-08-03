package net.epam.study.service;

public interface ChangeDBTableFieldsService {
    void changeStatus(String id, String status) throws ServiceException;
    void changeRole(String id, String role) throws ServiceException;
    void changePaymentStatus(String status, String login) throws ServiceException;
}