package dbutill;

import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserMigrationManager implements MigrationManager {
    private Connection connection;
    @Override
    public void OpenConnection() throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/epam?serverTimezone=Europe/Moscow&useSSL=false", "root", "root");
    }

    @Override
    public void CloseConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void CreateTable(boolean reCreate) throws SQLException {
        if (reCreate)
            connection.createStatement().execute("DROP TABLE Users");
        connection.createStatement().execute
                ("CREATE TABLE Users (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Discount INT, " +
                        "Login VARCHAR(30) UNIQUE, " +
                        "Password VARCHAR(30), Email VARCHAR(30)" +
                        ")");
    }

    @Override
    public void Migrate() throws SQLException, DAOException {
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getUserDataWorker();
        var users = worker.getUsers();
        var statement = connection.createStatement();
        for (var user:users){
            statement.execute(String.format("INSERT Users(Login,Password,Email,Discount) VALUES ('%s','%s','%s',%s)",
                    user.getLogin(),user.getPassword(),user.getEmail(),user.getUserDiscount()));
        }
    }
}
