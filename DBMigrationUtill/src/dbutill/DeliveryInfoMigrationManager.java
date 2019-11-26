package dbutill;

import dao_shop.beans.DeliveryInfo;
import dao_shop.datalayer.DeliveryInfoDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeliveryInfoMigrationManager implements MigrationManager {
    private final static Logger logger = Logger.getLogger(DeliveryInfoMigrationManager.class);
    private Connection connection;
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
    public void CreateTable(boolean reCreate) {
        if (connection == null)
            OpenConnection();
        if (reCreate) {
            try {
                connection.createStatement().execute("TRUNCATE TABLE DeliveryInfos");
            } catch (SQLException e) {
                logger.error("SqlExeption " + e.getMessage() + e.getSQLState());
            }
        }
        try {
            connection.createStatement().execute
                    ("CREATE TABLE DeliveryInfos (" +
                            "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "Adress VARCHAR(50), " +
                            "Phone VARCHAR(50), " +
                            "DateTime DATETIME" +
                            ")");
        } catch (SQLException e) {
            logger.error("SqlExeption " + e.getMessage() + e.getSQLState());
        }
        CloseConnection();
    }

    @Override
    public void Migrate() {
        if (connection == null)
            OpenConnection();
        DeliveryInfoDataWorker worker = FileDataWorkerFactory.getInstance().getDeliveryInfoDataWorker();
        DeliveryInfo[] infos = new DeliveryInfo[0];
        try {
            infos = worker.getAllInfo();
            logger.info("DAO Get deliveryinfo OK");
        } catch (DAOException e) {
            logger.error("DAO error" + e.getMessage());
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.error("Sql error" + e.getMessage() + e.getSQLState());
        }
        for (DeliveryInfo info:infos){
            try {
                statement.execute(String.format("INSERT DeliveryInfos(Adress,Phone) VALUES ('%s','%s')",
                        info.getAdress(),info.getPhoneNumber()));
                logger.info("INSERT id: " + info.getId() + "OK");
            } catch (SQLException e) {
                logger.error("Sql error" + e.getMessage() + e.getSQLState());
            }
        }
        CloseConnection();
    }
}
