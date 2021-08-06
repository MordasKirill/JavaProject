package net.epam.study.bean;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {
    private String id;
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private String details;
    private String status;
    private String paymentStatus;

    public Order() {
    }

    public Order(String fullName, String address, String email, String phone, String details) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                ", fullName: " + fullName +
                ", address: " + address +
                ", email: " + email +
                ", phone: " + phone +
                ", email: " + details +
                ", status: " + status + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId().equals(order.getId()) &&
                getFullName().equals(order.getFullName()) &&
                getAddress().equals(order.getAddress()) &&
                getEmail().equals(order.getEmail()) &&
                getPhone().equals(order.getPhone()) &&
                getDetails().equals(order.getDetails()) &&
                getStatus().equals(order.getStatus()) &&
                getPaymentStatus().equals(order.getPaymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getAddress(), getEmail(), getPhone(), getDetails(), getStatus(), getPaymentStatus());
    }
}
