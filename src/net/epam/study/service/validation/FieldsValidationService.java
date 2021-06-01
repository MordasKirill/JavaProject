package net.epam.study.service.validation;

public interface FieldsValidationService {
    String emailErrorMsg(String email);
    String fullNameErrorMsg(String fullName);
    String phoneErrorMsg(String phone);
    String cityErrorMsg(String city);
}
