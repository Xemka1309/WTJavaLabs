package dao_shop.beans;

import dao_shop.data.myserialize.InvalidSerializationStringException;
import dao_shop.data.myserialize.MySerializable;

// TODO:implement equals,hashcode,tostring, getters, setters
public class Order implements MySerializable {
    private int id;
    private User user;
    private DeliveryInfo deliveryInfo;
    private ShoppingCart shoppingCart;
    private  int endPrice;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
    @Override
    public String Serialize() {
        StringBuilder result = new StringBuilder();
        result.append("Order\n");
        result.append("<");
        result.append(id);
        result.append(">");
        result.append("<");
        result.append(user.getId());
        result.append(">");
        result.append("<");
        result.append(deliveryInfo.getId());
        result.append(">");
        result.append("<");
        result.append(shoppingCart.getId());
        result.append(">");
        return result.toString();
    }

    @Override
    // return shopping cart with setted id only
    public void DeSerialize(String str) throws InvalidSerializationStringException {
        String[] fields = new String[6];
        int fieldInd = 0;
        Object result = new User();
        StringBuilder builder = new StringBuilder(str);
        if (builder.length() > 0){
            StringBuilder buff = new StringBuilder();
            int ind = 0;
            while (builder.charAt(ind) != '\n'){
                buff.append(builder.charAt(ind));
                ind++;
            }
            ind++;
            if (!buff.toString().equals("Order"))
                throw new InvalidSerializationStringException("Type must be Order");
            while ((ind< builder.length()) && (fieldInd-1 < 4)){
                ind++;
                buff.delete(0,buff.length());
                while (builder.charAt(ind) != '>'){
                    buff.append(builder.charAt(ind));
                    ind++;
                }
                fields[fieldInd] = buff.toString();
                fieldInd++;
                ind++;
            }

        }
        try {
            id = Integer.parseInt(fields[0]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for id");
        }

        user = new User();
        try {
            user.setId(Integer.parseInt(fields[1]));
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for user id");
        }
        deliveryInfo = new DeliveryInfo();
        try {
            deliveryInfo.setId(Integer.parseInt(fields[2]));
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for deliveryinfo  id");
        }
        shoppingCart = new ShoppingCart();
        try {
            shoppingCart.setId(Integer.parseInt(fields[3]));
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for shopping cart id");
        }

    }
}
