package dao_shop.controllers;

import dao_shop.beans.*;
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
        int id;
        Product p;
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
                    if (products == null){
                        response = "No products in base\n";
                        return response;
                    }
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
            case "show_products_admin":
                try {
                    Product[] products = ServiceFactory.getInstance().getUserService().getProducts();
                    if (products == null){
                        response = "No products in base\n";
                        return response;
                    }
                    StringBuilder responsebuilder = new StringBuilder("Products\n");
                    for (int i = 0; i < products.length; i++){
                        responsebuilder.append("Id:" + products[i].getId() + "\n");
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
            case "remove_product":
                if (params.length < 2)
                    throw new NotEnoughtParamsException("Must be 1 param");
                id = Integer.parseInt(params[1]);
                p = new Product();
                p.setId(id);
                ServiceFactory.getInstance().getAdminService().removeProduct(p);
                response="REMOVEOK";
                break;
            case "show_cart":{
                if (params.length < 2)
                    throw new NotEnoughtParamsException("Must be 1 param");
                id = Integer.parseInt(params[1]);
                user = new User();
                user.setId(id);

                try {
                    ShoppingCart cart = new ShoppingCart();
                    cart.setId(currentUser.getShoppingCart().getId());
                    OrderItem[] items = ServiceFactory.getInstance().getUserService().getCartItems(cart);
                    if (items == null){
                        response = "Empty cart";
                        return response;
                    }
                    StringBuilder orderitemsbuilder = new StringBuilder();
                    cart.setEndPrice(0);
                    for (int i = 0; i < items.length; i++){
                        if (items[i].getCartId() == cart.getId()){
                            orderitemsbuilder.append("Product:"+ServiceFactory
                                    .getInstance().getAdminService().getProduct(items[i].getProductId()).getName()+"\n");
                            orderitemsbuilder.append("Count:" + items[i].getCount() + "\n");
                            orderitemsbuilder.append("Endprice of item:" + items[i].getEndPrice()+"\n");
                            cart.setEndPrice(cart.getEndPrice() + items[i].getEndPrice());
                        }
                        orderitemsbuilder.append("All cart price:" + cart.getEndPrice()+"\n");
                    }
                    response = orderitemsbuilder.toString();
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
            case "add_to_cart":
                if (params.length < 3)
                    throw new NotEnoughtParamsException("Must be 2 param");
                id = Integer.parseInt(params[1]);
                int count = Integer.parseInt(params[2]);
                OrderItem orderItem = new OrderItem();
                orderItem.setCount(count);
                try {
                    orderItem.setEndPrice(ServiceFactory.getInstance().getAdminService().getProduct(id).getPrice() * count);
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
                orderItem.setProductId(id);
                orderItem.setCartId(currentUser.getShoppingCart().getId());
                try {
                    ServiceFactory.getInstance().getUserService().addOrderItem(orderItem);
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
                response = "CARTADDOK";
                break;
            case "add_order":
                if (params.length < 5)
                    throw new NotEnoughtParamsException("Must be 4 param");
                DeliveryInfo info = new DeliveryInfo();
                info.setDate(params[2]);
                info.setAdress(params[3]);
                info.setPhoneNumber(params[4]);
                Order order = new Order();
                order.setUser(currentUser);
                order.setDeliveryInfo(info);
                try {
                    ServiceFactory.getInstance().getUserService().addDelivery(info);
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }

                try {
                    order.setShoppingCart(ServiceFactory.getInstance().getUserService().getCart(currentUser));
                    order.setEndPrice(order.getShoppingCart().getEndPrice());
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    ServiceFactory.getInstance().getUserService().addOrder(order);
                    response="ORDERADDOK";
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "show_orders":
                try {
                    Order[] orders = ServiceFactory.getInstance().getUserService().getOrders(currentUser);
                    if (orders == null){
                        return "You have no orders";
                    }
                    StringBuilder ordersbuiler = new StringBuilder();
                    for (int i = 0; i < orders.length; i++){
                        if (orders[i].getUser() != null){
                            ordersbuiler.append("Order №" + i + "\n");
                            ordersbuiler.append("Delivery:_______________\n");
                            ordersbuiler.append("Date:" + orders[i].getDeliveryInfo().getDate() + "\n");
                            ordersbuiler.append("Adress:" + orders[i].getDeliveryInfo().getAdress()+ "\n");
                            ordersbuiler.append("Phone Num:" + orders[i].getDeliveryInfo().getPhoneNumber()+ "\n");
                            ordersbuiler.append("______________________________________\n");
                            ordersbuiler.append("Products in order\n");
                            OrderItem[] products = ServiceFactory.getInstance().getUserService().getCartItems(orders[i].getShoppingCart());
                            if (products == null)
                                return "Invalid Order";
                            for (int j = 0; j < products.length; j++){
                                if (products[j].getCartId() == orders[i].getShoppingCart().getId()){
                                    ordersbuiler.append("Product:" + ServiceFactory.getInstance().
                                            getAdminService().getProduct(products[j].getProductId())+"\n");
                                    ordersbuiler.append("Count:" + products[j].getCount() + "\n");
                                    ordersbuiler.append("Item endprice:" + products[j].getEndPrice() + "\n");
                                }
                            }
                            return ordersbuiler.toString();
                        }
                    }
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "modify_product":
                if (params.length < 5)
                    throw new NotEnoughtParamsException("Must be 4 param");
                id = Integer.parseInt(params[1]);
                p = new Product();
                p.setId(id);
                Product newProduct= new Product();
                newProduct.setName(params[2]);
                newProduct.setDescription(params[3]);
                newProduct.setPrice(Integer.parseInt(params[4]));
                try {
                    ServiceFactory.getInstance().getAdminService().modifyProduct(p,newProduct);
                    response = "MODIFYOK";
                } catch (ServiceException e) {
                    System.out.println(e.getMessage());
                }

                break;

            default:
                response = "Unknown command";
        }
        return response;
    }
}
