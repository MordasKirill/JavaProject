package net.epam.study.dao.impl;

import net.epam.study.listener.Listener;
import net.epam.study.dao.DeleteOrderDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteOrderImpl implements DeleteOrderDAO {
    public static final String deleteFrom = "delete from orders where id =";
    public static String error;
    public void delete(String id){
        Connection connection = Listener.connectionPool.retrieve();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(deleteFrom + "'" + id + "'");
            System.out.println("SUCCESS DB: Connected.");
            statement.executeUpdate();
            System.out.println("SUCCESS DB: Order deleted.");
        } catch (SQLException exc) {
            exc.printStackTrace();
            error = "Failed to delete order !";
            System.out.println("FAIL DB: Fail to write DB.");
        }
        Listener.connectionPool.putBack(connection);
    }
}

