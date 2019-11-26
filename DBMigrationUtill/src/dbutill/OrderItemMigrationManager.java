package dbutill;

import dao_shop.beans.OrderItem;
import dao_shop.datalayer.OrderItemDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderItemMigrationManager implements MigrationManager {
    private Connection connection;
    private final Logger logger = Logger.getLogger(OrderMigrationManager.class);
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
        if (connection == null)
            OpenConnection();
        if (reCreate) {
            try {
                connection.createStatement().execute("TRUNCATE TABLE OrderItems");
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        try {
            connection.createStatement().execute
                    ("CREATE TABLE OrderItems (" +
                            "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "ProductId INT, CartId INT, "+
                            "FOREIGN KEY (ProductId)  REFERENCES Products(Id), " +
                            "FOREIGN KEY (CartId)  REFERENCES ShoppingCarts(Id), " +
                            "EndPrice INT, Count INT" +
                            ")");
            logger.info("create table OrderItems OK");
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
        CloseConnection();
    }

    @Override
    public void Migrate(){
        if (connection == null)
            OpenConnection();
        OrderItemDataWorker worker = FileDataWorkerFactory.getInstance().getOrderItemDataWorker();
        OrderItem[] items = new OrderItem[0];
        try {
            items = worker.getItems();
        } catch (DAOException e) {
            logger.error("DAO error when try get OrderItems");
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
        for (OrderItem item:items){
            try {
                statement.execute(String.format("INSERT OrderItems(CartId,ProductId,EndPrice,Count) VALUES (%s,%s,%s,%s)",
                        item.getCartId(),item.getProductId(),item.getEndPrice(),item.getCount()));
                logger.info("INSERT OK with id" + item.getId());
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        CloseConnection();

    }
}
