package dao_shop;
// TODO:implement equals,hashcode,tostring, getters, setters
public class User {
    private String login;
    private String password;
    private String email;
    private int userDiscount;

    public int getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(int userDiscount) {
        this.userDiscount = userDiscount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
