package dao_shop.datalayer.fileworkers;

import dao_shop.beans.Product;
import dao_shop.datalayer.ProductDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProductDataWorker implements ProductDataWorker {
    private int nextFreeId;
    private String dirpass;

    public FileProductDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }

    public Product[] getProducts() throws DAOException {
        nextFreeId = 0;
        File[] files = new File(dirpass).listFiles();
        Product[] products = new Product[files.length];
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
                products[i] = new Product();
                products[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());
                if (products[i].getId() > nextFreeId)
                    nextFreeId = products[i].getId();

            } catch (IOException | InvalidSerializationStringException e) {
                throw new DAOException("Can't get products");
            }

        }
        nextFreeId++;
        return products;

    }

    @Override
    public Product getProduct(int id) throws DAOException {
        Product result = new Product();
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
            throw new DAOException("Can't get product with id:"+id);
        }
        return result;
    }

    @Override
    public void addProduct(Product product) throws DAOException {
        File file = new File(dirpass + "/" + product.getId());
        FileWriter writer;
        StringBuilder buff = new StringBuilder();
        int symb;
        try {
            writer = new FileWriter(file);
            writer.write(product.Serialize());
            writer.close();
        } catch (IOException e) {
            throw new DAOException("Can't add product");
        }


    }

    @Override
    public void removeProduct(int id) {
        File file = new File(dirpass + "/" + id);
        file.delete();
    }

    @Override
    public void modifyProduct(int id, Product newProduct) throws DAOException {
        removeProduct(id);
        newProduct.setId(id);
        addProduct(newProduct);
    }
}
