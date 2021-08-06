package net.epam.study.service.impl;

import net.epam.study.Constants;
import net.epam.study.bean.MenuItem;
import net.epam.study.bean.Order;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderDAO;
import net.epam.study.dao.PaymentDAO;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    /**
     * Method to delete an item from order
     *
     * @param item name of item to delete
     *             throws in case something goes wrong
     */
    public void deleteOrderItem(String item) {
        for (int i = 0; i < Constants.ORDER.size(); i++) {
            if (Constants.ORDER.get(i).toString().equals(item)) {
                Constants.ORDER.remove(Constants.ORDER.get(i));
                Constants.TOTAL.remove(Constants.TOTAL.get(i));
                break;
            }
        }
    }

    /**
     * Add to cart method
     *
     * @param menuItem name of item to be added to cart
     */
    public void addToOrder(MenuItem menuItem) {
        if (menuItem.getName() != null && menuItem.getPrice() != null && menuItem.getFilingTime() != null) {
            Constants.ORDER.add(menuItem);
            Constants.TOTAL.add(menuItem.getPrice());
        }
    }

    /**
     * Method to get order total
     *
     * @param userId user login
     * @return returns BigDecimal total of order
     * @throws DAOException            exception in dao
     *                                 throws in case something goes wrong
     * @throws ConnectionPoolException in case problems
     *                                 with open/retrieve connection
     */
    public BigDecimal getTotal(int userId) {

        BigDecimal result = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);
        int discount = 0;

        for (int i = 0; i < Constants.TOTAL.size(); i++) {
            BigDecimal total = new BigDecimal(Constants.TOTAL.get(i));
            sum = sum.add(total);

            discount = getDiscount(userId);
            if (discount >= 3) {
                BigDecimal amount = new BigDecimal(String.valueOf(sum.multiply(BigDecimal.valueOf(discount))));
                amount = amount.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN);
                result = sum.subtract(amount);
            }

            if (discount < 3) {
                result = sum;
            }
        }
        return result;
    }

    public int getDiscount(int userId) {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        int total = 0;
        total = paymentDAO.getDonePayments(userId);
        if (total == 3) {
            return 3;
        } else if (total >= 10) {
            return 10;
        }
        return 0;
    }

    /**
     * Get order method
     *
     * @return returns a StringBuilder value
     * of all products in a cart
     */
    public StringBuilder getOrder() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Constants.ORDER.size(); i++) {
            stringBuilder.append(Constants.ORDER.get(i).toString()).append(" ");
        }
        return stringBuilder;
    }

    @Override
    public List<Order> getOrders(int limit) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        List<Order> orders;
        try {
            orders = changeOrder.getOrders(limit);
            return orders;

        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Get orders fail", e);
        }
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {

        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        List<Order> orders;
        try {
            orders = changeOrder.getAllOrders();
            return orders;

        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Get all orders fail", e);
        }
    }

    @Override
    public int createOrder(Order order) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        int orderId = 0;
        try {
            orderId = changeOrder.createOrder(order);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Order create fail", e);
        }
        return orderId;
    }

    @Override
    public void changeOrderStatus(String status, int id) {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        changeOrder.changeOrderStatus(status, id);
    }

    @Override
    public void deleteOrder(String id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            changeOrder.deleteOrder(id);
        } catch (DAOException | ConnectionPoolException e) {
            throw new ServiceException("Fail to delete order", e);
        }
    }

}
