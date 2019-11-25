package dao_shop.servicelayer.implementation;

import dao_shop.beans.*;
import dao_shop.datalayer.ProductDataWorker;
import dao_shop.datalayer.UserDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import dao_shop.servicelayer.UserService;
import dao_shop.servicelayer.exceptions.ServiceException;

public class UserServiceImpl implements UserService {
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    //Todo:implement addorderitem,removeorderitem
    @Override
    public void addOrderItem(OrderItem item) throws ServiceException {
        try {
            item.setId(FileDataWorkerFactory.getInstance().getOrderItemDataWorker().nextFreeId());
            FileDataWorkerFactory.getInstance().getOrderItemDataWorker().addItem(item);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void removeOrderItem(OrderItem item) {
        FileDataWorkerFactory.getInstance().getOrderItemDataWorker().removeItem(item.getId());

    }

    @Override
    public void addOrder(Order order) throws ServiceException {
        if (order == null)
            throw new ServiceException("Order wass null");
        order.setId(FileDataWorkerFactory.getInstance().getOrderDataWorker().nextFreeId());
        try {
            FileDataWorkerFactory.getInstance().getOrderDataWorker().addOrder(order);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public ShoppingCart getCart(User user) throws ServiceException {
        if (user == null){
            throw new ServiceException("User was null");
        }
        try {
            return FileDataWorkerFactory.getInstance().getShoppingCartDataWorker().getCart(user.getShoppingCart().getId());
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    @Override
    public OrderItem[] getCartItems(ShoppingCart cart) throws ServiceException {
        try {
            boolean isnull = true;
            OrderItem[] items = FileDataWorkerFactory.getInstance().getOrderItemDataWorker().getItems();
            if (items == null)
                return null;
            OrderItem[] result = new OrderItem[items.length];
            int ind = 0;
            for (int i = 0; i < items.length; i++){
                if (items[i].getCartId() == cart.getId()){
                    result[ind] = items[i];
                    ind++;
                    isnull = false;
                }
            }
            if (isnull) return null;
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Order[] getOrders(User user) throws ServiceException {
        try {
            Order[] orders = FileDataWorkerFactory.getInstance().getOrderDataWorker().getOrders();
            Order[] result = new Order[orders.length];
            boolean nullres = true;
            int ind = 0;
            for (int i = 0; i< orders.length; i++){
                if (orders[i].getUser().getId() == user.getId()){
                    result[ind] = orders[i];
                    ind++;
                    nullres = false;
                }
            }
            if (nullres)
                return null;
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void addDelivery(DeliveryInfo info) throws ServiceException {
        info.setId(FileDataWorkerFactory.getInstance().getDeliveryInfoDataWorker().nextFreeId());
        try {
            FileDataWorkerFactory.getInstance().getDeliveryInfoDataWorker().addInfo(info);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void registration(User user) throws ServiceException {

        UserDataWorker userdao = FileDataWorkerFactory.getInstance().getUserDataWorker();
        user.setId(userdao.nextFreeId());
        ShoppingCart cart = new ShoppingCart();
        cart.setEndPrice(0);
        cart.setId(FileDataWorkerFactory.getInstance().getShoppingCartDataWorker().nextFreeId());
        user.setShoppingCart(cart);
        try {
            userdao.addUser(user);
            FileDataWorkerFactory.getInstance().getShoppingCartDataWorker().addCart(cart);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User signIn(String login, String password) throws ServiceException {
        if (login == null)
            throw new IllegalArgumentException("login was null");
        if (password == null)
            throw new IllegalArgumentException("password was null");
        UserDataWorker userdao = FileDataWorkerFactory.getInstance().getUserDataWorker();
        try {
            return currentUser = userdao.signIn(login,password);
        } catch (DAOException e) {
            throw new ServiceException("sign in not executed");
        }

    }

    @Override
    public void signOut(String login) {
        currentUser = null;

    }

    @Override
    public Product[] getProducts() throws ServiceException {
        ProductDataWorker productDataWorker =  FileDataWorkerFactory.getInstance().getProductDataWorker();
        try {
            return productDataWorker.getProducts();
        } catch (DAOException e) {
            throw new ServiceException("Can't get products");
        }
    }

    @Override
    public int findUserId(User user) throws ServiceException {
        try {
            return FileDataWorkerFactory.getInstance().getUserDataWorker().findUserId(user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
