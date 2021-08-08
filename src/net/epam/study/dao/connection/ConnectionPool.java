package net.epam.study.dao.connection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    public static ConnectionPool connectionPool;
    private BlockingQueue<Connection> availableConnections;
    private BlockingQueue<Connection> usedConnections;
    private final String url;
    private final String password;
    private final String userName;
    private final String driver;
    private int initConnCnt;

    public ConnectionPool() throws ConnectionPoolException {
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

    public Connection retrieve() throws ConnectionPoolException {
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

    public void dispose() throws ConnectionPoolException {
        clearConnectionQueue();
    }

    public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {
        try {
            con.close();
            st.close();
            rs.close();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Connection or ResultSet or Statement isn't return to the pool.", e);
            throw new ConnectionPoolException("Connection or ResultSet or Statement isn't return to the pool.", e);
        }
    }

    public void closeConnection(Statement st, ResultSet rs) throws ConnectionPoolException {
        try {
            st.close();
            rs.close();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Statement or ResultSet isn't closed.", e);
            throw new ConnectionPoolException("Statement or ResultSet isn't closed.", e);
        }
    }

    public void closeConnection(Statement st) throws ConnectionPoolException {
        try {
            st.close();
        } catch (SQLException e) {
            log.log(Level.ERROR, "Statement isn't closed.", e);
            throw new ConnectionPoolException("Statement isn't closed.", e);
        }
    }

    private void clearConnectionQueue() throws ConnectionPoolException {
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
            ((PooledConnection) connection).reallyClose();
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

    private class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection c) throws SQLException { this.connection = c; this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection.");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

            if (!usedConnections.remove(this)) {
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }

            if (!availableConnections.offer(this)) {
                throw new SQLException("Error allocating connection in the pool.");
            }
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }
        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }
        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }
        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }
        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }
        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }
        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }
        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException { return connection.createStruct(typeName, attributes);
        }
        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }
        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }
        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }
        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }
        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }
        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }
        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }
        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }
        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }
        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }
        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }
        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }
        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }
        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }
        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException { return connection.prepareStatement(sql);
        }
        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }
        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }
        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }
        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }
        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }
        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }
        @Override
        public void setClientInfo(String name, String value)
                throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException { connection.setHoldability(holdability);
        }


        @Override
        public void setReadOnly(boolean readOnly) throws SQLException { connection.setReadOnly(readOnly);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException { connection.setTransactionIsolation(level);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public void abort(Executor arg0) throws SQLException {
            connection.abort(arg0);

        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void releaseSavepoint(Savepoint arg0) throws SQLException { connection.releaseSavepoint(arg0);
        }

        @Override
        public void rollback(Savepoint arg0) throws SQLException { connection.rollback(arg0);
        }

        @Override
        public void setClientInfo(Properties arg0)
                throws SQLClientInfoException { connection.setClientInfo(arg0);
        }

        @Override
        public void setNetworkTimeout(Executor arg0, int arg1)
                throws SQLException { connection.setNetworkTimeout(arg0, arg1);
        }

        @Override
        public void setSchema(String arg0) throws SQLException { connection.setSchema(arg0);
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException { connection.setTypeMap(arg0);
        }
    }
}
