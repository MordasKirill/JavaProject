package net.epam.study.service.validation.impl;

import net.epam.study.controller.command.Role;
import net.epam.study.service.validation.ValidationService;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationImpl implements ValidationService {
    public static Pattern pattern;
    public static Matcher matcher;
    public static String userLocale;

    public boolean isParamValid(String param, String ePattern){
        if (param == null) {
            return false;
        }
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(param);
        return matcher.matches();
    }

    public String getErrorMsg(String param, String pattern, String errorMsg){
        if (!isParamValid(param, pattern)){
            return errorMsg;
        }
        return null;
    }

    public boolean isValidCity(String city) {
        if (city == null) {
            return false;
        }
        boolean result = false;
        if (city.equalsIgnoreCase("Minsk") ||
                city.equalsIgnoreCase("Минск")) {
            result = true;
        }
        return result;
    }

    public String priceErrorMsg(BigDecimal price) {
        if (!isPriceZero(price)) {
            return "local.error.errMsgNullPrice";
        }
        return null;
    }

    public String cityErrorMsg(String city) {
        if (!isValidCity(city)) {
            return "local.error.orderErrorCity";
        }
        return null;
    }

    public boolean isAdmin(String role) {
        return !Role.ADMIN.toString().equalsIgnoreCase(role) && !Role.OWNER.toString().equalsIgnoreCase(role);
    }

    public boolean isUser(String role) {
        return !Role.USER.toString().equalsIgnoreCase(role);
    }

    public boolean isPriceZero(BigDecimal param) {
        return !(param.compareTo(BigDecimal.ZERO) == 0);
    }
}
