/*package dao_shop.data.myserialize;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

// Work for classes with int or string fields only
public class MySerialize {

    public static String Serialize(@NotNull Object obj) {
        var ClassName = obj.getClass();
        var fields = ClassName.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append("classname:<");
        System.out.println(ClassName.getName());
        stringBuilder.append(ClassName.getName());
        stringBuilder.append(">");
        stringBuilder.append("fields:<");
        for (var field:fields) {
            field.setAccessible(true);
            stringBuilder.append("fieldname:<");
            stringBuilder.append(field.getName());
            System.out.println(field.getName());
            stringBuilder.append(">value:<");
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            stringBuilder.append(value.toString());
            stringBuilder.append(">");
        }
        stringBuilder.append(">>");
        return stringBuilder.toString();
    }
    public static Object DeSerialize(String str) throws ClassNotFoundException, InvalidSerializationStringException {
        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList<Object> fieldValues = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder(str);
        StringBuilder className = new StringBuilder();
        int ind = 0;
        while (builder.length() > 0 ){
            if (builder.charAt(ind) != '<')
                throw new InvalidSerializationStringException("Expected '<' ");
            ind++;
            String buff = builder.substring(ind,ind + "classname".length());
            if (!buff.equals("classname"))
                throw new InvalidSerializationStringException("Invalid string");
            ind += "classname".length();
            if (builder.charAt(ind) != '<')
                throw new InvalidSerializationStringException("Expected '<' ");
            ind++;
            while (builder.charAt(ind) != '>'){
                className.append(builder.charAt(ind));
                ind++;
            }
            var classVar = Class.forName(className.toString());
            if (builder.substring(ind, ind + "fields:".length()) != "fields:")
                throw new InvalidSerializationStringException("Invalid string");
            ind += "fields:".length();
            if (builder.charAt(ind) != '<')
                throw new InvalidSerializationStringException("Expected '<' ");
            ind++;
            while (builder.charAt(ind) != '>' && builder.charAt(ind+1) != '>'){
                if (builder.substring(ind, ind + "fieldname:<".length()) != "fieldname:<")
                    throw new InvalidSerializationStringException("Invalid string");
                ind += "fieldname:<".length();
                StringBuilder buffBuilder = new StringBuilder();
                while (builder.charAt(ind) != '>'){
                    buffBuilder.append(builder.charAt(ind));
                    ind++;
                }
                ind++;
                fieldNames.add(buffBuilder.toString());
                buffBuilder.delete(0,buffBuilder.length());
                if (builder.substring(ind, ind + "value:<".length()) != "value:<")
                    throw new InvalidSerializationStringException("Invalid string");
                ind += "value:<".length();
                while (builder.charAt(ind) != '>'){
                    buffBuilder.append(builder.charAt(ind));
                    ind++;
                }
                int intValue;
                String stringValue;
                try{
                    intValue = Integer.parseInt(buffBuilder.toString());
                    fieldValues.add(intValue);
                }
                catch (NumberFormatException ex){
                    stringValue = buffBuilder.toString();
                    fieldValues.add(stringValue);
                }
                ind++;
            }

            //classVar.newInstance()

        }


    }
}
*/