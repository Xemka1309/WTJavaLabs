package dao_shop.servicelayer.implementation;

import dao_shop.servicelayer.AdminService;
import dao_shop.servicelayer.UserService;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final AdminService adminService = new AdminServiceImpl();
    private final UserService userService = new UserServiceImpl();



    private ServiceFactory(){}
    public static ServiceFactory getInstance(){
        return instance;
    }

    public UserService getUserService() {

        return userService;
    }
    public AdminService getAdminService() {
        return adminService;
    }
}
