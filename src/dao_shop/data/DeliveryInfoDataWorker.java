package dao_shop.data;

import dao_shop.beans.DeliveryInfo;

public interface DeliveryInfoDataWorker {
    public DeliveryInfo[] getAllInfo();
    public DeliveryInfo getInfo(int id);
    public void addInfo(DeliveryInfo order);
    public void removeInfo(int id);
    public void modifyInfo(int id, DeliveryInfo newInfo);
}
