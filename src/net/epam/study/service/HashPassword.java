package net.epam.study.service;
import net.epam.study.BCrypt.BCrypt;
public class HashPassword {

    public static String hashPassword(String password){
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }
    public static boolean checkPassword(String password, String hash){
        boolean password_verified = false;
        if(null == hash || !hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        password_verified = BCrypt.checkpw(password, hash);
        return password_verified;
    }

}
