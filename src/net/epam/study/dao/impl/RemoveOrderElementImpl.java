package net.epam.study.dao.impl;

import net.epam.study.controller.command.impl.AddToCart;
import net.epam.study.controller.command.impl.GoToBasketPage;
import net.epam.study.dao.RemoveOrderElementDAO;

public class RemoveOrderElementImpl implements RemoveOrderElementDAO {
    public void delete(String item){
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
