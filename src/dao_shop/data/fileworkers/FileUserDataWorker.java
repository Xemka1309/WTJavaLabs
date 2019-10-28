package dao_shop.data.fileworkers;

import dao_shop.beans.User;
import dao_shop.data.UserDataWorker;
import dao_shop.data.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUserDataWorker implements UserDataWorker {
    private String dirpass;
    private int nextFreeId;

    public FileUserDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }

    @Override
    public User[] getUsers() {
        nextFreeId = 0;
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
                    nextFreeId = users[i].getId();

            } catch (IOException | InvalidSerializationStringException e) {
                e.printStackTrace();
            }

        }
        nextFreeId++;
        return users;

    }

    @Override
    public User getUser(int id) {
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
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addUser(User user) {
        File file = new File(dirpass + "/" + user.getId());
        FileWriter writer;
        StringBuilder buff = new StringBuilder();
        int symb;
        try {
            writer = new FileWriter(file);
            writer.write(user.Serialize());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(int id) {

    }

    @Override
    public void modifyUser(int id, User newUser) {

    }
}
