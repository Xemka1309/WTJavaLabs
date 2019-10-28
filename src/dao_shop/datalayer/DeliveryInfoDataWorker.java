package dao_shop.datalayer;

import dao_shop.beans.DeliveryInfo;
import dao_shop.datalayer.exceptions.DAOException;

public interface DeliveryInfoDataWorker {
    public DeliveryInfo[] getAllInfo() throws DAOException;
    public DeliveryInfo getInfo(int id) throws DAOException;
    public void addInfo(DeliveryInfo order) throws DAOException;
    public void removeInfo(int id);
    public void modifyInfo(int id, DeliveryInfo newInfo) throws DAOException;
}
