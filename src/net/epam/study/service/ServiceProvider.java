package net.epam.study.service;

import net.epam.study.service.impl.FieldsValidation;
import net.epam.study.service.impl.RemoveOrderElementImpl;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private final FieldsValidationService fieldsValidationService = new FieldsValidation();
    private final RemoveOrderElementService removeOrderElementService = new RemoveOrderElementImpl();

    private ServiceProvider(){}

    public static ServiceProvider getInstance(){
        return instance;
    }
    public FieldsValidationService getFieldsValidationService(){
        return fieldsValidationService;
    }
    public RemoveOrderElementService getRemoveOrderElementService(){
        return removeOrderElementService;
    }
}
