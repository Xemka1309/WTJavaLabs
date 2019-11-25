package dao_shop.servicelayer;

import dao_shop.beans.*;
import dao_shop.servicelayer.exceptions.ServiceException;

public interface UserService extends ClientService {
    public void addOrderItem(OrderItem item) throws ServiceException;
    public void removeOrderItem(OrderItem item);
    public void addOrder(Order order) throws ServiceException;
    public ShoppingCart getCart(User user) throws ServiceException;
    public OrderItem[] getCartItems(ShoppingCart cart) throws ServiceException;
    public Order[] getOrders(User user) throws ServiceException;
    public void addDelivery(DeliveryInfo info) throws ServiceException;
}
