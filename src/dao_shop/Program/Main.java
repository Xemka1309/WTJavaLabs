package dao_shop.Program;

import dao_shop.beans.*;
import dao_shop.data.fileworkers.DataWorker;
import dao_shop.data.myserialize.InvalidSerializationStringException;

public class Main{
    public static void main(String[] args){
        Product p1 = new Product();
        p1.setPrice(100);
        p1.setName("product1");
        p1.setId(1);
        p1.setDescription("descr1");
        Product p2 = new Product();
        p2.setPrice(100);
        p2.setName("product2");
        p2.setId(2);
        p2.setDescription("descr2");
        ShoppingCart cart = new ShoppingCart();

        cart.setId(10);
        cart.setEndPrice(1000);

        User user = new User();
        user.setEmail("@email");
        user.setId(100);
        user.setLogin("login");
        user.setPassword("pass");
        user.setUserDiscount(10);
        user.setShoppingCart(cart);

        OrderItem item1 = new OrderItem();
        item1.setCartId(cart.getId());
        item1.setCount(2);
        item1.setProductId(p1.getId());
        item1.setEndPrice(p1.getPrice()*item1.getCount());

        OrderItem item2 = new OrderItem();
        item2.setCartId(cart.getId());
        item2.setCount(2);
        item2.setProductId(p1.getId());
        item2.setEndPrice(p1.getPrice()*item2.getCount());

        DeliveryInfo info = new DeliveryInfo();
        info.setAdress("adress");
        info.setPhoneNumber("phone");
        info.setDate("date");
        info.setId(1);

        Order order = new Order();
        order.setDeliveryInfo(info);
        order.setShoppingCart(cart);
        order.setUser(user);
        order.setEndPrice(cart.getEndPrice());

        DataWorker dataWorker = DataWorker.getInstance();
        dataWorker.getOrderDataWorker().addOrder(order);
        try{
            User newuser = new User();
            newuser.DeSerialize(user.Serialize());
            System.out.println(newuser.getId());
            System.out.println(newuser.getLogin());
            System.out.println(newuser.getPassword());
            System.out.println(newuser.getUserDiscount());
        }
        catch (InvalidSerializationStringException ex){
            System.out.println("error");
        }



    }
}