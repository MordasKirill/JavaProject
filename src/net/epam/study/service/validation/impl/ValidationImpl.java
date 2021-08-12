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

    public String emailErrorMsg(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        if (!isParamValid(email, ePattern)) {
            return "local.error.orderErrorEmail";
        }
        return null;
    }

    public String fullNameErrorMsg(String fullName) {
        String ePattern = "^([А-ЯA-Z]|[А-ЯA-Z][\\x27а-яa-z]{1,}|[А-ЯA-Z][\\x27а-яa-z]{1,}-([А-ЯA-Z][\\x27а-яa-z]{1,}|(оглы)|(кызы)))\\040[А-ЯA-Z][\\x27а-яa-z]{1,}(\\040[А-ЯA-Z][\\x27а-яa-z]{1,})?$";
        if (!isParamValid(fullName, ePattern)) {
            return "local.error.orderErrorFullName";
        }
        return null;
    }

    public String priceErrorMsg(BigDecimal price) {
        if (!isParamNotNull(price)) {
            return "local.error.errMsgNullPrice";
        }
        return null;
    }

    public String phoneErrorMsg(String phone) {
        String ePattern = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";
        if (!isParamValid(phone, ePattern)) {
            return "local.error.orderErrorPhone";
        }
        return null;
    }

    public String cityErrorMsg(String city) {
        if (!isValidCity(city)) {
            return "local.error.orderErrorCity";
        }
        return null;
    }

    public String priceErrorMsg(String price) {
        String ePattern = "\\d{1,4}\\.\\d{1,3}";
        if (!isParamValid(price, ePattern)) {
            return "local.error.adminErrorPrice";
        }
        return null;
    }

    public String timeErrorMsg(String time) {
        String ePattern = "\\d{1,4}";
        if (!isParamValid(time, ePattern)) {
            return "local.error.adminErrorTime";
        }
        return null;
    }

    public boolean isAuthenticated(Boolean auth, String role) {
        return auth != null && auth && role != null;
    }

    public boolean isAdmin(String role) {
        return !Role.ADMIN.toString().equalsIgnoreCase(role) && !Role.OWNER.toString().equalsIgnoreCase(role);
    }

    public boolean isUser(String role) {
        return !Role.USER.toString().equalsIgnoreCase(role);
    }

    public boolean isParamNotNull(BigDecimal param) {
        return !(param.compareTo(BigDecimal.ZERO) == 0);
    }
}
