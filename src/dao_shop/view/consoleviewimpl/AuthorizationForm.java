package dao_shop.view.consoleviewimpl;

import dao_shop.controllers.Controller;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.view.AuthorizationView;

import java.util.Scanner;

public class AuthorizationForm implements AuthorizationView {
    private Controller controller = Controller.getInstance();
    public AuthorizationForm(){

    }
    @Override
    public void ShowWelcome(){
        System.out.println("Welcome, please enter command");
        System.out.println("Available commands: create_user - registration; sign_in - authorization ,exit - close program ");
        Scanner scanner = new Scanner(System.in);
        String command= "nexttry";
        Controller controller = new Controller();
        while (!command.equals("exit")){
            command = scanner.nextLine();
            switch (command){
                case "create_user":
                    System.out.println("Enter params, separate with /: login, pass, confirm pass, email");
                    command = command + "/" + scanner.nextLine();
                    try {
                        String response = controller.ExecuteCommand(command);
                        if (response.equals("OK"))
                            System.out.println("Successfully create new user");
                        else
                            System.out.println(response);

                    } catch (CommandException e) {
                        System.out.println("Wrong command");
                    }
                    break;
                case "sign_in":
                    System.out.println("Enter params, separate with slash(/): login, pass");
                    command = command + "/" + scanner.nextLine();
                    try {
                        String response = controller.ExecuteCommand(command);
                        System.out.println(response);
                        if (response.equals("AdminOK")){
                            AdminForm adminForm = new AdminForm(this);
                            adminForm.ShowWelcome();

                        }
                        else {
                            ClientForm clientForm = new ClientForm(this);
                            clientForm.ShowWelcome();
                        }


                    } catch (CommandException e) {
                        System.out.println("Wrong command");
                    }
                    break;
                case "exit":
                    return;
                default:
                    command = "nexttry";
                    break;
            }

        }

    }
}
