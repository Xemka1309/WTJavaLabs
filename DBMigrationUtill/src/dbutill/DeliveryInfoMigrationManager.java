package dbutill;

import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.fileworkers.FileDataWorkerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DeliveryInfoMigrationManager implements MigrationManager {
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
            connection.createStatement().execute("DROP TABLE DeliveryInfos");
        connection.createStatement().execute
                ("CREATE TABLE DeliveryInfos (" +
                        "Id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "Adress VARCHAR(50), " +
                        "Phone VARCHAR(50), " +
                        "DateTime DATETIME" +
                        ")");
    }

    @Override
    public void Migrate() throws SQLException, DAOException {
        if (connection == null)
            return;
        var worker = FileDataWorkerFactory.getInstance().getDeliveryInfoDataWorker();
        var infos = worker.getAllInfo();
        var statement = connection.createStatement();
        for (var info:infos){
            statement.execute(String.format("INSERT DeliveryInfos(Adress,Phone) VALUES ('%s','%s')",
                    info.getAdress(),info.getPhoneNumber()));
        }

    }
}
