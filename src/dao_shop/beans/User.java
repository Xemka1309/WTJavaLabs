package dao_shop.beans;

import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import dao_shop.datalayer.myserialize.MySerializable;

// TODO:implement equals,hashcode,tostring, getters, setters
public class User implements MySerializable {
    private int id;
    private String login;
    private String password;
    private String email;
    private int userDiscount;
    // maybe delete
    private ShoppingCart shoppingCart;


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(int userDiscount) {
        this.userDiscount = userDiscount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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
        result.append("User\n");
        result.append("<");
        result.append(id);
        result.append(">");
        result.append("<");
        result.append(login);
        result.append(">");
        result.append("<");
        result.append(password);
        result.append(">");
        result.append("<");
        result.append(email);
        result.append(">");
        result.append("<");
        result.append(userDiscount);
        result.append(">");
        result.append("<");
        result.append(shoppingCart.getId());
        result.append(">\n");
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
            if (!buff.toString().equals("User"))
                throw new InvalidSerializationStringException("Type must be User");
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
        login = fields[1];
        password = fields[2];
        email = fields[3];
        try {
            userDiscount = Integer.parseInt(fields[4]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for discount");
        }
        shoppingCart = new ShoppingCart();
        try {
            shoppingCart.setId(Integer.parseInt(fields[5]));
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for shopping cart id");
        }

    }
}
