package dbutill;

import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OrderItemMigrationManager implements MigrationManager {
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
            connection.createStatement().execute("DROP TABLE OrderItems");
        connection.createStatement().execute
                ("CREATE TABLE OrderItems (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "ProductId INT, CartId INT, "+
                        "FOREIGN KEY (ProductId)  REFERENCES Products(Id), " +
                        "FOREIGN KEY (CartId)  REFERENCES ShoppingCarts(Id), " +
                        "EndPrice INT, Count INT" +
                        ")");

    }

    @Override
    public void Migrate() throws SQLException, DAOException {
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getOrderItemDataWorker();
        var items = worker.getItems();
        var statement = connection.createStatement();
        for (var item:items){
            statement.execute(String.format("INSERT OrderItems(CartId,ProductId,EndPrice,Count) VALUES (%s,%s,%s,%s)",
                    item.getCartId(),item.getProductId(),item.getEndPrice(),item.getCount()));
        }

    }
}
