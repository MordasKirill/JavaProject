package net.epam.study.bean;

import java.io.Serializable;
import java.util.Objects;

public class MenuItem implements Serializable {
    private String name;
    private String price;
    private String filingTime;
    private String category;


    public MenuItem() {
    }

    public MenuItem(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public MenuItem(String name, String price, String filingTime) {
        this.name = name;
        this.price = price;
        this.filingTime = filingTime;
    }

    public MenuItem(String name, String price, String filingTime, String category) {
        this.name = name;
        this.price = price;
        this.filingTime = filingTime;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFilingTime() {
        return filingTime;
    }

    public void setFilingTime(String filingTime) {
        this.filingTime = filingTime;
    }

    @Override
    public String toString() {
        return name + ", price: $" + price + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem dish = (MenuItem) o;
        return Objects.equals(getCategory(), dish.getCategory()) &&
                Objects.equals(getName(), dish.getName()) &&
                Objects.equals(getPrice(), dish.getPrice()) &&
                Objects.equals(getFilingTime(), dish.getFilingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getName(), getPrice(), getFilingTime());
    }
}