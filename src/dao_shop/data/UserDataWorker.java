package dao_shop.data;
import dao_shop.beans.User;
public interface UserDataWorker {
    public User[] getUsers();
    public User getUser(int id);
    public void addUser(User user);
    public void removeUser(int id);
    public void modifyUser(int id, User newUser);
}
