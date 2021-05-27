package net.epam.study.dao.impl;

import net.epam.study.controller.Listener;
import net.epam.study.dao.DeleteOrderDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrderImpl implements DeleteOrderDAO {
    public void delete(String sqlCommand){
        Connection connection = Listener.connection;
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sqlCommand);
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate();
            System.out.println("SUCCESS DB: Order deleted.");
        } catch (SQLException exc) {
            exc.printStackTrace();
            System.out.println("FAIL DB: Fail to write DB.");
        }
    }
}

