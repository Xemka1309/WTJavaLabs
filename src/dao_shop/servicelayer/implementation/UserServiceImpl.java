package dao_shop.servicelayer.implementation;

import dao_shop.beans.Order;
import dao_shop.beans.OrderItem;
import dao_shop.beans.ShoppingCart;
import dao_shop.beans.User;
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

    @Override
    public void addOrderItem(OrderItem item) {

    }

    @Override
    public void removeOrderItem(OrderItem item) {

    }

    @Override
    public void addOrder(Order order) {

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
    public User signIn(String login, String password) {
        UserDataWorker userdao = FileDataWorkerFactory.getInstance().getUserDataWorker();
        try {
            return currentUser = userdao.signIn(login,password);
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void signOut(String login) {
        currentUser = null;

    }
}
