package net.epam.study.service.validation;

public interface ValidationService {
    String emailErrorMsg(String email);

    String fullNameErrorMsg(String fullName);

    String phoneErrorMsg(String phone);

    String cityErrorMsg(String city);

    String priceErrorMsg(String price);

    String timeErrorMsg(String time);

    boolean isParamNotNull(String param);

    boolean isParamNotNull(int param);

    boolean isAuthenticated(Boolean auth, String role);

    boolean isAdmin(String role);

    boolean isUser(String role);
}