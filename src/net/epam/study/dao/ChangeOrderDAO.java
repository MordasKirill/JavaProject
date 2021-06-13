package net.epam.study.dao;

import net.epam.study.dao.connection.ConnectionPoolException;

import java.math.BigDecimal;

public interface ChangeOrderDAO {
    void deleteOrderItem(String item, String login)throws DAOException;
    void addToOrder(String name, String price, String time);
    BigDecimal getTotal(String login) throws DAOException, ConnectionPoolException;
    StringBuilder getOrder();
}
