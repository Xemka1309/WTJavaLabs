package dao_shop;

public interface UserDataWorker {
    public void getUsers();
    public User getUser(int id);
    public void addUser(User user);
    public void removeUser(int id);
    public void modifyUser(int id, User newUser);
}
