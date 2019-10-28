package dao_shop.datalayer.fileworkers;

import dao_shop.beans.Order;
import dao_shop.datalayer.OrderDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class FileOrderDataWorker implements OrderDataWorker {
    private String dirpass;
    private int nextFreeId;

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

    private Order[] loadOrders() throws IOException, InvalidSerializationStringException {
        File file = new File(dirpass);
        File[] files = file.listFiles();
        Order[] orders = new Order[files.length];
        FileReader fileReader;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < files.length; i++) {
            fileReader = new FileReader(files[i]);
            orders[i] = new Order();
            int symb = fileReader.read();
            while (symb != -1) {
                str.append((char) symb);
                symb = fileReader.read();
            }
            orders[i].DeSerialize(str.toString());
            str.delete(0, str.length());
            fileReader.close();
        }
        int maxid = orders[0].getId();
        for (int i = 1; i < orders.length; i++) {
            if (orders[i].getId() > maxid)
                maxid = orders[i].getId();
        }
        nextFreeId = maxid + 1;
        return orders;
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
}
