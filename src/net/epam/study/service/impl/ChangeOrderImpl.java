package net.epam.study.service.impl;

import net.epam.study.entity.MenuItem;
import net.epam.study.service.ChangeOrderService;

import java.util.ArrayList;
import java.util.List;

public class ChangeOrderImpl implements ChangeOrderService {
    public static final List<MenuItem> ORDER = new ArrayList<>();
    public static final List<String> TOTAL = new ArrayList<>();
    public void deleteOrderItem(String item){
        for (int i = 0; i< ORDER.size(); i++){
            if (ORDER.get(i).toString().equals(item)){
                ORDER.remove(ORDER.get(i));
                TOTAL.remove(TOTAL.get(i));
                break;
            }
            getTotal();
        }
    }
    public void addToOrder(String name, String price, String time){
        if (name != null && price != null && time != null) {
            ORDER.add(new MenuItem(name, price, time));
            TOTAL.add(price);
        }
    }
    public double getTotal(){
        double sum =0;
        for (int i = 0; i< TOTAL.size(); i++){
            sum = sum + Double.parseDouble(TOTAL.get(i));
            sum = (double) Math.round(sum * 100) / 100;
        }
        return sum;
    }
    public StringBuilder getOrder(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i< ORDER.size(); i++){
            stringBuilder.append(ORDER.get(i).toString()).append(" ");
        }
        return stringBuilder;
    }
}
