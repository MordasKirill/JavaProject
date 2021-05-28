package net.epam.study.service;

import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.impl.FieldsValidation;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final FieldsValidationService fieldsValidationService = new FieldsValidation();
    private final ChangeOrderService changeOrderService = new ChangeOrderImpl();

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
}
