package net.epam.study.dao.impl;

import net.epam.study.dao.ChangeOrderDAO;
import net.epam.study.dao.DAOException;
import net.epam.study.dao.connection.ConnectionPoolException;
import net.epam.study.bean.MenuItem;
import net.epam.study.service.ServiceException;
import net.epam.study.service.ServiceProvider;
import net.epam.study.service.TablesListService;
import net.epam.study.service.impl.ManageOrderImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * implementation of ChangeOrderDAO
 *
 */
public class ChangeOrderImpl implements ChangeOrderDAO {


    private static final Logger LOG = Logger.getLogger(ChangeOrderImpl.class);

    /**
     * Method to delete an item from order
     * @param item name of item to delete
     * @param login user login to get total
     * throws in case something goes wrong
     */
    public void deleteOrderItem(String item, String login){
        for (int i = 0; i< ManageOrderImpl.ORDER.size(); i++){
            if (ManageOrderImpl.ORDER.get(i).toString().equals(item)){
                ManageOrderImpl.ORDER.remove(ManageOrderImpl.ORDER.get(i));
                ManageOrderImpl.TOTAL.remove(ManageOrderImpl.TOTAL.get(i));
                break;
            }
        }
    }

    /**
     * Add to cart method
     * @param menuItem name of item to be added to cart
     */
    public void addToOrder(MenuItem menuItem){
        if (menuItem.getName() != null && menuItem.getPrice() != null && menuItem.getFilingTime() != null) {
            ManageOrderImpl.ORDER.add(menuItem);
            ManageOrderImpl.TOTAL.add(menuItem.getPrice());
        }
    }

    /**
     * Method to get order total
     * @param login user login
     * @return returns BigDecimal total of order
     * @throws DAOException exception in dao
     * throws in case something goes wrong
     * @throws ConnectionPoolException in case problems
     * with open/retrieve connection
     */
    public BigDecimal getTotal(String login) throws DAOException, ConnectionPoolException{
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TablesListService tablesListService = serviceProvider.getTablesListService();


        BigDecimal result = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);

        for (int i = 0; i< ManageOrderImpl.TOTAL.size(); i++){
            BigDecimal total = new BigDecimal(ManageOrderImpl.TOTAL.get(i));
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
                LOG.log(Level.ERROR,"Get total fail", e);
                throw new DAOException("Get total fail", e);
            }
        }
        return result;
    }

    /**
     * Get order method
     * @return returns a StringBuilder value
     * of all products in a cart
     */
    public StringBuilder getOrder(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i< ManageOrderImpl.ORDER.size(); i++){
            stringBuilder.append(ManageOrderImpl.ORDER.get(i).toString()).append(" ");
        }
        return stringBuilder;
    }
}
