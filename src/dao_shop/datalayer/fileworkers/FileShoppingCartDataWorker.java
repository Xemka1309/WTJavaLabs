package dao_shop.datalayer.fileworkers;

import dao_shop.beans.ShoppingCart;
import dao_shop.datalayer.ShoppingCartDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileShoppingCartDataWorker implements ShoppingCartDataWorker {
    private String dirpass;
    private int nextFreeId;

    public FileShoppingCartDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }

    @Override
    public ShoppingCart[] getCarts() throws DAOException {
        nextFreeId = 0;
        File[] files = new File(dirpass).listFiles();
        ShoppingCart[] carts = new ShoppingCart[files.length];
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
                carts[i] = new ShoppingCart();
                carts[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());
                if (carts[i].getId() > nextFreeId)
                    nextFreeId = carts[i].getId();

            } catch (IOException | InvalidSerializationStringException e) {
               throw new DAOException("Can't get carts");
            }

        }
        nextFreeId++;
        return carts;
    }

    @Override
    public ShoppingCart getCart(int id) throws DAOException {
        ShoppingCart result = new ShoppingCart();
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
           throw new DAOException("Can't get cart with id:"+id);
        }
        return result;
    }

    @Override
    public void addCart(ShoppingCart cart) throws DAOException {
        File file = new File(dirpass + "/" + cart.getId());
        FileWriter writer;
        StringBuilder buff = new StringBuilder();
        int symb;
        try {
            writer = new FileWriter(file);
            writer.write(cart.Serialize());
            writer.close();
        } catch (IOException e) {
            throw new DAOException("Can't add cart");
        }

    }

    @Override
    public void removeCart(int id) {
        File file = new File(dirpass + "/" + id);
        file.delete();

    }

    @Override
    public void modifyCart(int id, ShoppingCart newCart) throws DAOException {
        removeCart(id);
        newCart.setId(id);
        addCart(newCart);

    }
}
