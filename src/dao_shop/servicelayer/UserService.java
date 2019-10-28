package dao_shop.servicelayer;

import dao_shop.beans.Order;
import dao_shop.beans.OrderItem;
import dao_shop.beans.ShoppingCart;
import dao_shop.beans.User;
import dao_shop.servicelayer.exceptions.ServiceException;

public interface UserService extends ClientService {
    public void addOrderItem(OrderItem item);
    public void removeOrderItem(OrderItem item);
    public void addOrder(Order order);
    public ShoppingCart getCart(User user) throws ServiceException;
    public OrderItem[] getCartItems(ShoppingCart cart) throws ServiceException;
}
