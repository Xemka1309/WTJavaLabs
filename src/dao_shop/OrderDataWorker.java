package dao_shop;

public interface OrderDataWorker {
    public Order[] getOrders();
    public Order getOrder(int id);
    public void addOrder(Order order);
    public void removeOrder(int id);
    public void modifyOrder(int id, Order newOrder);
}
