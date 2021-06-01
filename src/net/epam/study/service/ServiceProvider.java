package net.epam.study.service;

import net.epam.study.service.impl.*;
import net.epam.study.service.validation.FieldsValidationService;
import net.epam.study.service.validation.impl.FieldsValidationImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final FieldsValidationService fieldsValidationService = new FieldsValidationImpl();
    private final ChangeOrderService changeOrderService = new ChangeOrderImpl();
    private final HashPasswordService hashPasswordService = new HashPasswordImpl();
    private final CheckUserService checkUserService = new CheckUserImpl();
    private final CheckNewUserService checkNewUserService = new CheckNewUserImpl();
    private final TablesListService tablesListService = new TablesListImpl();
    private final OrderCreateService orderCreateService = new OrderCrateImpl();
    private final RemoveOrderService removeOrderService = new RemoveOrderImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return instance;
    }
    public FieldsValidationService getFieldsValidationService(){
        return fieldsValidationService;
    }
    public ChangeOrderService getChangeOrderService(){
        return changeOrderService;
    }
    public HashPasswordService getHashPasswordService(){
        return hashPasswordService;
    }
    public CheckUserService getCheckUserService(){
        return checkUserService;
    }
    public CheckNewUserService getCheckNewUserService(){
        return checkNewUserService;
    }
    public TablesListService getTablesListService(){
        return tablesListService;
    }
    public OrderCreateService getOrderCreateService(){
        return orderCreateService;
    }
    public RemoveOrderService getRemoveOrderService(){
        return removeOrderService;
    }
}
