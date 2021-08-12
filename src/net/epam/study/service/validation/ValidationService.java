package net.epam.study.service.validation;

import java.math.BigDecimal;

public interface ValidationService {
    String emailErrorMsg(String email);

    String fullNameErrorMsg(String fullName);

    String phoneErrorMsg(String phone);

    String cityErrorMsg(String city);

    String priceErrorMsg(String price);

    String priceErrorMsg(BigDecimal price);

    String timeErrorMsg(String time);

    boolean isAuthenticated(Boolean auth, String role);

    boolean isAdmin(String role);

    boolean isUser(String role);
}