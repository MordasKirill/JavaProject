package net.epam.study.entity;

import java.util.Objects;

public class MenuItem {
    private String name;
    private String price;
    private String filingTime;
    private String category;


    public MenuItem(){
        super();
    }
    public MenuItem(String name, String price, String filingTime){
        this.name = name;
        this.price = price;
        this.filingTime = filingTime;
    }
    public MenuItem(String name, String price, String filingTime, String category){
        this.name = name;
        this.price = price;
        this.filingTime = filingTime;
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getFilingTime() {
        return filingTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setFilingTime(String filingTime) {
        this.filingTime = filingTime;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", price: " + price +
                ", filing time: " + filingTime +".";
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