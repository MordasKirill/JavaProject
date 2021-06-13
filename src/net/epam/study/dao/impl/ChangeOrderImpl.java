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

    public double getTotal(String login) throws DAOException, ConnectionPoolException{
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TablesListService tablesListService = serviceProvider.getTablesListService();

        double sum = 0;
        double result = 0;

        for (int i = 0; i< net.epam.study.service.impl.ChangeOrderImpl.TOTAL.size(); i++){

            sum = sum + Double.parseDouble(net.epam.study.service.impl.ChangeOrderImpl.TOTAL.get(i));
            sum = (double) Math.round(sum * 100) / 100;

            try {
                if (tablesListService.getDonePayments(login) >= 3){
                    result = (sum * 3) / 100;
                    result = sum - result;
                    result = (double) Math.round(result * 100) / 100;
                }

                if (tablesListService.getDonePayments(login) >= 10){
                    result = (sum * 10) / 100;
                    result = sum - result;
                    result = (double) Math.round(result * 100) / 100;
                }
                if (tablesListService.getDonePayments(login) < 3){
                    result = (double) Math.round(sum * 100) / 100;
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
