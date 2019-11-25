package dbutill;

import dao_shop.datalayer.exceptions.DAOException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Main {
    public static void main(String[] args){
        DB db = new DB();
        try {
            db.ConnectToDB();
            db.MigrateProducts();
        }  catch (DAOException | SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
