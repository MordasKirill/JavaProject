package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.OrderCreateDAO;
import net.epam.study.dao.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderCreateImpl implements OrderCreateDAO {
    public static final String insertInto = "INSERT INTO orders (fullName,address,email,phone,details) VALUES";

    public void create(String fullName, String address, String email, String phone, StringBuilder stringBuilder) throws DAOException {
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(insertInto + "('" + fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "')");
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate();
            System.out.println("SUCCESS DB: Order created.");

        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DAOException(e);
            }
        }
    }
}
