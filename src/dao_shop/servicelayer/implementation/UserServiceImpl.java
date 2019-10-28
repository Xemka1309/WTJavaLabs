package dao_shop.servicelayer.implementation;

import dao_shop.beans.Order;
import dao_shop.beans.OrderItem;
import dao_shop.beans.User;
import dao_shop.datalayer.UserDataWorker;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import dao_shop.servicelayer.UserService;

public class UserServiceImpl implements UserService {
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
    public void registration(User user) {
        UserDataWorker userdao = FileDataWorkerFactory.getInstance().getUserDataWorker();
        userdao.addUser(user);
    }

    @Override
    public void signIn(String login, String password) {
        UserDataWorker userdao = FileDataWorkerFactory.getInstance().getUserDataWorker();
        userdao.signIn(login,password);
    }

    @Override
    public void signOut(String login) {

    }
}
