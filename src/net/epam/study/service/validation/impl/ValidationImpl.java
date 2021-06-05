package net.epam.study.service.validation.impl;

import net.epam.study.controller.command.Role;
import net.epam.study.service.validation.ValidationService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationImpl implements ValidationService {
    public static Pattern pattern;
    public static Matcher matcher;
    public static String userLocale;

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isValidFullName(String fullName){
        String ePattern = "^([А-ЯA-Z]|[А-ЯA-Z][\\x27а-яa-z]{1,}|[А-ЯA-Z][\\x27а-яa-z]{1,}-([А-ЯA-Z][\\x27а-яa-z]{1,}|(оглы)|(кызы)))\\040[А-ЯA-Z][\\x27а-яa-z]{1,}(\\040[А-ЯA-Z][\\x27а-яa-z]{1,})?$";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(fullName);
        return matcher.matches();
    }
    public boolean isValidPhoneNumber(String phone){
        String ePattern = "^\\+375(17|29|33|44)[0-9]{3}[0-9]{2}[0-9]{2}$";
        pattern = Pattern.compile(ePattern);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public boolean isValidCity(String city){
        //todo use pattern
        boolean result = false;
        if (city.equals("Minsk")
                ||city.equals("minsk")
                ||city.equals("минск")
                ||city.equals("Минск")){

            result = true;
        }
        return result;
    }
    public String emailErrorMsg(String email){
        if (!isValidEmailAddress(email)){
            return "local.error.orderErrorEmail";
        }
        return null;
    }
    public String fullNameErrorMsg(String fullName){
        if (!isValidFullName(fullName)){
            return "local.error.orderErrorFullName";
        }
        return null;
    }
    public String phoneErrorMsg(String phone){
        if (!isValidPhoneNumber(phone)){
            return "local.error.orderErrorPhone";
        }
        return null;
    }
    public String cityErrorMsg(String city){
        if (!isValidCity(city)){
            return "local.error.orderErrorCity";
        }
        return null;
    }
    public boolean isAdmin(String role){
        return role.equals(String.valueOf(Role.ADMIN)) || role.equals(String.valueOf(Role.OWNER));
    }
}
