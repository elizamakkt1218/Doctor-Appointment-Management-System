import java.util.Scanner;

public class User {
    private final String userID;
    private final String password;
    private final String usertype;
    private final String name;

    public User(String userID, String password, String usertype, String name){
        this.userID = userID;
        this.password = password;
        this.usertype = usertype;
        this.name = name;
    }

    public User(){
        this("", "", "", "");
    }

    public User(String userID, String password){
        this.userID = userID;
        this.password = password;
        this.usertype = "";
        this.name = "";
    }

    public String getUserID(){
        return userID;
    }

    public String getPassword(){
        return password;
    }

    public String getUsertype(){
        return usertype;
    }

    public String getName(){
        return name;
    }

    public String fullUserType(String usertype){
        if (usertype.equals("a")){
            return "Admin";
        }else if (usertype.equals("p")){
            return "Patient";
        }
        return "Doctor";
    }

    public String toString(){
        return userID + "    " + password + "    " + fullUserType(usertype) + "    " + name;
    }
}
