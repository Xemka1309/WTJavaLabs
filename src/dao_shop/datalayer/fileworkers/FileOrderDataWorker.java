package dao_shop.datalayer.fileworkers;

import dao_shop.beans.DeliveryInfo;
import dao_shop.beans.Order;
import dao_shop.beans.ShoppingCart;
import dao_shop.beans.User;
import dao_shop.datalayer.OrderDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class FileOrderDataWorker implements OrderDataWorker {
    private String dirpass;
    private int nextFreeId = -1;

    public FileOrderDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }

    @Override
    public Order[] getOrders() throws DAOException {
        try {
            return loadOrders();
        } catch (IOException | InvalidSerializationStringException e) {
            throw new DAOException("Can't get all orders");
        }
    }

    private void LoadCarts(Order[] orders) throws DAOException {
        ShoppingCart[] carts = FileDataWorkerFactory.getInstance().getShoppingCartDataWorker().getCarts();
        for (int i = 0; i < orders.length; i++){
            for (int j = 0; j < carts.length; j++){
                if (carts[j].getId() == orders[i].getShoppingCart().getId()){
                    orders[i].setShoppingCart(carts[j]);
                }
            }
        }
    }
    private void LoadDelivery(Order[] orders) throws DAOException {
        DeliveryInfo[] deliveryInfos = FileDataWorkerFactory.getInstance().getDeliveryInfoDataWorker().getAllInfo();
        for (int i = 0; i < orders.length; i++){
            for (int j = 0; j < deliveryInfos.length; j++){
                if (deliveryInfos[j].getId() == orders[i].getDeliveryInfo().getId()){
                    orders[i].setDeliveryInfo(deliveryInfos[j]);
                }
            }
        }
    }
    private void LoadUsers(Order[] orders) throws DAOException {
        User[] users = FileDataWorkerFactory.getInstance().getUserDataWorker().getUsers();
        for (int i = 0; i < orders.length; i++){
            for (int j = 0; j < users.length; j++){
                if (users[j].getId() == orders[i].getUser().getId()){
                    orders[i].setUser(users[j]);
                }
            }
        }

    }
    private Order[] loadOrders() throws IOException, InvalidSerializationStringException, DAOException {

        boolean notnull = false;
        File[] files = new File(dirpass).listFiles();
        Order[] items = new Order[files.length];
        FileReader reader;
        StringBuilder builder = new StringBuilder();
        int symb;
        if (files.length < 1){
            nextFreeId ++;
            return null;
        }

        for (int i = 0; i < files.length; i++) {
            symb = -1;
            notnull = true;
            try {
                reader = new FileReader(files[i]);
                symb = reader.read();
                while (symb != -1) {
                    builder.append((char) symb);
                    symb = reader.read();
                }
                items[i] = new Order();
                items[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());
                if (items[i].getId() >= nextFreeId )
                    nextFreeId = items[i].getId() + 1;

            } catch (IOException | InvalidSerializationStringException e) {
                throw new DAOException("Can't get order items");
            }

        }
        if (nextFreeId == -1){
            nextFreeId++;
            return null;
        }
        if (notnull){
            LoadUsers(items);
            LoadDelivery(items);
            LoadCarts(items);
            return items;
        }
        else
            return null;

    }

    @Override
    public Order getOrder(int id) throws DAOException {
        Order result = new Order();
        File file = new File(dirpass + "/" + id);
        int symb;
        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            symb = reader.read();
            while (symb != -1) {
                builder.append((char) symb);
                symb = reader.read();
            }
            result.DeSerialize(builder.toString());
            reader.close();
        } catch (IOException | InvalidSerializationStringException e) {
            throw new DAOException("Cant's get order with id:"+id);
        }
        return result;
    }

    @Override
    public void addOrder(@NotNull Order order) throws DAOException {
        FileWriter writer;
        try {
            writer = new FileWriter(dirpass + "/" + order.getId());
            writer.write(order.Serialize());
            writer.close();

        } catch (IOException e) {
            throw new DAOException("Can't add order");
        }
    }

    @Override
    public void removeOrder(int id) {
        File file = new File(dirpass + "/" + id);
        file.delete();
    }

    @Override
    public void modifyOrder(int id, Order newOrder) throws DAOException {
        removeOrder(id);
        newOrder.setId(id);
        addOrder(newOrder);
    }
    @Override
    public int nextFreeId() {
        if (nextFreeId != -1)
            return nextFreeId++;
        else{
            try {
                getOrders();
            } catch (DAOException e) {
                return 0;
            }
            return nextFreeId;
        }
    }
}
