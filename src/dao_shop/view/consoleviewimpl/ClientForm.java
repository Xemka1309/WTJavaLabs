package dao_shop.view.consoleviewimpl;

import dao_shop.beans.User;
import dao_shop.controllers.Controller;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.view.ClientView;

import java.util.Scanner;

public class ClientForm implements ClientView {
    private User user;
    // Todo:заменить на синглтон
    private Controller controller = Controller.getInstance();

    public ClientForm() {
        this.user = controller.getCurrentUser();
    }

    @Override
    public void ShowWelcome(){
        System.out.println("Welcome back, " + user.getLogin());
        System.out.println("Available commands: ");
        System.out.println("show_products - view all sailing products");
        System.out.println("add_to_cart - add product to your shopping cart");
        System.out.println("show_ your_cart - view your cart");
        System.out.println("remove_from_cart - remove product from cart");
        System.out.println("create_order - create order from your cart");
        System.out.println("exit - logout from shop");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        switch (command){
            case "show_products":
                ShowProducts();
                break;
        }
    }

    @Override
    public void ShowProducts() {
        try {
            System.out.print(controller.ExecuteCommand("show_products"));
        } catch (CommandException e) {
            System.out.println("Can't show products");
        }

    }

    @Override
    public void ShowShoppingCart() {

    }

    @Override
    public void ShowOrder() {

    }
}
