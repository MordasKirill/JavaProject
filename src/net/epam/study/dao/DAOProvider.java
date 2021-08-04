package net.epam.study.dao;

import net.epam.study.dao.impl.*;

public final class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final CheckUserDAO checkUserDAO = new CheckUserImpl();
    private final DeleteTableInfoDAO deleteTableInfoDAO = new DeleteTableInfoImpl();
    private final CreateTableInfoDAO createTableInfoDAO = new CreateTableInfoImpl();
    private final TablesListDAO tablesListDAO = new TablesListImpl();
    private final RetrieveUserDAO retrieveUserDAO = new RetrieveUserImpl();
    private final HashPasswordDAO hashPasswordDAO = new HashPasswordImpl();
    private final ChangeDBTableFieldsDAO changeDBTableFieldsDAO = new ChangeDBTableFieldsImpl();
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
    public CreateTableInfoDAO getCreateTableInfoDAO(){
        return createTableInfoDAO;
    }
    public TablesListDAO getTablesListDAO(){
        return tablesListDAO;
    }
    public RetrieveUserDAO getRetrieveUserDAO(){
        return retrieveUserDAO;
    }
    public HashPasswordDAO getHashPasswordDAO(){
        return hashPasswordDAO;
    }
    public ChangeDBTableFieldsDAO getChangeDBTableFieldsDAO(){
        return changeDBTableFieldsDAO;
    }
    public ChangeOrderDAO getChangeOrderDAO(){
        return changeOrderDAO;
    }
}
