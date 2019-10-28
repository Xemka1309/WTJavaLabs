package dao_shop.servicelayer;

import dao_shop.beans.Product;
import dao_shop.servicelayer.exceptions.ServiceException;

public interface AdminService extends ClientService {
    public Product getProduct(int id) throws ServiceException;
    public Product[] getProducts() throws ServiceException;
    public void addProduct(Product product) throws ServiceException;
    public void removeProduct(Product product);
    public void modifyProduct(Product oldProduct, Product newProduct) throws ServiceException;
}
