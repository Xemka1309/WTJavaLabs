package dao_shop.servicelayer;

import dao_shop.beans.User;
import dao_shop.servicelayer.exceptions.ServiceException;

public interface ClientService {
    public void registration(User user) throws ServiceException;
    public User signIn(String login, String password);
    public void signOut(String login);
}
