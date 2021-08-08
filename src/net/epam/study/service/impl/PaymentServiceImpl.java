package net.epam.study.service.impl;

import net.epam.study.bean.Order;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.PaymentDAO;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.PaymentService;
import net.epam.study.service.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public int getDonePayments(int userId) throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

        int result;

        try {
            result = paymentDAO.getDonePayments(userId);
            return result;

        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Get all orders fail", e);
        }

    }

    @Override
    public void doPayment(int userId, int orderId, BigDecimal total, String status) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();

        try {
            paymentDAO.doPayment(userId, orderId, total, status);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Payment create fail", e);
        }
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            return paymentDAO.getAllOrders();
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Get all orders fail", e);
        }
    }

    @Override
    public void changeOrderStatus(String status, int id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            paymentDAO.changePaymentStatus(status, id);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Change order status fail", e);
        }
    }
}
