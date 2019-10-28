package dao_shop.view;

import dao_shop.controllers.Controller;
import dao_shop.controllers.exceptions.CommandException;

import java.util.Scanner;

public class AuthorizationForm {
    public AuthorizationForm(){

    }
    public void ShowWelcome(){
        System.out.println("Welcome, please enter command");
        System.out.println("Available commands: create_user - registration; sign_in - authorization ");
        Scanner scanner = new Scanner(System.in);
        //String command = scanner.nextLine();
        String command = "create_user";
        Controller controller = new Controller();
        switch (command){
            case "create_user":
                System.out.println("Enter params, separate with space: login, pass, confirm pass, email");
                //command = command + " " + scanner.nextLine();
                command = "create_user xemka pass pass email";
                try {
                    String response = controller.ExecuteCommand(command);
                    if (response.equals("OK"))
                        System.out.println("Successfully create new user");
                } catch (CommandException e) {
                    System.out.println("Wrong command");
                }
                break;
        }
    }
}
