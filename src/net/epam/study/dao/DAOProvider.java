package net.epam.study.dao;

import net.epam.study.dao.impl.*;

public final class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final CheckUserDAO checkUserDAO = new CheckUserImpl();
    private final NewUserValidateDAO newUserValidateDAO = new NewUserValidateImpl();
    private final DeleteOrderDAO deleteOrderDAO = new DeleteOrderImpl();
    private final OrderCreateDAO orderCreateDAO = new OrderCreateImpl();
    private final TablesListDAO tablesListDAO = new TablesListImpl();
    private final CheckSessionDAO checkSessionDAO = new CheckSessionImpl();

    private DAOProvider(){}

    public static DAOProvider getInstance(){
        return instance;
    }
    public CheckUserDAO getCheckUserDAO(){
        return checkUserDAO;
    }
    public NewUserValidateDAO getNewUserValidateDAO(){
        return newUserValidateDAO;
    }
    public DeleteOrderDAO getDeleteOrderDAO(){
        return deleteOrderDAO;
    }
    public OrderCreateDAO getOrderCreateDAO(){
        return orderCreateDAO;
    }
    public TablesListDAO getTablesListDAO(){
        return tablesListDAO;
    }
    public CheckSessionDAO  getCheckSessionDAO(){
        return checkSessionDAO;
    }
}
