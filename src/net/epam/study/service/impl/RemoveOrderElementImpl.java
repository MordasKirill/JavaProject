package net.epam.study.service.impl;

import net.epam.study.controller.command.impl.AddToCart;
import net.epam.study.controller.command.impl.GoToBasketPage;
import net.epam.study.service.RemoveOrderElementService;

public class RemoveOrderElementImpl implements RemoveOrderElementService {
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
