package net.epam.study.service.impl;

import net.epam.study.OrderProvider;
import net.epam.study.bean.MenuItem;
import net.epam.study.bean.Order;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.OrderDAO;
import net.epam.study.dao.PaymentDAO;
import net.epam.study.service.OrderService;
import net.epam.study.service.ServiceException;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    final static int DISCOUNT_10_PERCENT = 10;
    final static int DISCOUNT_3_PERCENT = 3;

    /**
     * Method to delete an item from order
     *
     * @param userId user id
     * @param itemName name of item to delete
     *
     */
    public void deleteOrderItem(int userId, String itemName) {
        LinkedList<MenuItem> linkedList = OrderProvider.getInstance().getOrder().get(userId);
        for(MenuItem item : linkedList){
            if (item.getName().equals(itemName)) {
                linkedList.remove(item);
                break;
            }
        }
    }

    /**
     * Add to cart method
     *
     * @param menuItem name of item to be added to cart
     * @param userId user id
     *
     */
    public void addToOrder(MenuItem menuItem, int userId) {
        LinkedList<MenuItem> linkedList =  new LinkedList<>();
        if (OrderProvider.getInstance().getOrder().containsKey(userId)) {
            linkedList = OrderProvider.getInstance().getOrder().get(userId);
        }
        linkedList.add(menuItem);
        OrderProvider.getInstance().getOrder().put(userId, linkedList);
    }

    /**
     * Method to get order total
     *
     * @param userId user login
     * @return returns BigDecimal total price
     * without discount
     */
    public BigDecimal getTotal(int userId) {
        BigDecimal total = new BigDecimal(0);
        List<MenuItem> linkedList = OrderProvider.getInstance().getOrder().get(userId);
        for (MenuItem menuItem : linkedList) {
            BigDecimal totalWithoutDiscount = new BigDecimal(menuItem.getPrice());
            total = total.add(totalWithoutDiscount);
        }
        return total;
    }
    /**
     * Method to apply discount
     *
     * @param totalPrice price without discount
     * @param userId user login
     * @return returns BigDecimal total of order
     * @throws ServiceException exception in service
     *                          throws in case something goes wrong
     */
    public BigDecimal applyDiscount(BigDecimal totalPrice, int userId) throws ServiceException {
        BigDecimal totalWithDiscount = new BigDecimal(String.valueOf(totalPrice));
        int discount = getDiscount(userId);
        if (discount >= 0) {
            BigDecimal amount = new BigDecimal(String.valueOf(totalWithDiscount.multiply(BigDecimal.valueOf(discount))));
            amount = amount.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN);
            return totalWithDiscount.subtract(amount);
        }
        return totalWithDiscount;
    }

    /**
     * Method to get discount
     *
     * @param userId user login
     * @return returns int discount x %
     * @throws ServiceException exception in service
     *                          throws in case something goes wrong
     */
    public int getDiscount(int userId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        int successOrders = 0;
        try {
            successOrders = paymentDAO.getDonePayments(userId);
        } catch (DAOException e) {
            throw new ServiceException("Get discount fail", e);
        }
        if (successOrders >= 3 && successOrders < 10) {
            return DISCOUNT_3_PERCENT;
        } else if (successOrders >= 10) {
            return DISCOUNT_10_PERCENT;
        }
        return 0;
    }

    /**
     * Get order method
     *
     * @return returns a StringBuilder value
     * of all products in a cart
     */
    public String orderToString(Map<Integer, LinkedList<MenuItem>> order, int userId) {
        LinkedList<MenuItem> linkedList = order.get(userId);
        return linkedList.toString();
    }

    @Override
    public List<Order> getOrderDetailsWithLimit(int limit) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            return changeOrder.getOrderDetailsWithLimit(limit);
        } catch (DAOException e) {
            throw new ServiceException("Get orders fail", e);
        }
    }

    @Override
    public List<Order> getOrderDetails(int userId) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            return changeOrder.getOrderDetails(userId);
        } catch (DAOException e) {
            throw new ServiceException("Get orders fail", e);
        }
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            return changeOrder.getAllOrders();
        } catch (DAOException e) {
            throw new ServiceException("Get orders fail", e);
        }
    }


    @Override
    public int createOrder(Order order) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            return changeOrder.createOrder(order);
        } catch (DAOException e) {
            throw new ServiceException("Order create fail", e);
        }
    }

    @Override
    public void changeOrderStatus(String status, int id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            changeOrder.changeOrderStatus(status, id);
        } catch (DAOException e) {
            throw new ServiceException("Fail to change order status", e);
        }
    }

    @Override
    public void deleteOrder(String id) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            changeOrder.deleteOrder(id);
        } catch (DAOException e) {
            throw new ServiceException("Fail to delete order", e);
        }
    }
}
