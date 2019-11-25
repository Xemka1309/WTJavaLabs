package dbutill;

import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ShoppingCartMigrationManager implements MigrationManager {
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
            connection.createStatement().execute("DROP TABLE ShoppingCarts");
        connection.createStatement().execute
                ("CREATE TABLE ShoppingCarts (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "EndPrice INT " +
                        ")");
    }

    @Override
    public void Migrate() throws SQLException, DAOException {
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getShoppingCartDataWorker();
        var carts = worker.getCarts();
        var statement = connection.createStatement();
        for (var cart:carts){
            statement.execute(String.format("INSERT ShoppingCarts(EndPrice) VALUES (%s)",
                    cart.getEndPrice()));
        }
    }
}
