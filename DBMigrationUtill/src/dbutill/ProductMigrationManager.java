package dbutill;

import dao_shop.beans.Product;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductMigrationManager implements MigrationManager {
    private Connection connection;
    private final Logger logger = Logger.getLogger(ProductMigrationManager.class);

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
                connection.createStatement().execute("DROP TABLE Products");
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        try {
            connection.createStatement().execute
                    ("CREATE TABLE Products (" +
                            "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "Price INT, " +
                            "Name VARCHAR(50) UNIQUE, " +
                            "Description VARCHAR(50) " +
                            ")");
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
    }

    @Override
    public void Migrate(){
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getProductDataWorker();
        Product[] products = new Product[0];
        try {
            products = worker.getProducts();
        } catch (DAOException e) {
            logger.error("DAO error" + e.getMessage());
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
        for (var product:products){
            try {
                statement.execute(String.format("INSERT Products(Name,Price,Description) VALUES ('%s',%s,'%s') ",
                        product.getName(),product.getPrice(),product.getDescription()));
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
    }
}
