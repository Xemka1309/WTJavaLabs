package dao_shop.datalayer;
import dao_shop.beans.Order;
import dao_shop.datalayer.exceptions.DAOException;

public interface OrderDataWorker {
    public Order[] getOrders() throws DAOException;
    public Order getOrder(int id) throws DAOException;
    public void addOrder(Order order) throws DAOException;
    public void removeOrder(int id);
    public void modifyOrder(int id, Order newOrder) throws DAOException;
}
