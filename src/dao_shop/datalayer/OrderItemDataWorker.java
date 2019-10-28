package dao_shop.datalayer;

import dao_shop.beans.OrderItem;
import dao_shop.datalayer.exceptions.DAOException;

public interface OrderItemDataWorker {
    public OrderItem[] getItems() throws DAOException;
    public OrderItem getItem(int id) throws DAOException;
    public void addItem(OrderItem item) throws DAOException;
    public void removeItem(int id);
    public void modifyItem(int id, OrderItem newItem) throws DAOException;
}
