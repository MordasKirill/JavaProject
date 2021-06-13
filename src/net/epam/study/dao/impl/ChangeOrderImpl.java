package net.epam.study.dao.impl;

import net.epam.study.dao.ChangeOrderDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.bean.MenuItem;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class ChangeOrderImpl implements ChangeOrderDAO {

    private static final Logger log = Logger.getLogger(ChangeOrderImpl.class);


    public void deleteOrderItem(String item, String login) throws DAOException{
        for (int i = 0; i< net.epam.study.service.impl.ChangeOrderImpl.ORDER.size(); i++){
            if (net.epam.study.service.impl.ChangeOrderImpl.ORDER.get(i).toString().equals(item)){
                net.epam.study.service.impl.ChangeOrderImpl.ORDER.remove(net.epam.study.service.impl.ChangeOrderImpl.ORDER.get(i));
                net.epam.study.service.impl.ChangeOrderImpl.TOTAL.remove(net.epam.study.service.impl.ChangeOrderImpl.TOTAL.get(i));
                break;
            }
            try {
                getTotal(login);
            } catch (DAOException | ConnectionPoolException e) {
                log.log(Level.ERROR,"Get orders fail", e);
                throw new DAOException("Get orders fail", e);
            }
        }
    }

    public void addToOrder(String name, String price, String time){
        if (name != null && price != null && time != null) {
            net.epam.study.service.impl.ChangeOrderImpl.ORDER.add(new MenuItem(name, price, time));
            net.epam.study.service.impl.ChangeOrderImpl.TOTAL.add(price);
        }
    }

    public BigDecimal getTotal(String login) throws DAOException, ConnectionPoolException{
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TablesListService tablesListService = serviceProvider.getTablesListService();


        BigDecimal result = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);

        for (int i = 0; i< net.epam.study.service.impl.ChangeOrderImpl.TOTAL.size(); i++){
            BigDecimal total = new BigDecimal(net.epam.study.service.impl.ChangeOrderImpl.TOTAL.get(i));
            sum = sum.add(total);

            try {
                if (tablesListService.getDonePayments(login) >= 3){
                    BigDecimal amount = new BigDecimal(String.valueOf(sum.multiply(BigDecimal.valueOf(3))));
                    amount = amount.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN);
                    result = sum.subtract(amount);
                }

                if (tablesListService.getDonePayments(login) >= 10){
                    BigDecimal amount =  new BigDecimal(String.valueOf(sum.multiply(BigDecimal.valueOf(10))));
                    amount = amount.divide(BigDecimal.valueOf(100), BigDecimal.ROUND_DOWN);
                    result = sum.subtract(amount);
                }

                if (tablesListService.getDonePayments(login) < 3){
                    result = sum;
                }

            } catch (ServiceException e){
                log.log(Level.ERROR,"Get total fail", e);
                throw new DAOException("Get total fail", e);
            }
        }
        return result;
    }

    public StringBuilder getOrder(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i< net.epam.study.service.impl.ChangeOrderImpl.ORDER.size(); i++){
            stringBuilder.append(net.epam.study.service.impl.ChangeOrderImpl.ORDER.get(i).toString()).append(" ");
        }
        return stringBuilder;
    }
}
