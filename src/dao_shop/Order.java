package dao_shop;
// TODO:implement equals,hashcode,tostring, getters, setters
public class Order {
    private User user;
    private DeliveryInfo deliveryInfo;
    private ShoppingCart shoppingCart;
    private  int endPrice;

    public int getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }


    public User getUser() {
        return user;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
