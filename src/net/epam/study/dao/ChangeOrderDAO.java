package net.epam.study.dao;

import net.epam.study.bean.MenuItem;
import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;

public interface ChangeOrderDAO {
    void deleteOrderItem(String item, String login)throws DAOException;
    void addToOrder(MenuItem menuItem);
    BigDecimal getTotal(int userId) throws DAOException, ConnectionPoolException;
    int getDiscount(int userId) throws DAOException, ConnectionPoolException;
    StringBuilder getOrder();
}
