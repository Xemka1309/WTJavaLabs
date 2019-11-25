package dao_shop.beans;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import dao_shop.datalayer.myserialize.MySerializable;

import java.util.List;
public class ShoppingCart implements MySerializable {
    private List<OrderItem> orderItems;
    private int endPrice;
    private int id;
    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    public int getEndPrice() {
        return endPrice;
    }

    public List<OrderItem> getProducts() {
        return  orderItems;
    }

    public void setProducts(List<OrderItem> products) {
        this. orderItems = products;
    }
    public void addOrderItem(OrderItem item){
        if (orderItems == null)
            return;
        orderItems.add(item);
    }
    public void removeOrderItem(OrderItem item){
        if (orderItems == null)
            return;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
    @Override
    public String Serialize() {
        StringBuilder result = new StringBuilder();
        result.append("Cart\n");
        result.append("<");
        result.append(id);
        result.append(">");
        result.append("<");
        result.append(endPrice);
        result.append(">");
        return result.toString();
    }

    @Override
    //
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
            if (!buff.toString().equals("Cart"))
                throw new InvalidSerializationStringException("Type must be Product Cart");
            while ((ind< builder.length()) && (fieldInd-1 < 2)){
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
        try {
            endPrice = Integer.parseInt(fields[1]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for discount");
        }

    }
}
