package net.epam.study.dao;

import net.epam.study.controller.commands.impl.AddToCart;
import net.epam.study.controller.commands.impl.GoToBasketPage;

public class RemoveOrderElement {
    public static void delete(String item){
        for (int i = 0; i< AddToCart.order.size(); i++){
            if (AddToCart.order.get(i).toString().equals(item)){
                AddToCart.order.remove(AddToCart.order.get(i));
                AddToCart.total.remove(AddToCart.total.get(i));
                break;
            }
            GoToBasketPage.getTotal();
        }
    }
}
