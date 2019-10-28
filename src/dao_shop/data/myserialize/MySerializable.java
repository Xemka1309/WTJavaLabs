package dao_shop.data.myserialize;

public interface MySerializable {
    public String Serialize();
    public void DeSerialize(String str) throws InvalidSerializationStringException;
}
