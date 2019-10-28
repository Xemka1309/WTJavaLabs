package dao_shop.beans;

import dao_shop.data.myserialize.InvalidSerializationStringException;
import dao_shop.data.myserialize.MySerializable;

public class Product implements MySerializable {
    private int id;
    private  String name;
    private int price;
    private  String description;

    public int getId(){
        return  id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return  price;
    }
    public void setPrice(int price){
        this.price = price;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Product(){};
    @Override
    public int hashCode(){
        if (name == null || price == 0)
            return -1;
        return name.hashCode() ^ price ^ id;
    }
    @Override
    public String toString(){
        return  name.toString() + description.toString() + price;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product product = (Product) obj;
        if (product.id != id)
            return false;
        if (!product.name.equals(name))
            return false;
        if (product.price != price)
            return false;
        if (!product.name.equals(name))
            return false;
        return true;

    }
    @Override
    public String Serialize() {
        StringBuilder result = new StringBuilder();
        result.append("Product\n");
        result.append("<");
        result.append(id);
        result.append(">");
        result.append("<");
        result.append(name);
        result.append(">");
        result.append("<");
        result.append(description);
        result.append(">");
        result.append("<");
        result.append(price);
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
            if (!buff.toString().equals("Product"))
                throw new InvalidSerializationStringException("Type must be Product");
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
        name = fields[1];
        description = fields[2];
        try {
            price = Integer.parseInt(fields[3]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for discount");
        }
    }
}
