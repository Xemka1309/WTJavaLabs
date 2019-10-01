package dao_shop;

public class FileProductDataWorker implements ProductDataWorker {
    @Override
    public Product[] getProducts() {
        return new Product[0];
    }

    @Override
    public Product getProduct(int id) {
        return null;
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void removeProduct(int id) {

    }

    @Override
    public void modifyProduct(int id, Product newProduct) {

    }

    @Override
    public void decProductCount(Product product, int value) {

    }

    @Override
    public void incProductCount(Product product, int value) {

    }

    @Override
    public void setProductCount(Product product, int value) {

    }
}
