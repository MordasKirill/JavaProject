package net.epam.study.service;

public interface ChangeOrderStatusService {
    void accept(String id, String status) throws ServiceException;
}
