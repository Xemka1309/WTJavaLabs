package dao_shop.controllers;

import dao_shop.beans.Product;
import dao_shop.beans.User;
import dao_shop.controllers.exceptions.CommandException;
import dao_shop.controllers.exceptions.InvalidCommandParamException;
import dao_shop.controllers.exceptions.NotEnoughtParamsException;
import dao_shop.servicelayer.implementation.ServiceFactory;
import dao_shop.servicelayer.exceptions.ServiceException;

public class Controller {
    private final char paramDelimeter = '/';
    private static User currentUser;
    private final static Controller instance = new Controller();
    public User getCurrentUser() {
        return currentUser;
    }

    public static Controller getInstance(){
        return instance;
    }
    public String ExecuteCommand(String request) throws CommandException {
        String command;
        String[] params = new String[0];
        if (request.indexOf('/') != -1){
            command = request.substring(0,request.indexOf(paramDelimeter));
            params = request.split("/");
            command.toLowerCase();
        }
        else
            command = request;
        String response = "ERROR";

        switch (command){
            case "sign_in":
                if (params.length < 3)
                    throw new NotEnoughtParamsException("Must be 2 params (login, password)");
                User user = null;
                if (params[1].equals("admin") && params[2].equals("admin"))
                    return "AdminOK";
                try {
                    user = ServiceFactory.getInstance().getUserService().signIn(params[1],params[2]);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                if (user == null)
                    response =  "Invalid login/password";
                else{
                    response = "welcome,"+user.getLogin();
                    currentUser = user;
                }

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
                try {
                    ServiceFactory.getInstance().getUserService().registration(newuser);
                    response = "OK";
                } catch (ServiceException e) {
                    response = "Not unique login";
                }

                break;
            case "show_products":
                try {
                    Product[] products = ServiceFactory.getInstance().getUserService().getProducts();
                    StringBuilder responsebuilder = new StringBuilder("Products\n");
                    for (int i = 0; i < products.length; i++){
                        responsebuilder.append("Name:" + products[i].getName() + "\n");
                        responsebuilder.append("Description:" + products[i].getDescription() + "\n");
                        responsebuilder.append("Price:" + products[i].getPrice() + "\n");
                    }
                    response = responsebuilder.toString();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                break;
            case "add_product":
                if (params.length < 4)
                    throw new NotEnoughtParamsException("Must be 3 params");
                Product product = new Product();
                product.setName(params[1]);
                product.setDescription(params[2]);
                product.setPrice(Integer.parseInt(params[3]));
                try {
                    ServiceFactory.getInstance().getAdminService().addProduct(product);
                } catch (ServiceException e) {
                    System.out.println("Can't execute command exc message:" + e.getMessage());
                }
                response = "CREATEOK";
                break;

            default:
                response = "Unknown command";
        }
        return response;
    }
}
