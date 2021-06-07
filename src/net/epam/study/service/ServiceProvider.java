package net.epam.study.service;

import net.epam.study.service.impl.*;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final ValidationService validationService = new ValidationImpl();
    private final ChangeOrderService changeOrderService = new ChangeOrderImpl();
    private final CheckUserService checkUserService = new CheckUserImpl();
    private final CheckNewUserService checkNewUserService = new CheckNewUserImpl();
    private final TablesListService tablesListService = new TablesListImpl();
    private final OrderCreateService orderCreateService = new OrderCrateImpl();
    private final RemoveOrderService removeOrderService = new RemoveOrderImpl();
    private final HashPasswordService hashPasswordService = new HashPasswordImpl();
    private final CheckSessionService checkSessionService = new CheckSessionImpl();
    private final ChangeOrderStatusService changeOrderStatusService = new ChangeOrderStatusImpl();

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
    public HashPasswordService getHashPasswordService(){
        return hashPasswordService;
    }
    public CheckSessionService getCheckSessionService(){
        return checkSessionService;
    }
    public ChangeOrderStatusService getChangeOrderStatusService(){
        return changeOrderStatusService;
    }
}
