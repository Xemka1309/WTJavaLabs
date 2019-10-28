package dao_shop.controllers;

import dao_shop.beans.ShoppingCart;
import dao_shop.beans.User;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.controllers.exceptions.InvalidCommandParamException;
import dao_shop.controllers.exceptions.NotEnoughtParamsException;
import dao_shop.servicelayer.ServiceFactory;

public class Controller {
    private final char paramDelimeter = ' ';
    public String ExecuteCommand(String request) throws CommandException {
        String response = "ERROR";
        String command = request.substring(0,request.indexOf(paramDelimeter));
        String[] params = request.split(" ");
        command.toLowerCase();
        switch (command){
            case "sign_in":
                if (params.length < 3)
                    throw new NotEnoughtParamsException("Must be 2 params (login, password)");
                User user = ServiceFactory.getInstance().getUserService().signIn(params[1],params[2]);
                if (user == null)
                    response =  "Invalid login/password";
                else
                    response = "welcome,"+user.getLogin();
                break;
            case "create_user":
                if (params.length < 5)
                    throw new NotEnoughtParamsException("Must be 4 params (login, password, confirm password , email)");
                if (!params[2].equals(params[3]))
                    throw new InvalidCommandParamException("Password and password confirm must be equal");
                User newuser = new User();
                newuser.setLogin(params[1]);
                newuser.setPassword(params[2]);
                newuser.setUserDiscount(0);
                newuser.setEmail(params[4]);
                ServiceFactory.getInstance().getUserService().registration(newuser);
                response = "OK";
                break;

            default:
                response = "Unknown command";
        }
        return response;
    }
}
