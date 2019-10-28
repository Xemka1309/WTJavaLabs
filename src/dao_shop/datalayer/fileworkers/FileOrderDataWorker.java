package dao_shop.datalayer.fileworkers;

import dao_shop.beans.Order;
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

    private Order[] loadOrders() throws IOException, InvalidSerializationStringException, DAOException {

        File[] files = new File(dirpass).listFiles();
        Order[] items = new Order[files.length];
        FileReader reader;
        StringBuilder builder = new StringBuilder();
        int symb;
        for (int i = 0; i < files.length; i++) {
            symb = -1;
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
                if (items[i].getId() > nextFreeId )
                    nextFreeId = items[i].getId() + 1;

            } catch (IOException | InvalidSerializationStringException e) {
                throw new DAOException("Can't get order items");
            }

        }
        if (nextFreeId == -1){
            nextFreeId++;
            return null;
        }
        return items;
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
