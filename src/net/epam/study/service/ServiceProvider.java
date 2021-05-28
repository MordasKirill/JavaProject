package net.epam.study.service;

import net.epam.study.service.impl.ChangeOrderImpl;
import net.epam.study.service.impl.FieldsValidationImpl;
import net.epam.study.service.impl.HashPasswordImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final FieldsValidationService fieldsValidationService = new FieldsValidationImpl();
    private final ChangeOrderService changeOrderService = new ChangeOrderImpl();
    private final HashPasswordService hashPasswordService = new HashPasswordImpl();

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
}
