package dao_shop;
// TODO:other methods
public interface DataWorker {
    public Product[] getProducts();
    public void addProduct(Product product);
    public void removeProduct(Product product);

    public void getUsers();
    public void addUser(User user);
    public void removeUser(User user);
}
