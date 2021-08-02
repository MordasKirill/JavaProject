package net.epam.study.service.impl;

import net.epam.study.dao.ChangeOrderDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.DAOProvider;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.bean.MenuItem;
import net.epam.study.service.ChangeOrderService;
import net.epam.study.service.ServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChangeOrderImpl implements ChangeOrderService {
    public static final List<MenuItem> ORDER = new ArrayList<>();
    public static final List<String> TOTAL = new ArrayList<>();

    public void deleteOrderItem(String item, String login) throws ServiceException{
        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeOrderDAO changeOrder = daoProvider.getChangeOrderDAO();

        try {
            changeOrder.deleteOrderItem(item, login);
        } catch (DAOException e){
        throw new ServiceException("Get orders fail", e);
        }
    }

    public void addToOrder(String name, String price, String time){
        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeOrderDAO changeOrder = daoProvider.getChangeOrderDAO();

        changeOrder.addToOrder(name, price, time);
    }

    public BigDecimal getTotal(String login) throws ServiceException{
        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeOrderDAO changeOrder = daoProvider.getChangeOrderDAO();

        BigDecimal total;


        try {
            total = changeOrder.getTotal(login);

        } catch (DAOException | ConnectionPoolException e){
            throw new ServiceException("Get orders fail", e);
        }

        return total;
    }

    public StringBuilder getOrder(){
        DAOProvider daoProvider = DAOProvider.getInstance();
        ChangeOrderDAO changeOrder = daoProvider.getChangeOrderDAO();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = changeOrder.getOrder();
        return stringBuilder;

    }
}
