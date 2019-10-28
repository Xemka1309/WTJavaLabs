package dao_shop.datalayer.myserialize;

public interface MySerializable {
    public String Serialize();
    public void DeSerialize(String str) throws InvalidSerializationStringException;
}
