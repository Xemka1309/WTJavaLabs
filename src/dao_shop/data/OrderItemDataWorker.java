package dao_shop.data;

import dao_shop.beans.OrderItem;

public interface OrderItemDataWorker {
    public OrderItem[] getItems();
    public OrderItem getItem(int id);
    public void addItem(OrderItem item);
    public void removeItem(int id);
    public void modifyItem(int id, OrderItem newItem);
}
