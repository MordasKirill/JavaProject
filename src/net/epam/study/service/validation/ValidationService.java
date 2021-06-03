package net.epam.study.service.validation;

public interface ValidationService {
    String emailErrorMsg(String email);
    String fullNameErrorMsg(String fullName);
    String phoneErrorMsg(String phone);
    String cityErrorMsg(String city);
    boolean isAdmin (String role);
}
