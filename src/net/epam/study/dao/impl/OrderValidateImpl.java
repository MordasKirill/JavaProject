package net.epam.study.dao.impl;

import net.epam.study.listener.Listener;
import net.epam.study.dao.OrderValidateDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderValidateImpl implements OrderValidateDAO {
    public static final String insertInto = "INSERT INTO orders (fullName,address,email,phone,details) VALUES";
    public static String error;
    public void validate (String fullName, String address, String email, String phone, StringBuilder stringBuilder)  {
        Connection connection = Listener.connectionPool.retrieve();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(insertInto + "('" + fullName + "','" + address + "','" + email + "','" + phone + "','" + stringBuilder + "')");
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate();
            System.out.println("SUCCESS DB: Order created.");
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to validate order !";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        Listener.connectionPool.putBack(connection);
    }
}
