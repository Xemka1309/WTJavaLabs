package dbutill;

import java.util.Scanner;
public class Main {
    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        String command = "";
        Scanner scanner = new Scanner(System.in);
        while (!command.equals("exit")){
            System.out.println("Enter command(M/Bean or C/Bean)");
            if (command.equals("exit"))
                break;
            command = scanner.nextLine();
            System.out.println(controller.executeCommand(command));
        }

    }
}
