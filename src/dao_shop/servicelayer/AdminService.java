package dao_shop.servicelayer;

import dao_shop.beans.Product;

public interface AdminService extends ClientService {
    public Product getProduct(int id);
    public void addProduct(Product product);
    public void removeProduct(Product product);
    public void modifyProduct(Product oldProduct, Product newProduct);
}
