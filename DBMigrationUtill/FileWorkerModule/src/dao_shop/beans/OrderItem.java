package dao_shop.beans;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import dao_shop.datalayer.myserialize.MySerializable;

public class OrderItem implements MySerializable {
    private int id;
    private int cartId;
    private int productId;
    private int count;
    private int endPrice;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

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
    public String Serialize() {
        StringBuilder result = new StringBuilder();
        result.append("OrderItem\n");
        result.append("<");
        result.append(id);
        result.append(">");
        result.append("<");
        result.append(cartId);
        result.append(">");
        result.append("<");
        result.append(productId);
        result.append(">");
        result.append("<");
        result.append(count);
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
        StringBuilder builder = new StringBuilder(str);
        if (builder.length() > 0){
            StringBuilder buff = new StringBuilder();
            int ind = 0;
            while (builder.charAt(ind) != '\n'){
                buff.append(builder.charAt(ind));
                ind++;
            }
            ind++;
            if (!buff.toString().equals("OrderItem"))
                throw new InvalidSerializationStringException("Type must be OrderItem");
            while ((ind< builder.length()) && (fieldInd-1 < 5)){
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
            cartId = Integer.parseInt(fields[1]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for id");
        }
        try {
            productId = Integer.parseInt(fields[2]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for id");
        }
        try {
            count= Integer.parseInt(fields[3]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for id");
        }
        try {
            endPrice = Integer.parseInt(fields[4]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for id");
        }

    }
}
