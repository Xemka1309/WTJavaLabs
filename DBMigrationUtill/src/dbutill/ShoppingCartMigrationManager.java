package dbutill;

import dao_shop.beans.ShoppingCart;
import dao_shop.datalayer.ShoppingCartDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ShoppingCartMigrationManager implements MigrationManager {
    private Connection connection;
    private static Logger logger = Logger.getLogger(ShoppingCartMigrationManager.class);
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
        if (connection == null)
            OpenConnection();
        if (reCreate) {
            try {
                connection.createStatement().execute("TRUNCATE TABLE ShoppingCarts");
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        try {
            connection.createStatement().execute
                    ("CREATE TABLE ShoppingCarts (" +
                            "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "EndPrice INT " +
                            ")");
        } catch (SQLException e) {
            logger.error("SQl error" + e.getMessage() + e.getSQLState());
        }
        CloseConnection();
    }

    @Override
    public void Migrate(){
        if (connection == null)
            OpenConnection();
        ShoppingCartDataWorker worker = FileDataWorkerFactory.getInstance().getShoppingCartDataWorker();
        ShoppingCart[] carts = new ShoppingCart[0];
        try {
            carts = worker.getCarts();
        } catch (DAOException e) {
            logger.error("DAO error" + e.getMessage());
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (ShoppingCart cart:carts){
            try {
                statement.execute(String.format("INSERT ShoppingCarts(EndPrice) VALUES (%s)",
                        cart.getEndPrice()));
            } catch (SQLException e) {
                logger.error("SQl error" + e.getMessage() + e.getSQLState());
            }
        }
        CloseConnection();
    }
}
