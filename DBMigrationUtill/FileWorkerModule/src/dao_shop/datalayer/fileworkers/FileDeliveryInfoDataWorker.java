package dao_shop.datalayer.fileworkers;

import dao_shop.beans.DeliveryInfo;
import dao_shop.datalayer.DeliveryInfoDataWorker;
import dao_shop.datalayer.exceptions.DAOException;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileDeliveryInfoDataWorker implements DeliveryInfoDataWorker {
    private String dirpass;
    private int nextFreeId = -1;

    public FileDeliveryInfoDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }
    @Override
    public DeliveryInfo[] getAllInfo() throws DAOException {

        File[] files = new File(dirpass).listFiles();
        DeliveryInfo[] infos = new DeliveryInfo[files.length];
        FileReader reader;
        StringBuilder builder = new StringBuilder();
        int symb;
        for (int i = 0; i < files.length; i++) {
            symb = -1;
            try {
                reader = new FileReader(files[i]);
                symb = reader.read();
                while (symb != -1) {
                    builder.append((char) symb);
                    symb = reader.read();
                }
                infos[i] = new DeliveryInfo();
                infos[i].DeSerialize(builder.toString());
                builder.delete(0, builder.length());
                if (infos[i].getId() >= nextFreeId)
                    nextFreeId = infos[i].getId() + 1;

            } catch (IOException | InvalidSerializationStringException e) {
                throw new DAOException("Can't get all deliveryinfo");
            }

        }
        if (nextFreeId == -1){
            nextFreeId++;
            return null;
        }
        return infos;
    }

    @Override
    public DeliveryInfo getInfo(int id) throws DAOException {
        DeliveryInfo result = new DeliveryInfo();
        File file = new File(dirpass + "/" + id);
        int symb;
        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            symb = reader.read();
            while (symb != -1) {
                builder.append((char) symb);
                symb = reader.read();
            }
            result.DeSerialize(builder.toString());
            reader.close();
        } catch (IOException | InvalidSerializationStringException e) {
            throw new DAOException("cant get info for id:" + id);
        }
        return result;
    }

    @Override
    public void addInfo(DeliveryInfo info) throws DAOException {
        File file = new File(dirpass + "/" + info.getId());
        FileWriter writer;
        StringBuilder buff = new StringBuilder();
        int symb;
        try {
            writer = new FileWriter(file);
            writer.write(info.Serialize());
            writer.close();
        } catch (IOException e) {
            throw new DAOException("io exception");
        }

    }

    @Override
    public void removeInfo(int id) {
        File file = new File(dirpass + "/" + id);
        file.delete();
    }

    @Override
    public void modifyInfo(int id, DeliveryInfo newInfo) throws DAOException{
        removeInfo(id);
        newInfo.setId(id);
        addInfo(newInfo);
    }
    @Override
    public int nextFreeId() {
        if (nextFreeId != -1)
            return nextFreeId++;
        else{
            try {
                getAllInfo();
            } catch (DAOException e) {
                return 0;
            }
            return nextFreeId;
        }
    }
}
