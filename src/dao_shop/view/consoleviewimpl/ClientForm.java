package dao_shop.view.consoleviewimpl;

import dao_shop.beans.ShoppingCart;
import dao_shop.beans.User;
import dao_shop.controllers.Controller;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import dao_shop.view.ClientView;

import java.util.Scanner;

public class ClientForm implements ClientView {
    private AuthorizationForm authorizationForm;
    private User user;
    private Scanner scanner = new Scanner(System.in);
    private Controller controller = Controller.getInstance();

    public ClientForm(AuthorizationForm form) {
        this.authorizationForm = form;
        this.user = controller.getCurrentUser();
    }

    private void ShowCommands(){
        System.out.println("Available commands:_______________________________");
        System.out.println("show_comands- view all comands_________");
        System.out.println("show_products - view all sailing products_________");
        System.out.println("add_to_cart - add product to your shopping cart___");
        System.out.println("show_cart - view your cart__________________");
        System.out.println("remove_from_cart - remove product from cart_______");
        System.out.println("create_order - create order from your cart________");
        System.out.println("log_out - log out from shop_______________________");
    }
    @Override
    public void ShowWelcome(){
        System.out.println("Welcome back, " + user.getLogin());
        String command = "";
        ShowCommands();
        while (!command.equals("log_out")){

            command = scanner.nextLine();
            switch (command){
                case "show_commands":
                    ShowCommands();
                    break;
                case "show_products":
                    ShowProducts();
                    break;
                case "show_cart":
                    ShowShoppingCart();
                    break;
                case "add_to_cart":
                    AddToCart();
                    break;
                case "log_out":
                    LogOut();
                    break;
                case "create_order":
                    CreateOrder();
                    break;
                case "show_orders":
                    ShowOrders();
                    break;
            }
        }
    }

    private void LogOut(){
        System.out.println("Goodbye America, oo, gde ne bil nikogda");
        authorizationForm.ShowWelcome();
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
        try {
            System.out.println("Your Shopping Cart");
            System.out.println(controller.ExecuteCommand("show_cart" +"/"+ controller.getCurrentUser().getId()));
        } catch (CommandException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ShowOrder() {

    }

    @Override
    public void AddToCart() {
        System.out.println("Enter product id");
        String id = scanner.nextLine();
        System.out.println("Enter product count");
        String count = scanner.nextLine();
        try {
            Integer.parseInt(id);
            Integer.parseInt(count);
        }
        catch (NumberFormatException e){
            System.out.println("Wrong integer value");
        }
        try {
            System.out.println(controller.ExecuteCommand("add_to_cart/"+id+"/"+count));
        } catch (CommandException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void RemoveFromCart() {

    }

    @Override
    public void ShowOrders() {
        System.out.println("Your orders\n");
        try {
            System.out.println(controller.ExecuteCommand("show_orders"));
        } catch (CommandException e) {
            System.out.println("Error executing command");
        }

    }

    @Override
    public void CreateOrder() {
        ShowShoppingCart();
        System.out.println("Create delivery");
        System.out.println("Enter date of delivery");
        String date = scanner.nextLine();
        System.out.println("Enter adress of delivery");
        String adress = scanner.nextLine();
        System.out.println("Enter your phone number of delivery");
        String phonenumber = scanner.nextLine();
        String command = "add_order/"+user.getId()+"/"+ date+"/"+adress+"/"+phonenumber;
        try {
            System.out.println(controller.ExecuteCommand(command));
        } catch (CommandException e) {
            System.out.println("Invalid params");
        }


    }
}
