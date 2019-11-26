package dbutill;

import dao_shop.beans.Order;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderMigrationManager implements MigrationManager {
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
        if (reCreate) {
            try {
                connection.createStatement().execute("DROP TABLE Orders");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.createStatement().execute
                    ("CREATE TABLE Orders (" +
                            "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "CartId INT, DeliveryId INT, UserId INT, "+
                            "FOREIGN KEY (DeliveryId)  REFERENCES DeliveryInfos(Id), " +
                            "FOREIGN KEY (CartId)  REFERENCES ShoppingCarts(Id), " +
                            "FOREIGN KEY (UserId)  REFERENCES Users(Id), " +
                            "EndPrice INT" +
                            ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Migrate(){
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getOrderDataWorker();
        Order[] orders = new Order[0];
        try {
            orders = worker.getOrders();
        } catch (DAOException e) {
            logger.error("DAO error when getting orders");
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
        for (var order:orders){
            try {
                statement.execute(String.format("INSERT Orders(CartId,DeliveryId,UserId,EndPrice) VALUES (%s,%s,%s,%s)",
                        order.getShoppingCart().getId(),order.getDeliveryInfo().getId(),
                        order.getUser().getId(),order.getEndPrice()));
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
    }
}
