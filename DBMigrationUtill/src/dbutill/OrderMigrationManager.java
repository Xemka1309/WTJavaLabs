package dbutill;

import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OrderMigrationManager implements MigrationManager {
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
            connection.createStatement().execute("DROP TABLE Orders");
        connection.createStatement().execute
                ("CREATE TABLE Orders (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "CartId INT, DeliveryId INT, UserId INT, "+
                        "FOREIGN KEY (DeliveryId)  REFERENCES DeliveryInfos(Id), " +
                        "FOREIGN KEY (CartId)  REFERENCES ShoppingCarts(Id), " +
                        "FOREIGN KEY (UserId)  REFERENCES Users(Id), " +
                        "EndPrice INT" +
                        ")");
    }

    @Override
    public void Migrate() throws SQLException, DAOException {
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getOrderDataWorker();
        var orders = worker.getOrders();
        var statement = connection.createStatement();
        for (var order:orders){
            statement.execute(String.format("INSERT Orders(CartId,DeliveryId,UserId,EndPrice) VALUES (%s,%s,%s,%s)",
                    order.getShoppingCart().getId(),order.getDeliveryInfo().getId(),
                    order.getUser().getId(),order.getEndPrice()));
        }
    }
}
