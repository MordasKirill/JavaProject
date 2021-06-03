package net.epam.study.dao.impl;

import net.epam.study.dao.HashPasswordDAO;
import org.mindrot.jbcrypt.BCrypt;

public class HashPasswordImpl implements HashPasswordDAO {

    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

}
