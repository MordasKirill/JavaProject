package net.epam.study.service.impl;

import net.epam.study.bean.Order;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.PaymentDAO;
import net.epam.study.service.PaymentService;
import net.epam.study.service.ServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public int getDonePayments(int userId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            return paymentDAO.getDonePayments(userId);
        } catch (DAOException e) {
            throw new ServiceException("Get all orders fail", e);
        }

    }

    @Override
    public void doPayment(int userId, int orderId, BigDecimal total, String status) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            paymentDAO.doPayment(userId, orderId, total, status);
        } catch (DAOException e) {
            throw new ServiceException("Payment create fail", e);
        }
    }

    @Override
    public List<Order> getAllPayments(int userId, int limit) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            return paymentDAO.getAllPayments(userId, limit);
        } catch (DAOException e) {
            throw new ServiceException("Get all orders fail", e);
        }
    }

    @Override
    public void changeOrderStatus(String status, int id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        try {
            paymentDAO.changePaymentStatus(status, id);
        } catch (DAOException e) {
            throw new ServiceException("Change order status fail", e);
        }
    }

    public List<Order> getDetailsForCurrentUser(List<Order> allOrders, List<Order> userOrders){
        List<Order> currentUserDetails = new ArrayList<>();
        for (Order allOrder : allOrders) {
            for (Order userOrder : userOrders) {
                if (allOrder.getId().equals(userOrder.getId())) {
                    currentUserDetails.add(allOrder);
                    break;
                }
            }
        }
        return currentUserDetails;
    }
}
