package dao_shop.data.fileworkers;

import dao_shop.beans.OrderItem;
import dao_shop.data.OrderItemDataWorker;
import dao_shop.data.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOrderItemDataWorker implements OrderItemDataWorker {
    private String dirpass;
    private int nextFreeId;

    public FileOrderItemDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }
    @Override
    public OrderItem[] getItems() {
        nextFreeId = 0;
        File[] files = new File(dirpass).listFiles();
        OrderItem[] items = new OrderItem[files.length];
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
                items[i] = new OrderItem();
                items[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());
                if (items[i].getId() > nextFreeId)
                    nextFreeId = items[i].getId();

            } catch (IOException | InvalidSerializationStringException e) {
                e.printStackTrace();
            }

        }
        nextFreeId++;
        return items;
    }

    @Override
    public OrderItem getItem(int id) {
        OrderItem result = new OrderItem();
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
    public void addItem(OrderItem item) {

    }

    @Override
    public void removeItem(int id) {

    }

    @Override
    public void modifyItem(int id, OrderItem newItem) {

    }
}
