import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileWriter;

public class UserDB{
    //user array
    private User[] userArray = new User[0];

    //loadIn file
    public int loadIn(String filename)throws Exception{
        File userDbFile = new File(filename);
        Scanner userDbScanner = new Scanner(userDbFile);
        int userNum = 0;
        while(userDbScanner.hasNextLine()){
            //Create user object
            String userID = userDbScanner.next();
            String password = userDbScanner.next();
            String userType = userDbScanner.next();
            String name = userDbScanner.nextLine();
            if (add(userID, password, userType, name)) {
                userNum++;
            }
        }
        userDbScanner.close();
        return userNum;
    }

    //adding user
    public boolean add(String userID, String password, String userType, String name){
        if (!search(userID)) {
            User[] newUserArray = new User[userArray.length + 1];
            User newUser = new User(userID, password, userType, name);
            for (int i = 0; i < userArray.length; i++) {
                newUserArray[i] = userArray[i];
            }
            newUserArray[newUserArray.length - 1] = newUser;
            userArray = newUserArray;
            return true;
        }
        return false;
    }

    //adding user and return user
    public User add(User newUser){
        if (!search(newUser.getUserID())) {
            User[] newUserArray = new User[userArray.length + 1];
            for (int i = 0; i < userArray.length; i++) {
                newUserArray[i] = userArray[i];
            }
            newUserArray[newUserArray.length - 1] = newUser;
            userArray = newUserArray;
            return newUser;
        }
        return null;
    }

    //searching for user
    public boolean search(String userID){
        for (int i = 0; i < userArray.length; i++){
            if (userID.equals(userArray[i].getUserID())){
                return true;
            }
        }
        return false;
    }

    //searching for user and return user
    public User searchUser(String userID){
        for (int i = 0; i < userArray.length; i++){
            if (userID.equals(userArray[i].getUserID())){
                return userArray[i];
            }
        }
        return null;
    }

    public String toString(){
        String output = "";
        for (int i = 0; i < userArray.length; i++){
            output += userArray[i].toString() + "\n";
        }
        return output;
    }

    //saving array to file
    public int save(String filename)throws Exception{
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter out = new PrintWriter(fileWriter);
        out.print(toString());
        out.close();
        return userArray.length;
    }

    //verifying user
    public User verifyingUser(User user){
        for (int i = 0; i < userArray.length; i++){
            if (user.getUserID().equals(userArray[i].getUserID())){
                if (user.getPassword().equals(userArray[i].getPassword())){
                    return userArray[i];
                }
            }
        }
        return null;
    }

    //del user
    public User del(String userID) {
        int pos = 0;
        for (int i = 0; i < userArray.length; i++){
            if (userID.equals(userArray[i].getUserID())){
                pos = i;
            }
        }

        User user = null;
        User[] newUserArray = new User[userArray.length-1];
        for (int i = 0; i < userArray.length; i++){
            if (i < pos){
                newUserArray[i] = userArray[i];
            }else if (i == pos){
                user = userArray[i];
            }else if (i > pos){
                newUserArray[i-1] = userArray[i];
            }
        }
        userArray = newUserArray;
        return user;
    }

    //get array length
    public int getArrayLength(){
        int arrayLength = 0;
        int pos = 0;
        while (pos < userArray.length){
            arrayLength++;
            pos++;
        }
        return arrayLength;
    }
}
