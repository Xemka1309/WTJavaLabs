package dbutill;

import dao_shop.datalayer.exceptions.DAOException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public interface MigrationManager {
    public void OpenConnection() throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
    public void CloseConnection() throws SQLException;
    public void CreateTable(boolean reCreate) throws SQLException;
    public void Migrate() throws SQLException, DAOException;
}
