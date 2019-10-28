package dao_shop.view;

import dao_shop.beans.User;

public class ClientForm {
    private User user;
    public ClientForm(User user){
        this.user = user;
    }
    public void ShowWelcome(){
        System.out.println("Welcome back, " + user.getLogin());
        System.out.println("Available commands: ");
        System.out.println("show_products - view all sailing products");
        System.out.println("add_to_cart - add product to your shopping cart");
        System.out.println("show_ your_cart - view your cart");
        System.out.println("remove_from_cart - remove product from cart");
        System.out.println("create_order - create order from your cart");
        System.out.println("exit - logout from shop");
    }
}
