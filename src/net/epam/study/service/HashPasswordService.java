package net.epam.study.service;

public interface HashPasswordService {
    String hashPassword(String password);
    boolean checkHashPassword(String password, String hashPassword);
}
