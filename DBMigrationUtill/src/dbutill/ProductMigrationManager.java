package dbutill;

import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProductMigrationManager implements MigrationManager {
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
            connection.createStatement().execute("DROP TABLE Products");
        connection.createStatement().execute
                ("CREATE TABLE Products (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Price INT, " +
                        "Name VARCHAR(50) UNIQUE, " +
                        "Description VARCHAR(50) " +
                        ")");
    }

    @Override
    public void Migrate() throws SQLException, DAOException {
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
