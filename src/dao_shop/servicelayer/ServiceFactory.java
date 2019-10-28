package dao_shop.servicelayer;

import dao_shop.servicelayer.implementation.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instanse = new ServiceFactory();
    private final UserService userService = new UserServiceImpl();

    public UserService getUserService() {
        return userService;
    }

    private ServiceFactory(){}
    public static ServiceFactory getInstance(){
        return instanse;
    }
}
