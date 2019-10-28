package dao_shop.servicelayer;

import dao_shop.beans.Order;
import dao_shop.beans.OrderItem;

public interface UserService extends ClientService {
    public void addOrderItem(OrderItem item);
    public void removeOrderItem(OrderItem item);
    public void addOrder(Order order);
}
