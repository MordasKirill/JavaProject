package net.epam.study.service;

import net.epam.study.service.impl.*;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final ValidationService validationService = new ValidationImpl();
    private final ManageOrderService manageOrderService = new ManageOrderImpl();
    private final CheckUserService checkUserService = new CheckUserImpl();
    private final TablesListService tablesListService = new TablesListImpl();
    private final CreateTableInfoService createTableInfoService = new CreateTableInfoImpl();
    private final DeleteTableInfoService deleteTableInfoService = new DeleteTableInfoImpl();
    private final HashPasswordService hashPasswordService = new HashPasswordImpl();
    private final RetrieveUserService retrieveUserService = new RetrieveUserImpl();
    private final ChangeDBTableFieldsService changeDBTableFieldsService = new ChangeDBTableFieldsImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return instance;
    }
    public ValidationService getValidationService(){
        return validationService;
    }
    public ManageOrderService getManageOrderService(){
        return manageOrderService;
    }
    public CheckUserService getCheckUserService(){
        return checkUserService;
    }
    public TablesListService getTablesListService(){
        return tablesListService;
    }
    public CreateTableInfoService getCreateTableInfoService(){
        return createTableInfoService;
    }
    public DeleteTableInfoService getDeleteTableInfoService(){
        return deleteTableInfoService;
    }
    public HashPasswordService getHashPasswordService(){
        return hashPasswordService;
    }
    public RetrieveUserService getRetrieveUserService(){
        return retrieveUserService;
    }
    public ChangeDBTableFieldsService getChangeDBTableFieldsService(){
        return changeDBTableFieldsService;
    }
}
