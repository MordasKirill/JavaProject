package net.epam.study.dao;

import net.epam.study.dao.impl.DBCommonCRUDOperationImpl;
import net.epam.study.dao.impl.MenuDAOImpl;
import net.epam.study.dao.impl.OrderDAOImpl;
import net.epam.study.dao.impl.UserDAOImpl;

public final class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final UsersDAO usersDAO = new UserDAOImpl();
    private final DBCommonCRUDOperationDAO DBCommonCRUDOperationDAO = new DBCommonCRUDOperationImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final MenuDAO menuDAO = new MenuDAOImpl();
    private final PaymentDAO paymentDAO = new net.epam.study.dao.impl.PaymentDAOImpl();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UsersDAO getUsersDAO() {
        return usersDAO;
    }

    public DBCommonCRUDOperationDAO getDBCommonCRUDOperationDAO() {
        return DBCommonCRUDOperationDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public MenuDAO getMenuDAO() {
        return menuDAO;
    }

    public PaymentDAO getPaymentDAO() {
        return paymentDAO;
    }
}
