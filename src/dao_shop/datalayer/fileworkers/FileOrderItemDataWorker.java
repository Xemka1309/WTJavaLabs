package dao_shop.datalayer.fileworkers;

import dao_shop.beans.OrderItem;
import dao_shop.datalayer.OrderItemDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;

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
    public OrderItem[] getItems() throws DAOException {
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
                throw new DAOException("Can't get order items");
            }

        }
        nextFreeId++;
        return items;
    }

    @Override
    public OrderItem getItem(int id) throws DAOException {
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
            throw new DAOException("Can't get order item with id:"+id);
        }
        return result;
    }

    @Override
    public void addItem(OrderItem item) throws DAOException {
        File file = new File(dirpass + "/" + item.getId());
        FileWriter writer;
        StringBuilder buff = new StringBuilder();
        int symb;
        try {
            writer = new FileWriter(file);
            writer.write(item.Serialize());
            writer.close();
        } catch (IOException e) {
            throw new DAOException("Cant' add orderitem");
        }

    }

    @Override
    public void removeItem(int id) {
        File file = new File(dirpass + "/" + id);
        file.delete();

    }

    @Override
    public void modifyItem(int id, OrderItem newItem) throws DAOException {
        removeItem(id);
        newItem.setId(id);
        addItem(newItem);

    }
    @Override
    public int nextFreeId() {
        if (nextFreeId != -1)
            return nextFreeId++;
        else{
            try {
                getItems();
            } catch (DAOException e) {
                return 0;
            }
            return nextFreeId;
        }
    }

}
