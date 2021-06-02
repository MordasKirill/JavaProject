package net.epam.study.service.impl;

import net.epam.study.service.HashPasswordService;
import org.mindrot.jbcrypt.BCrypt;

public class HashPasswordImpl implements HashPasswordService {

    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    public boolean checkHashPassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }

}
