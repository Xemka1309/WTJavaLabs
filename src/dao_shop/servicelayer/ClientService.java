package dao_shop.servicelayer;

import dao_shop.beans.User;

public interface ClientService {
    public void registration(User user);
    public void signIn(String login, String password);
    public void signOut(String login);
}
