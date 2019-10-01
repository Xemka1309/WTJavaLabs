package dao_shop;
import java.io.Serializable;
public class Product implements Serializable {
    private int id;
    private  String name;
    private int price;
    private  String description;
    private  ProductCategory category;
    private boolean available;

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

    public ProductCategory getCategory() {
        return category;
    }
    public void setCategory(ProductCategory category){
        this.category = category;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }
    public boolean isAvaiable(){
        return available;
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
        return  name.toString() + description.toString() + price + category.toString();
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
        if (product.category != category)
            return false;
        if (product.available != available)
            return false;
        return true;

    }
}
