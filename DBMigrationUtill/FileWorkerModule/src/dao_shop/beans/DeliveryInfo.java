package dao_shop.beans;
import dao_shop.datalayer.myserialize.InvalidSerializationStringException;
import dao_shop.datalayer.myserialize.MySerializable;

public class DeliveryInfo implements MySerializable {
    private int id;
    private String adress;
    private String phoneNumber;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }



    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public String Serialize() {
        StringBuilder result = new StringBuilder();
        result.append("DeliveryInfo\n");
        result.append("<");
        result.append(id);
        result.append(">");
        result.append("<");
        result.append(adress);
        result.append(">");
        result.append("<");
        result.append(phoneNumber);
        result.append(">");
        result.append("<");
        result.append(date);
        result.append(">");
        return result.toString();
    }

    @Override
    // return shopping cart with setted id only
    public void DeSerialize(String str) throws InvalidSerializationStringException {
        String[] fields = new String[6];
        int fieldInd = 0;
        Object result = new User();
        StringBuilder builder = new StringBuilder(str);
        if (builder.length() > 0){
            StringBuilder buff = new StringBuilder();
            int ind = 0;
            while (builder.charAt(ind) != '\n'){
                buff.append(builder.charAt(ind));
                ind++;
            }
            ind++;
            if (!buff.toString().equals("DeliveryInfo"))
                throw new InvalidSerializationStringException("Type must be DeliveryInfo");
            while ((ind< builder.length()) && (fieldInd-1 < 5)){
                ind++;
                buff.delete(0,buff.length());
                while (builder.charAt(ind) != '>'){
                    buff.append(builder.charAt(ind));
                    ind++;
                }
                fields[fieldInd] = buff.toString();
                fieldInd++;
                ind++;
            }

        }
        try {
            id = Integer.parseInt(fields[0]);
        }
        catch (NumberFormatException ex){
            throw  new InvalidSerializationStringException("wrong int value for id");
        }
        adress = fields[1];
        phoneNumber = fields[2];
        date = fields[3];
    }
}
