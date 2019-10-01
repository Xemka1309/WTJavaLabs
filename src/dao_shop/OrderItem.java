package dao_shop;
import java.io.Serializable;

// TODO:implement equals,hashcode,tostring, getters, setters
public class OrderItem implements Serializable {
    private int productId;
    private int count;
    private int endPrice;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getEndPrice() {
        return endPrice;
    }

    public int getProductId() {
        return productId;
    }


    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
