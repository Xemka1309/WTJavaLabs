package dao_shop.datalayer;
import dao_shop.beans.User;
import dao_shop.datalayer.exceptions.DAOException;

public interface UserDataWorker {
    public User signIn(String login, String password) throws DAOException;
    public User[] getUsers() throws DAOException;
    public User getUser(int id) throws DAOException;
    public void addUser(User user) throws DAOException;
    public void removeUser(int id);
    public void modifyUser(int id, User newUser) throws DAOException;
    public int nextFreeId();
}
