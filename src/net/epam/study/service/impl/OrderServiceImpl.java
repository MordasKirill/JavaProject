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
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.validation.ValidationService;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    /**
     * Method to delete an item from order
     *
     * @param item name of item to delete
     *             throws in case something goes wrong
     */
    public void deleteOrderItem(String item, int userId, String itemName, String itemPrice) {
        LinkedList<MenuItem> linkedList = OrderProvider.getInstance().getOrder().get(userId);
        MenuItem menuItem = new MenuItem(itemName, itemPrice);
        for (int i = 0; i < linkedList.size(); i++){
            if (menuItem.equals(linkedList.get(i))){
                linkedList.remove(linkedList.get(i));
                OrderProvider.getInstance().getOrder().put(userId, linkedList);
                break;
            }
        }
    }

    /**
     * Add to cart method
     *
     * @param menuItem name of item to be added to cart
     */
    public void addToOrder(MenuItem menuItem, int userId) {
        if (menuItem.getName() != null && menuItem.getPrice() != null) {
            if (OrderProvider.getInstance().getOrder().get(userId) == null){
                LinkedList<MenuItem> linkedList = new LinkedList<>();
                linkedList.add(menuItem);
                OrderProvider.getInstance().getOrder().put(userId, linkedList);
            } else {
                LinkedList<MenuItem> linkedList = OrderProvider.getInstance().getOrder().get(userId);
                linkedList.add(menuItem);
                OrderProvider.getInstance().getOrder().put(userId, linkedList);
            }
        }
    }

    /**
     * Method to get order total
     *
     * @param userId user login
     * @return returns BigDecimal total of order
     * @throws ServiceException        exception in service
     *                                 throws in case something goes wrong
     */
    public BigDecimal getTotal(int userId) throws ServiceException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ValidationService validationService = serviceProvider.getValidationService();
        BigDecimal result = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);
        int discount = 0;
        LinkedList<MenuItem> linkedList = new LinkedList<>();
        if (validationService.isParamNotNull(OrderProvider.getInstance().getOrder())) {
            linkedList = OrderProvider.getInstance().getOrder().get(userId);
        }
        for (MenuItem menuItem : linkedList) {
            BigDecimal total = new BigDecimal(menuItem.getPrice());
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

    public int getDiscount(int userId) throws ServiceException{
        DAOProvider daoProvider = DAOProvider.getInstance();
        PaymentDAO paymentDAO = daoProvider.getPaymentDAO();
        int total = 0;
        try {
            total = paymentDAO.getDonePayments(userId);
        } catch (DAOException e) {
            throw new ServiceException("Get discount fail", e);
        }
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

    public String orderToString(Map<Integer, LinkedList<MenuItem>> order, int userId){
        LinkedList<MenuItem> linkedList = order.get(userId);
        return linkedList.toString();
    }

    @Override
    public List<Order> getOrders(int limit) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            return changeOrder.getOrders(limit);

        } catch (DAOException e) {
            throw new ServiceException("Get orders fail", e);
        }
    }


    @Override
    public int createOrder(String fullName, String address, String email, String phone, String details) throws ServiceException {
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        int orderId = 0;
        try {
            orderId = changeOrder.createOrder(new Order(fullName, address, email, phone, details));
        } catch (DAOException e) {
            throw new ServiceException("Order create fail", e);
        }
        return orderId;
    }

    @Override
    public void changeOrderStatus(String status, int id)throws ServiceException{
        DAOProvider daoProvider = DAOProvider.getInstance();
        OrderDAO changeOrder = daoProvider.getOrderDAO();
        try {
            changeOrder.changeOrderStatus(status, id);
        }catch (DAOException e) {
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
