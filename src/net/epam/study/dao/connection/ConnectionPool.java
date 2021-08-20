package net.epam.study.dao.connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    public static ConnectionPool connectionPool;
    public static BlockingQueue<Connection> availableConnections;
    public static BlockingQueue<Connection> usedConnections;
    private final String url;
    private final String password;
    private final String userName;
    private final String driver;
    private int initConnCnt;

    public ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driver = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.userName = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.initConnCnt = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
        } catch (NumberFormatException e) {
            initConnCnt = 5;
        }
    }

    public Connection retrieve() {
        Connection newConn = getConnection();
        if (availableConnections.size() != 0) {
            try {
                newConn = availableConnections.take();
                availableConnections.remove(newConn);
            } catch (InterruptedException e) {
                log.log(Level.ERROR, "Error connecting to the data source.", e);
                throw new ConnectionPoolException("Error connecting to the data source.", e);
            }
        }
        usedConnections.add(newConn);
        return newConn;
    }

    public void putBack(Connection connection) {
        if (connection != null) {
            if (usedConnections.remove(connection)) {
                availableConnections.add(connection);
            }
        }
    }

    public void dispose() {
        clearConnectionQueue();
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) {
        try {
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Connection or ResultSet or Statement isn't return to the pool.", e);
            throw new ConnectionPoolException("Connection or ResultSet or Statement isn't return to the pool.", e);
        }
    }

    public void closeConnection(Statement st, ResultSet rs) {
        try {
            rs.close();
            st.close();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Statement or ResultSet isn't closed.", e);
            throw new ConnectionPoolException("Statement or ResultSet isn't closed.", e);
        }
    }

    public void closeConnection(Statement st) {
        try {
            st.close();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Statement isn't closed.", e);
            throw new ConnectionPoolException("Statement isn't closed.", e);
        }
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionsQueue(usedConnections);
            closeConnectionsQueue(availableConnections);
        } catch (SQLException e) {
            log.log(Level.ERROR, "Error closing the connection.", e);
            throw new ConnectionPoolException("Error closing the connection.", e);
        }
    }

    private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            connection.close();
        }
    }

    private Connection getConnection() throws ConnectionPoolException {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            PooledConnection pooledConnection = new PooledConnection(conn);
            availableConnections = new ArrayBlockingQueue<Connection>(initConnCnt);
            usedConnections = new ArrayBlockingQueue<Connection>(initConnCnt);
            for (int i = 0; i < initConnCnt; i++) {
                availableConnections.add(pooledConnection);
            }
        } catch (Exception e) {
            log.log(Level.ERROR, "Driver error.", e);
            throw new ConnectionPoolException("Driver error.", e);
        }
        return conn;
    }
}
