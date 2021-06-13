package net.epam.study.dao;

import net.epam.study.dao.impl.*;

public final class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final CheckUserDAO checkUserDAO = new CheckUserImpl();
    private final DeleteTableInfoDAO deleteTableInfoDAO = new DeleteTableInfoImpl();
    private final OrderCreateDAO orderCreateDAO = new OrderCreateImpl();
    private final TablesListDAO tablesListDAO = new TablesListImpl();
    private final CheckSessionDAO checkSessionDAO = new CheckSessionImpl();
    private final HashPasswordDAO hashPasswordDAO = new HashPasswordImpl();
    private final ChangeTableInfoDAO changeTableInfoDAO = new ChangeTableInfoImpl();
    private final ChangeOrderDAO changeOrderDAO = new ChangeOrderImpl();

    private DAOProvider(){}

    public static DAOProvider getInstance(){
        return instance;
    }
    public CheckUserDAO getCheckUserDAO(){
        return checkUserDAO;
    }
    public DeleteTableInfoDAO getDeleteTableInfoDAO(){
        return deleteTableInfoDAO;
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
    public HashPasswordDAO getHashPasswordDAO(){
        return hashPasswordDAO;
    }
    public ChangeTableInfoDAO getChangeTableInfoDAO(){
        return changeTableInfoDAO;
    }
    public ChangeOrderDAO getChangeOrderDAO(){
        return changeOrderDAO;
    }
}
