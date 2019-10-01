package dao_shop;

public interface ProductDataWorker {
    public Product[] getProducts();
    public Product getProduct(int id);
    public void addProduct(Product product);
    public void removeProduct(int id);
    public void modifyProduct(int id, Product newProduct);

    public void decProductCount(Product product, int value);
    public void incProductCount(Product product, int value);
    public void setProductCount(Product product, int value);

}
