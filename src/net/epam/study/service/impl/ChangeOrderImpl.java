package net.epam.study.service.impl;

import net.epam.study.entity.MenuItem;
import net.epam.study.service.ChangeOrderService;

import java.util.ArrayList;
import java.util.List;

public class ChangeOrderImpl implements ChangeOrderService {
    public static List<MenuItem> order = new ArrayList<>();
    public static List<String> total = new ArrayList<>();
    public void deleteOrderItem(String item){
        for (int i = 0; i< order.size(); i++){
            if (order.get(i).toString().equals(item)){
                order.remove(order.get(i));
                total.remove(total.get(i));
                break;
            }
            getTotal();
        }
    }
    public void addToOrder(String name, String price, String time){
        if (name != null && price != null && time != null) {
            order.add(new MenuItem(name, price, time));
            total.add(price);
        }
        for (int i = 0; i<order.size(); i++) {
            System.out.println(order.get(i));
        }
    }
    public double getTotal(){
        double sum =0;
        for (int i=0; i<total.size(); i++){
            sum = sum + Double.parseDouble(total.get(i));
            sum = (double) Math.round(sum * 100) / 100;
        }
        return sum;
    }
    public StringBuilder getOrder(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i< order.size(); i++){
            stringBuilder.append(order.get(i).toString()).append(" ");
        }
        return stringBuilder;
    }
}
