package net.epam.study.service.validation;

import java.math.BigDecimal;

public interface ValidationService {
    String getErrorMsg(String param, String pattern, String errorMsg);

    String cityErrorMsg(String city);

    String priceErrorMsg(BigDecimal price);

    boolean isAdmin(String role);

    boolean isUser(String role);
}