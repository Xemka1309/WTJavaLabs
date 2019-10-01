package dao_shop;

public class FileOrderDataWorker implements  OrderDataWorker {
    @Override
    public Order[] getOrders() {
        return new Order[0];
    }

    @Override
    public Order getOrder(int id) {
        return null;
    }

    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void removeOrder(int id) {

    }

    @Override
    public void modifyOrder(int id, Order newOrder) {

    }
}
