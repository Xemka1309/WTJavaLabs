package dao_shop.view.consoleviewimpl;

import dao_shop.beans.Product;
import dao_shop.controllers.Controller;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.view.AdminView;

import java.util.Scanner;

public class AdminForm implements AdminView {
    private AuthorizationForm authorizationForm;
    private Scanner scanner = new Scanner(System.in);
    public AdminForm(AuthorizationForm form){
        this.authorizationForm = form;
    }
    private void ShowCommands(){
        System.out.println("Available commands: ");
        System.out.println("add product");
        System.out.println("remove product");
        System.out.println("show products");
        System.out.println("modify product");
        System.out.println("log out");
    }
    @Override
    public void ShowWelcome() {
        System.out.println("Welcome admin");
        String command = "";
        ShowCommands();
        while (!command.equals("log_out")){

            command = scanner.nextLine();
            switch (command){
                case "show commands":
                    ShowCommands();
                    break;
                case "add product":
                    AddProduct();
                    break;
                case "show products":
                    ShowProducts();
                    break;
                case"remove product":
                    RemoveProduct();
                    break;
                case"modify product":
                    ModifyProduct();
                    break;
                case "log out":
                    LogOut();
                    break;
            }
        }
    }

    public void LogOut(){
        System.out.println("Goodbye America, oo, gde ne bil nikogda");
        authorizationForm.ShowWelcome();
    }
    @Override
    public void ShowProducts() {
        try {
            String response = Controller.getInstance().ExecuteCommand("show_products_admin");
            System.out.println(response);
        } catch (CommandException e) {
            System.out.println("Can't execute command ex message:" + e.getMessage());
        }

    }

    @Override
    public void AddProduct() {
        int price;
        String name,description;
        Product product = new Product();
        System.out.println("Enter product name");
        product.setName(name = scanner.nextLine());
        System.out.println("Enter product description");
        product.setDescription(description = scanner.nextLine());
        System.out.println("Enter product price");
        try {
            price = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("wrong price value");
            return;
        }
        try {
            String response = Controller.getInstance().ExecuteCommand("add_product/"+name+"/"+ description + "/" + price);
            if (response.equals("CREATEOK"))
                System.out.println("Created!");
        } catch (CommandException e) {
            System.out.println("Can't add product");
        }
    }

    @Override
    public void RemoveProduct() {
        ShowProducts();
        System.out.println("Enter product id to remove");
        int id = -1;
        try{
            id = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid id");
            return;
        }
        try {
            System.out.println(Controller.getInstance().ExecuteCommand("remove_product/"+id));
        } catch (CommandException e) {
            System.out.println("Command can't be executed");
            return;
        }

    }

    @Override
    public void ModifyProduct() {
        ShowProducts();
        System.out.println("Enter product id to modify");
        int id = -1;
        try{
            id = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid id");
            return;
        }
        try {
            System.out.println("Enter new name");
            String name = scanner.nextLine();
            System.out.println("Enter new description");
            String descr = scanner.nextLine();
            System.out.println("Enter new price");
            String price = scanner.nextLine();
            try {
                Integer.parseInt(price);
            }
            catch (NumberFormatException e){
                System.out.println("Invalid price value");
                return;
            }
            Controller.getInstance().ExecuteCommand("modefy/"+id +"/" + name + "/" + descr + "/" + price);
        } catch (CommandException e) {
            System.out.println("Command can't be executed");
            return;
        }

    }
}
