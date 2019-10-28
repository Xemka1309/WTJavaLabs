package dao_shop.datalayer;
import dao_shop.beans.Product;
import dao_shop.datalayer.exceptions.DAOException;

public interface ProductDataWorker {
    public Product[] getProducts() throws DAOException;
    public Product getProduct(int id) throws DAOException;
    public void addProduct(Product product) throws DAOException;
    public void removeProduct(int id);
    public void modifyProduct(int id, Product newProduct) throws DAOException;


}
