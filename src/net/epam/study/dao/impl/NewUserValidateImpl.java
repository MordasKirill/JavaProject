package net.epam.study.dao.impl;

import net.epam.study.dao.DAOException;
import net.epam.study.dao.NewUserValidateDAO;
import net.epam.study.dao.connection.ConnectionPool;
import net.epam.study.dao.connection.ConnectionPoolException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewUserValidateImpl implements NewUserValidateDAO {
    public static final String COLUMN_LOGIN = "login";
    public static final String SELECT_FROM = "select login from users where login =";
    public static final String INSERT_INTO = "INSERT INTO users (login,password,role) VALUES";
    private static final Logger log = Logger.getLogger(NewUserValidateImpl.class);

    public boolean validate (String login, String hashPassword, String role) throws DAOException, ConnectionPoolException {

        boolean result = true;
        Connection connection = ConnectionPool.connectionPool.retrieve();
        PreparedStatement statement = null;

        try {
            log.info("SUCCESS DB: Connected.");
            statement = connection.prepareStatement(SELECT_FROM + "'" + login + "'");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()
                    &&resultSet.getString(COLUMN_LOGIN).equals(login)) {
                log.info("FAIL DB: User already exist.");
                result = false;
            } else{
                statement.executeUpdate(INSERT_INTO + "('" + login + "','" + hashPassword + "','" + role + "')");
                log.info("SUCCESS DB: User created.");
            }

        } catch (SQLException exc) {

            log.log(Level.ERROR,"FAIL DB: Fail to write DB.", exc);
            throw new DAOException(exc);
        } finally {

            ConnectionPool.connectionPool.putBack(connection);
            assert statement != null;
            ConnectionPool.connectionPool.closeConnection(statement);
        }

        return result;
    }
}
