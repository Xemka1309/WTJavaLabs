package dao_shop.servicelayer;

import dao_shop.beans.Product;
import dao_shop.beans.User;
import dao_shop.servicelayer.exceptions.ServiceException;

// Base interface for both of client, admin, etc.
public interface ClientService {
    public void registration(User user) throws ServiceException;
    public User signIn(String login, String password) throws ServiceException;
    public void signOut(String login);
    public Product[] getProducts() throws ServiceException;
    public int findUserId(User user) throws ServiceException;
}
