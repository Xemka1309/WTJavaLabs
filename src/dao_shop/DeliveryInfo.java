package dao_shop;
// TODO:implement equals,hashcode,tostring, getters, setters
import java.util.Date;

public class DeliveryInfo {
    private String adress;
    private String phoneNumber;
    private Date date;

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
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
}
