package dao_shop.datalayer.fileworkers;

import dao_shop.beans.ShoppingCart;
import dao_shop.beans.User;
import dao_shop.datalayer.UserDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUserDataWorker implements UserDataWorker {
    private String dirpass;
    private int nextFreeId = -1;

    public FileUserDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }

    @Override
    public User signIn(String login, String password) throws DAOException {
        User[] users = getUsers();
        for (int i=0; i< users.length; i++){
            if (users[i].getLogin().equals(login) && users[i].getPassword().equals(password))
                return users[i];
        }
        return null;
    }

    private void LoadShoppingCarts(User[] users) throws DAOException {
        File[] files = new File("ShoppingCarts").listFiles();
        ShoppingCart[] carts = new ShoppingCart[files.length];
        FileReader reader;
        StringBuilder builder = new StringBuilder();
        int symb;
        for (int i = 0; i < files.length; i++) {
            symb = -1;
            try {
                reader = new FileReader(files[i]);
                symb = reader.read();
                while (symb != -1) {
                    builder.append((char) symb);
                    symb = reader.read();
                }
                carts[i] = new ShoppingCart();
                carts[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());


            } catch (IOException | InvalidSerializationStringException e) {
                throw new DAOException("Can't get carts");
            }

        }
        for (int i = 0; i < carts.length; i++){
            for (int j = 0; j < users.length; j++ ){
                if (users[j].getShoppingCart().getId() == carts[i].getId()){
                    users[j].setShoppingCart(carts[i]);
                }
            }
        }



    }
    @Override
    public User[] getUsers() throws DAOException {
        //nextFreeId = 0;
        File[] files = new File(dirpass).listFiles();
        User[] users = new User[files.length];
        FileReader reader;
        StringBuilder builder = new StringBuilder();
        int symb;
        for (int i = 0; i < files.length; i++) {
            symb = -1;
            try {
                reader = new FileReader(files[i]);
                symb = reader.read();
                while (symb != -1) {
                    builder.append((char) symb);
                    symb = reader.read();
                }
                users[i] = new User();
                users[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());
                if (users[i].getId() > nextFreeId)
                    nextFreeId = users[i].getId() + 1;

            } catch (IOException | InvalidSerializationStringException e) {
                throw new DAOException("Can't get users");
            }

        }
        LoadShoppingCarts(users);
        if (nextFreeId == -1){
            nextFreeId++;
            return null;
        }

        return users;

    }

    @Override
    public User getUser(int id) throws DAOException {
        User result = new User();
        File file = new File(dirpass + "/" + id);
        int symb;
        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            symb = reader.read();
            while (symb != -1) {
                builder.append((char) symb);
                symb = reader.read();
            }
            result.DeSerialize(builder.toString());
            reader.close();
        } catch (IOException | InvalidSerializationStringException e) {
            throw new DAOException("Can't get user with id:"+id);
        }
        return result;
    }

    @Override
    public void addUser(User user) throws DAOException {
        User[] users = getUsers();
        if (users != null){
            boolean unique = true;
            for (int i = 0; i < users.length; i++){
                if (user.getLogin().equals(users[i].getLogin()))
                    unique = false;
            }
            if (!unique)
                throw new DAOException("Not unique login");
        }
        File file = new File(dirpass + "/" + user.getId());
        FileWriter writer;
        StringBuilder buff = new StringBuilder();
        int symb;
        try {
            writer = new FileWriter(file);
            writer.write(user.Serialize());
            writer.close();
        } catch (IOException e) {
            throw new DAOException("Can't add user");
        }
    }

    @Override
    public void removeUser(int id) {
        File file = new File(dirpass + "/" + id);
        file.delete();
    }

    @Override
    public void modifyUser(int id, User newUser) throws DAOException {
        removeUser(id);
        newUser.setId(id);
        addUser(newUser);
    }

    @Override
    public int nextFreeId() {

        if (nextFreeId != -1)
            return nextFreeId++;
        else{
            try {
                getUsers();
            } catch (DAOException e) {
                return 0;
            }
            return nextFreeId;
        }
    }

    @Override
    public int findUserId(User user) throws DAOException {
        User[] users = getUsers();
        for (int i = 0; i < users.length; i++){
            if (users[i].getLogin() == user.getLogin() && users[i].getPassword() == user.getPassword()
                    && users[i].getEmail() == user.getEmail())
                return i;
        }
        return -1;
    }
}
