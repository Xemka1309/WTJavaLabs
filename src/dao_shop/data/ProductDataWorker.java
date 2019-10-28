package dao_shop.data;
import dao_shop.beans.Product;
public interface ProductDataWorker {
    public Product[] getProducts();
    public Product getProduct(int id);
    public void addProduct(Product product);
    public void removeProduct(int id);
    public void modifyProduct(int id, Product newProduct);


}
