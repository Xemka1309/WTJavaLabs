package dao_shop.view.consoleviewimpl;

import dao_shop.beans.Product;
import dao_shop.controllers.Controller;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.view.AdminView;

import java.util.Scanner;

public class AdminForm implements AdminView {
    private Scanner scanner = new Scanner(System.in);
    private void ShowCommands(){
        System.out.println("Available commands: ");
        System.out.println("add_product");
        System.out.println("remove_product");
        System.out.println("show_products");
        System.out.println("modify_product");
        System.out.println("log_out");
    }
    @Override
    public void ShowWelcome() {
        System.out.println("Welcome admin");
        String command = "";

        while (!command.equals("log_out")){
            ShowCommands();
            command = scanner.nextLine();
            switch (command){
                case "add_product":
                    AddProduct();
                    break;
                case "show_products":
                    ShowProducts();
                    break;
            }
        }
    }

    @Override
    public void ShowProducts() {
        try {
            String response = Controller.getInstance().ExecuteCommand("show_products");
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
}
