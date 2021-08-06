package net.epam.study.service;

import net.epam.study.service.impl.*;
import net.epam.study.service.validation.ValidationService;
import net.epam.study.service.validation.impl.ValidationImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final ValidationService validationService = new ValidationImpl();
    private final OrderService orderService = new OrderServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final PaginationService paginationService = new PaginationImpl();
    private final MenuService menuService = new MenuServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public UserService getUserService() {
        return userService;
    }

    public PaginationService getPaginationService() {
        return paginationService;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }
}
