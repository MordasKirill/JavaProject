package net.epam.study.service;

import net.epam.study.service.impl.*;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final ValidationService validationService = new ValidationImpl();
    private final ChangeOrderService changeOrderService = new ChangeOrderImpl();
    private final CheckUserService checkUserService = new CheckUserImpl();
    private final TablesListService tablesListService = new TablesListImpl();
    private final CreateTableInfoService createTableInfoService = new CreateTableInfoImpl();
    private final DeleteTableInfoService deleteTableInfoService = new DeleteTableInfoImpl();
    private final HashPasswordService hashPasswordService = new HashPasswordImpl();
    private final CheckSessionService checkSessionService = new CheckSessionImpl();
    private final ChangeTableInfoService changeTableInfoService = new ChangeTableInfoImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return instance;
    }
    public ValidationService getValidationService(){
        return validationService;
    }
    public ChangeOrderService getChangeOrderService(){
        return changeOrderService;
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
    public CheckSessionService getCheckSessionService(){
        return checkSessionService;
    }
    public ChangeTableInfoService getChangeTableInfoService(){
        return changeTableInfoService;
    }
}
