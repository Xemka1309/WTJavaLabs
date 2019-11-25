package dbutill;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import com.mysql.jdbc.*;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import javax.naming.spi.DirectoryManager;

public class DB {
    private Connection connection;
    public void CreateTables(Statement statement) throws SQLException {
        statement.execute("DROP TABLE Products");
        statement.execute
                       ("CREATE TABLE Products (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Price INT, " +
                        "Name VARCHAR(50) UNIQUE, " +
                        "Description VARCHAR(50) " +
                        ")");
        statement.execute
                       ("CREATE TABLE Users (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Discount INT, " +
                        "Login VARCHAR(30) UNIQUE, " +
                        "Password VARCHAR(30), Email VARCHAR(30)" +
                        ")");
        statement.execute
                ("CREATE TABLE ShoppingCarts (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "EndPrice INT " +
                        ")");
        statement.execute
                ("CREATE TABLE OrderItems (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ProductId INT, CartId INT, "+
                        "FOREIGN KEY (ProductId)  REFERENCES Products(Id), " +
                        "FOREIGN KEY (CartId)  REFERENCES ShoppingCarts(Id), " +
                        "EndPrice INT, Count INT" +
                        ")");
        statement.execute
                ("CREATE TABLE DeliveryInfos (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Adress VARCHAR(50), " +
                        "Phone VARCHAR(50), " +
                        "DateTime DATETIME" +
                        ")");
        statement.execute
                ("CREATE TABLE Orders (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "CartId INT, DeliveryId INT, UserId INT, "+
                        "FOREIGN KEY (DeliveryId)  REFERENCES DeliveryInfos(Id), " +
                        "FOREIGN KEY (CartId)  REFERENCES ShoppingCarts(Id), " +
                        "FOREIGN KEY (UserId)  REFERENCES Users(Id), " +
                        "EndPrice INT" +
                        ")");
    }

    public Connection getConnection(){
        return connection;
    }
    public void ConnectToDB() throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/epam?serverTimezone=Europe/Moscow&useSSL=false", "root", "root");
    }
    public void CloseConnection() throws SQLException{
        connection.close();
    }
    public void MigrateUsers(){

    }
    public void MigrateProducts() throws DAOException, SQLException {
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getProductDataWorker();
        var products = worker.getProducts();
        var statement = connection.createStatement();
        for (var product:products){
            statement.execute(String.format("INSERT Products(Name,Price,Description) VALUES ('%s',%s,'%s') ",
                    product.getName(),product.getPrice(),product.getDescription()));

        }

    }
}
