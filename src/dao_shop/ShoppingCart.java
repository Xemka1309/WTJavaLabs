package dao_shop;
import java.util.List;
// TODO:implement equals,hashcode,tostring, getters, setters
public class ShoppingCart {
    private List<OrderItem> products;
    private int endPrice;

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
