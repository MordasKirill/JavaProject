package net.epam.study.service;

public interface FieldsValidationService {
    boolean isValidEmailAddress(String email);
    boolean isValidFullName(String fullName);
    boolean isValidPhoneNumber(String phone);
    boolean isValidCity(String city);
}
