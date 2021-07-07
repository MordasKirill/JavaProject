package net.epam.study.service.validation;

public interface ValidationService {
    String emailErrorMsg(String email);
    String fullNameErrorMsg(String fullName);
    String phoneErrorMsg(String phone);
    String cityErrorMsg(String city);
    String priceErrorMsg(String price);
    String timeErrorMsg(String time);
    boolean isAdmin (String role);
    boolean isParamNotNull(String param);
}
