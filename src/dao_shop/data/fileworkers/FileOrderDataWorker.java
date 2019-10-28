package dao_shop.data.fileworkers;

import dao_shop.beans.Order;
import dao_shop.data.OrderDataWorker;
import dao_shop.data.myserialize.InvalidSerializationStringException;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class FileOrderDataWorker implements OrderDataWorker {
    private String dirpass;
    private int nextFreeId;

    public FileOrderDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }

    @Override
    public Order[] getOrders() {
        try {
            return loadOrders();
        } catch (IOException | InvalidSerializationStringException e) {
            e.printStackTrace();
        }
        return null;

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
    public Order getOrder(int id) {
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
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addOrder(@NotNull Order order) {
        FileWriter writer;
        try {
            writer = new FileWriter(dirpass + "/" + order.getId());
            writer.write(order.Serialize());
            writer.close();

        } catch (IOException e) {
        }
    }

    @Override
    public void removeOrder(int id) {
        File file = new File(dirpass + "/" + id);
    }

    @Override
    public void modifyOrder(int id, Order newOrder) {
        removeOrder(id);
        newOrder.setId(id);
        addOrder(newOrder);
    }
}
