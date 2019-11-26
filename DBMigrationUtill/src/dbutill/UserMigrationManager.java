package dbutill;

import dao_shop.beans.User;
import dao_shop.datalayer.UserDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class UserMigrationManager implements MigrationManager {
    private Connection connection;
    private final static Logger logger = Logger.getLogger(UserMigrationManager.class);
    @Override
    public void OpenConnection()  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            logger.error("Sql Driver Error:" + e.getMessage());
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/epam?serverTimezone=Europe/Moscow&useSSL=false", "root", "root");
        } catch (SQLException e) {
            logger.error("Sql Error:" + e.getMessage() + "Sql state" +  e.getSQLState());
        }
    }

    @Override
    public void CloseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Sql Error" + e.getMessage() + e.getSQLState());
        }
    }

    @Override
    public void CreateTable(boolean reCreate){
        if (reCreate) {
            try {
                connection.createStatement().execute("TRUNCATE TABLE Users");
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        try {
            connection.createStatement().execute
                    ("CREATE TABLE Users (" +
                            "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "Discount INT, " +
                            "Login VARCHAR(30) UNIQUE, " +
                            "Password VARCHAR(30), Email VARCHAR(30)" +
                            ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Migrate(){
        if (connection == null)
            OpenConnection();
        UserDataWorker worker = FileDataWorkerFactory.getInstance().getUserDataWorker();
        User[] users = new User[0];
        try {
            users = worker.getUsers();
        } catch (DAOException e) {
            logger.error("DAO error" + e.getMessage());
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
        for (User user:users){
            try {
                statement.execute(String.format("INSERT Users(Login,Password,Email,Discount) VALUES ('%s','%s','%s',%s)",
                        user.getLogin(),user.getPassword(),user.getEmail(),user.getUserDiscount()));
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        CloseConnection();
    }
}
