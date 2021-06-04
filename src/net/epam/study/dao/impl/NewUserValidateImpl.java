package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.NewUserValidateDAO;
import net.epam.study.dao.connection.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewUserValidateImpl implements NewUserValidateDAO {
    public static final String columnLogin = "login";
    public static final String selectFrom = "select login from users where login =";
    public static final String insertInto = "INSERT INTO users (login,password,role) VALUES";
    private static final Logger log = Logger.getLogger(NewUserValidateImpl.class);

    public boolean validate (String login, String hashPassword, String role) throws DAOException {

        boolean result = true;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {
            log.debug("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(selectFrom + "'" + login + "'");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()
                    &&resultSet.getString(columnLogin).equals(login)) {
                log.debug("FAIL DB: User already exist.");
                result = false;
            } else{
                statement.executeUpdate(insertInto + "('" + login + "','" + hashPassword + "','" + role + "')");
                log.debug("SUCCESS DB: User created.");
            }

        } catch (SQLException exc) {
            log.debug("FAIL DB: Fail to write DB.");
            throw new DAOException(exc);
        } finally {
            ConnectionPool.connectionPool.putBack(connection);
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        return result;
    }
}
