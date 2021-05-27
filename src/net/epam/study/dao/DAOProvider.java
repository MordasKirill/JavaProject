package net.epam.study.dao;

import net.epam.study.dao.impl.*;

public final class DAOProvider {

    private static final DAOProvider instance = new DAOProvider();

    private final UserLoginValidateDAO loginAndPasswordValidate = new LoginAndPasswordValidateImpl();
    private final NewUserValidateDAO newUserValidateDAO = new NewUserValidateImpl();
    private final DeleteOrderDAO deleteOrderDAO = new DeleteOrderImpl();
    private final OrderValidateDAO orderValidateDAO = new OrderValidateImpl();
    private final ShowTablesDAO showTablesDAO = new ShowTablesImpl();
    private final CheckSessionDAO checkSessionDAO = new CheckSessionImpl();

    private DAOProvider(){}

    public static DAOProvider getInstance(){
        return instance;
    }
    public UserLoginValidateDAO getLoginAndPasswordValidate(){
        return loginAndPasswordValidate;
    }
    public NewUserValidateDAO getNewUserValidateDAO(){
        return newUserValidateDAO;
    }
    public DeleteOrderDAO getDeleteOrderDAO(){
        return deleteOrderDAO;
    }
    public OrderValidateDAO getOrderValidateDAO(){
        return orderValidateDAO;
    }
    public ShowTablesDAO getShowTablesDAO(){
        return showTablesDAO;
    }
    public CheckSessionDAO  getCheckSessionDAO(){
        return checkSessionDAO;
    }
}
