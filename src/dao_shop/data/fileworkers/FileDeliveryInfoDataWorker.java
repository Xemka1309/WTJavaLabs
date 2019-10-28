package dao_shop.data.fileworkers;

import dao_shop.beans.DeliveryInfo;
import dao_shop.data.DeliveryInfoDataWorker;
import dao_shop.data.myserialize.InvalidSerializationStringException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileDeliveryInfoDataWorker implements DeliveryInfoDataWorker {
    private String dirpass;
    private int nextFreeId;

    public FileDeliveryInfoDataWorker(String dirpass) {
        this.dirpass = dirpass;
    }
    @Override
    public DeliveryInfo[] getAllInfo() {
        nextFreeId = 0;
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
                if (infos[i].getId() > nextFreeId)
                    nextFreeId = infos[i].getId();

            } catch (IOException | InvalidSerializationStringException e) {
                e.printStackTrace();
            }

        }
        nextFreeId++;
        return infos;
    }

    @Override
    public DeliveryInfo getInfo(int id) {
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
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addInfo(DeliveryInfo order) {

    }

    @Override
    public void removeInfo(int id) {

    }

    @Override
    public void modifyInfo(int id, DeliveryInfo newInfo) {

    }
}
