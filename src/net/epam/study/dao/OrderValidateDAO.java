package net.epam.study.dao;

public interface OrderValidateDAO {
    void validate(String fullName, String address, String email, String phone, StringBuilder stringBuilder);
}
