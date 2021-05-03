// Doctor Appointment Management System
// Mak Kwan Ting

import java.io.File;
import java.util.Scanner;

public class DocAppMS {
    private final UserDB userDB;
    private final AppointmentDB appointmentDB;
    private String[] arg;
    private User loginUser;

    public DocAppMS(){
        this.userDB =  new UserDB();
        this.appointmentDB = new AppointmentDB();
        this.arg = new String[5];
        this.loginUser = new User();
    }

    public static void main(String[] args) throws Exception {
        new DocAppMS().startUp(args);
    }

    void startUp(String[] args)throws Exception{
        //Initialize the args
        String userDb = args[0];
        String appointDb = args[1];

        //Access user database
        int userNum = userDB.loadIn(userDb);

        //Access appointment database
        int appointmentNum = appointmentDB.loadIn(appointDb);

        //Print Setup message
        System.out.println("*** System Startup: begin ***");
        System.out.println("Loading user db from " + userDb + "..." + userNum + " user records loaded.");
        System.out.println("Loading appointment db from " + appointDb + "..." + appointmentNum
                + " appointment records loaded.");
        System.out.println("*** System Startup: done! ***");
        login();
    }

    void login()throws Exception{
        //square
        System.out.println("\n\n" +
                           "+------------------------------------------+\n" +
                           "|                                          |\n" +
                           "|   Doctor Appointment Management System   |\n" +
                           "|                                          |\n" +
                           "+------------------------------------------+\n");

        boolean login = true;
        while (login) {
            System.out.println("Available commands: ");
            System.out.println("  Login User\n  Quit");
            System.out.println();
            System.out.print("ready> ");
            Scanner in = new Scanner(System.in);
            String command = in.next();
            switch (command) {
                case "login":
                    String userID = in.next();
                    in.nextLine();
                    System.out.print("password: ");
                    String password = in.nextLine();
                    User user = new User(userID, password);
                    if (userDB.verifyingUser(user) != null) {
                        System.out.println("Login success! Welcome to Doctor Appointment Management System!");
                        loginUser = userDB.verifyingUser(user);
                        checkUserType(loginUser);
                        login = false;
                        break;
                    } else {
                        System.out.println("Invalid UserID or Password.");
                        continue;
                    }
                case "quit":
                    System.out.println("\n GoodBye!");
                    login = false;
                    break;
                default:
                    System.out.println("Unknown Command: " + command);
            }
        }
    }
    //checking user type
    void checkUserType(User user)throws Exception{
        if (user.getUsertype().equals("a")){
            admin();
        }else if(user.getUsertype().equals("p")){
            patient();
        }else if(user.getUsertype().equals("d")){
            doctor();
        }
    }
    // admin command
    void admin()throws Exception{
        boolean ready = true;
        while(ready) {
            System.out.print("ready> ");
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            arg = strToStrArray(command);
            switch (arg[0]) {
                case "load":
                    load();
                    break;
                case "save":
                    save();
                    break;
                case "list":
                    list();
                    break;
                case "show":
                    show();
                    break;
                case "add":
                    add();
                    break;
                case "delete":
                    delete();
                    break;
                case "logout":
                    ready = false;
                    login();
                    break;
                case "help":
                    help();
                    break;
                default:
                    System.out.println("Command does not exist!");
            }
        }
    }
    // changing command into string array
    String[] strToStrArray(String line) {
        String token = "";
        String[] commandArray = new String[0];
        int index = 0;
        for (int pos = 0; pos < line.length(); pos++) {
            char c = line.charAt(pos);
            if (c != ' ') {
                token += c;
            } else if (c == ' '){
                String[] newCommandArray = new String[commandArray.length+1];

                for (int i = 0; i < commandArray.length; i++){
                    newCommandArray[i] = commandArray[i];
                }
                newCommandArray[index] = token;
                commandArray = newCommandArray;
                token = "";
                index++;
            }
        }

        if (!token.equals("")) {
            String[] newCommandArray = new String[commandArray.length+1];

            for (int i = 0; i < commandArray.length; i++){
                newCommandArray[i] = commandArray[i];
            }
            newCommandArray[index] = token;
            commandArray = newCommandArray;
        }
        return commandArray;
    }

    // patient command
    void patient() throws Exception{
        boolean ready = true;
        while(ready) {
            System.out.print("ready> ");
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            arg = strToStrArray(command);
            switch (arg[0]) {
                case "show":
                    show();
                    break;
                case "add":
                    addAppointment(arg[1], loginUser.getUserID(),arg[2]);
                    break;
                case"delete":
                    delAppointment(arg[1], loginUser.getUserID(), arg[2]);
                    break;
                case"help":
                    help();
                    break;
                case"logout":
                    ready = false;
                    login();
                    break;
                default:
                    System.out.println("Command does not exist!");
            }
        }
    }
    //doctor command
    void doctor()throws Exception{
        boolean ready = true;
        while(ready) {
            System.out.print("ready> ");
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            arg = strToStrArray(command);
            switch (arg[0]) {
                case "show":
                    show();
                    break;
                case "add":
                    addAppointment(loginUser.getUserID(), arg[1],arg[2]);
                    break;
                case"delete":
                    delAppointment(loginUser.getUserID(), arg[1], arg[2]);
                    break;
                case"help":
                    help();
                    break;
                case"logout":
                    ready = false;
                    login();
                    break;
                default:
                    System.out.println("Command does not exist!");
            }
        }
    }
    //admin: load command
    void load()throws Exception{
        switch (arg[1]){
            case "user":
                loadUser();
                break;
            case "appointment":
                loadAppointment();
                break;
        }
    }
    //admin: load user
    void loadUser()throws Exception{
        int UserNum = userDB.loadIn(arg[2]);
        System.out.println("Loading user db from " + arg[2]);
        System.out.println(UserNum + " user records loaded");
    }

    //admin: load appointment
    void loadAppointment()throws Exception{
        int AppointmentNum = appointmentDB.loadIn(arg[2]);
        System.out.println("Loading appointment db from " + arg[2]);
        System.out.println(AppointmentNum + " appointment records loaded");
    }

    //admin: save command
    void save() throws Exception{
        switch (arg[1]){
            case "user":
                saveUser();
                break;
            case "appointment":
                saveAppointment();
                break;
        }
    }

    //admin: saving user
    void saveUser()throws Exception{
        System.out.println(userDB.save(arg[2]) + " user records saved to " + arg[2]);
    }

    //admin: saving appointment
    void saveAppointment() throws Exception{
        System.out.println(appointmentDB.save(arg[2]) + " appointment records saved to " + arg[2]);
    }

    //admin: list command
    void list(){
        switch (arg[1]){
            case "user":
                listUser();
                break;
            case "appointment":
                listAppointment();
                break;
        }
    }

    //admin: list user
    void listUser(){
        System.out.println("Listing all Users:");
        System.out.println(userDB.toString());
        System.out.println(userDB.getArrayLength() + " users in found");
    }

    //admin: list appointment
    void listAppointment(){
        System.out.println("Listing all appointments:");
        System.out.println(appointmentDB.toString());
        System.out.println(appointmentDB.getArrayLength() + " appointments in found");
    }

    //admin: show appointment
    void show(){
        switch(arg[1]){
            case "timetable":
                showTimetable();
                break;
            case "reminder":
                showReminder();
                break;
        }
    }

    //admin: show timetable
    void showTimetable(){
        User user;
        if (loginUser.getUsertype().equals("a")) {
            user = userDB.searchUser(arg[2]);
        }else {
            user = userDB.searchUser(loginUser.getUserID());
        }

        System.out.println("Time Table for " + user.getName() + "(" + user.getUserID() + "):");
        System.out.printf("%11s%12s%12s%12s%12s \n", "Mon", "Tue", "Wed", "Thu", "Fri");
        Appointment[] userAppointment;
        if (loginUser.getUsertype().equals("a")) {
            userAppointment = appointmentDB.getUserAppointment(arg[2]);
        }else {
            userAppointment = appointmentDB.getUserAppointment(loginUser.getUserID());
        }
        for (int i = 9; i < 19; i++) {
            System.out.println("   +-----------+-----------+-----------+-----------+-----------+");
            System.out.println("   |           |           |           |           |           |");
            String[] temp = {"", "", "", "", ""};
            for (int j = 0; j < userAppointment.length; j++){
                Appointment apmt = userAppointment[j];
                Timeslot t = apmt.getTimeslot();
                if (t.getHour() == i){
                    if (user.getUsertype().equals("d")) {
                        temp[userAppointment[j].getTimeslot().getDay()] = userAppointment[j].getPatientID();
                    }else if (user.getUsertype().equals("p")){
                        temp[userAppointment[j].getTimeslot().getDay()] = userAppointment[j].getDoctorID();
                    }
                }
            }
            int width = 11;
            System.out.printf("%2s |%s|%s|%s|%s|%s|\n",
                    i,
                    centre(temp[0], width),
                    centre(temp[1], width),
                    centre(temp[2], width),
                    centre(temp[3], width),
                    centre(temp[4], width)
            );
            System.out.println("   |           |           |           |           |           |");
        }
        System.out.println("   +-----------+-----------+-----------+-----------+-----------+\n");
        System.out.println(user.getName() + "(" + user.getUserID() + ") has " + userAppointment.length +
                " appointments.");
    }

    //centring string
    String centre(String temp, int width){
        if (temp.length() >= width){
            return temp;
        }
        int newWidth = (width - temp.length()) / 2;
        String space = "";
        String output = "";
        for (int i = 0; i < newWidth; i++){
            space += " ";
        }
        output = space + temp + space;
        if ((width - temp.length())% 2 == 1){
            output += " ";
        }
        return output;
    }

    //show reminder
    void showReminder() {
        User user;
        if (loginUser.getUsertype().equals("a")) {
            user = userDB.searchUser(arg[2]);
        } else {
            user = userDB.searchUser(loginUser.getUserID());
        }
        System.out.println(user.getName() + "(" + user.getUserID()
                + ") is a " + user.fullUserType(user.getUsertype()).toLowerCase() + ", and has the following appointments");
        if (user.getUsertype().equals("d")) {
            Appointment[] appointments = appointmentDB.getUserAppointment(user.getUserID());
            for (int i = 0; i < appointments.length; i++) {
                User patient = userDB.searchUser(appointments[i].getPatientID());
                System.out.println(appointments[i].getTimeslot() + " -- Patient: " +
                        appointments[i].getPatientID() + patient.getName());
            }
            System.out.println(appointments.length + " appointments found.");
        } else if (user.getUsertype().equals("p")) {
            Appointment[] appointments = appointmentDB.getUserAppointment(user.getUserID());
            for (int i = 0; i < appointments.length; i++) {
                User doctor = userDB.searchUser(appointments[i].getPatientID());
                System.out.println(appointments[i].getTimeslot() + " -- Doctor: " +
                        appointments[i].getPatientID() + doctor.getName());
            }
            System.out.println(appointments.length + " appointment found.");
        } else {
            System.out.println("Admin has no reminder!");
        }
    }

    //add command
    void add(){
        switch(arg[1]) {
            case "admin":
                addUser(new User(arg[2], arg[3], "a", arg[4]));
                break;
            case "doctor":
                addUser(new User(arg[2], arg[3], "d", arg[4]));
                break;
            case "patient":
                addUser(new User(arg[2], arg[3], "p",arg[4]));
                break;
            case "Appointment":
                addAppointment(arg[2], arg[3],arg[4]);
                break;
        }
    }
    //add appointment
    void addAppointment(String doctorID, String patientID, String timeslot){
        Appointment appointment = new Appointment(doctorID, patientID, timeslot);
        System.out.print("Adding new appointment, (" + appointment.getDoctorID() +
                "," +  appointment.getPatientID() + "," + appointment.getTimeslot() + ":");
        if (appointmentDB.add(doctorID, patientID,timeslot)){
            System.out.println("done!");
        }else{
            System.out.println("failed!");
            System.out.println();
        }
    }

    //add user
    void addUser(User user){
        System.out.print("Adding new user, "+ user.fullUserType(user.getUsertype()) + " " +
                user.getName() + "(" + user.getUserID() + ") as " + user.fullUserType(user.getUsertype()) + " :");
        if (userDB.add(user) != null){
            System.out.println("done!");
        }else{
            System.out.println("failed!");
            System.out.println("Duplicated user Record: (" + user.getUserID() + ")");
        }
    }

    //del command
    void delete(){
        if (arg[1].equals("admin") || arg[1].equals("doctor") || arg[1].equals("patient")){
            delUser();
        }else if (arg[1].equals("appointment")){
            delAppointment(arg[2],arg[3],arg[4]);
        }
    }

    //del appointment
    void delAppointment(String doctorID, String patientID, String timeslot){
        if (appointmentDB.search(doctorID,patientID,timeslot)) {
            Appointment delAppointment = appointmentDB.del(doctorID, patientID, timeslot);
            System.out.print("Deleting appointment (" + delAppointment.getDoctorID() +
                    "," + delAppointment.getPatientID() + "," + delAppointment.getTimeslot() + "):");
            System.out.println("done!");
        }else{
            System.out.println("failed! Appointment does not exist!");
        }
    }

    //del user
    void delUser(){
        if (userDB.search(arg[2])){
            User delUser = userDB.del(arg[2]);
            System.out.print("Deleting " + delUser.fullUserType(delUser.getUsertype()) + "(" + delUser.getUserID()
                    + "): ");
            System.out.println("done!");
        }else{
            System.out.println("failed! User does not exist!");
        }
    }

    //help command
    void help(){
        System.out.println("User: " + loginUser.getUserID() + "  *** " +
                loginUser.fullUserType(loginUser.getUsertype()).toUpperCase() + " ***");
        System.out.println();
        if (loginUser.getUsertype().equals("a")) {
            if (arg.length < 2) {
                adminHelp();
            } else {
                adminHelpDetails();
            }
        }else if (loginUser.getUsertype().equals("p")){
            if (arg.length < 2){
                doctorAndPatientHelp();
            }else{
                patientHelp();
            }
        }else if (loginUser.getUsertype().equals("d")){
            if (arg.length < 2){
                doctorAndPatientHelp();
            }else {
                doctorHelp();
            }
        }
    }

    //admin: help command
    void adminHelp(){
        System.out.println("Available commands:");
        System.out.println("  load   [ user | appointment ]\n" +
                "  save   [ user | appointment ]\n" +
                "  list   [ user | appointment ]\n" +
                "  show   [ remainder | timetable ]\n" +
                "  add    [ user | appointment ]\n" +
                "  delete [ user | appointment ]\n" +
                "  help   [ command ]\n" +
                "  logout \n");
    }

    //doctor,patient: help command
    void doctorAndPatientHelp(){
        System.out.println("Available commands: ");
        System.out.println("  show   [ remainder | timetable ]\n" +
                           "  add    appointment\n" +
                           "  delete appointment\n" +
                           "  help   [ command ]\n" +
                           "  logout\n");
    }

    //admin: help command details
    void adminHelpDetails(){
        switch (arg[1]){
            case "load":
                System.out.println("\"load\" -- loads users or appointments from a file.\n" +
                        "Usage of \"load\":\n" +
                        "  load user fName\n" +
                        "  load appointment fName\n" +
                        "\n");
                break;
            case "save":
                System.out.println("\"save\" -- saves users or appointments to a file.\n" +
                        "Usage of \"save\":\n" +
                        "  save user fName\n" +
                        "  save appointment fName\n");
                break;
            case "list":
                System.out.println("\"list\" -- lists all users or appointments.\n" +
                        "Usage of \"list\":\n" +
                        "  list user\n" +
                        "  list appointment\n");
                break;
            case "show":
                System.out.println("\"show\" -- shows reminders or timetable of a user.\n" +
                        "Usage of \"show\":\n" +
                        "  show reminder userID\n" +
                        "  show timetable userID\n");
                break;
            case "add":
                System.out.println("\"add\" -- adds a new user (admin/doctor/patient) or appointment to the system.\n" +
                        "Usage of \"add\":\n" +
                        "  add admin userID passwd [ fullname ]\n" +
                        "  add doctor userID passwd [ fullname ]\n" +
                        "  add patient userID passwd [ fullname ]\n" +
                        "  add appointment doctorID patientID timeslot\n");
                break;
            case "delete":
                System.out.println("\"delete\" -- delete a user (admin/doctor/patient) or appointment from the system.\n" +
                        "Usage of \"delete\":\n" +
                        "  delete admin userID\n" +
                        "  delete doctor userID\n" +
                        "  delete patient userID\n" +
                        "  delete appointment doctorID patientID timeslot\n");
                break;
            case "help":
                System.out.println("\"help\" -- shows this help message.\n" +
                        "Usage of \"help\":\n" +
                        "  help\n" +
                        "  help command\n" +
                        "\n");
                break;
            case "logout":
                System.out.println("\"logout\" -- logout from Room Booking System.\n" +
                        "Usage of \"logout\":\n" +
                        "  logout\n" +
                        "\n");
                break;
            default:
                System.out.println("Unknown Command");
        }
    }

    //patient: help details
    void patientHelp(){
        switch (arg[1]){
            case "show":
                System.out.println("\"show\" -- shows reminders or timetable.\n" +
                        "Usage of \"show\":\n" +
                        "  show reminder\n" +
                        "  show timetable\n");
                break;
            case "add":
                System.out.println("\"add\" -- adds a new appointment to the system.\n" +
                        "Usage of \"add\":\n" +
                        "  add doctorID timeslot\n");
                break;
            case "delete":
                System.out.println("\"delete\" -- delete an appointment from the system.\n" +
                        "Usage of \"delete\":\n" +
                        "  delete doctorID timeslot\n");
                break;
            case "help":
                System.out.println("\"help\" -- shows this help message.\n" +
                        "Usage of \"help\":\n" +
                        "  help\n" +
                        "  help command\n" +
                        "\n");
                break;
            case "logout":
                System.out.println("\"logout\" -- logout from Room Booking System.\n" +
                        "Usage of \"logout\":\n" +
                        "  logout\n" +
                        "\n");
                break;
            default:
                System.out.println("Unknown Command");
        }
    }

    //doctor: help command details
    void doctorHelp(){
        switch (arg[1]){
            case "show":
                System.out.println("\"show\" -- shows reminders or timetable.\n" +
                        "Usage of \"show\":\n" +
                        "  show reminder\n" +
                        "  show timetable\n");
                break;
            case "add":
                System.out.println("\"add\" -- adds a new appointment to the system.\n" +
                        "Usage of \"add\":\n" +
                        "  add patientID timeslot\n");
                break;
            case "delete":
                System.out.println("\"delete\" -- delete an appointment from the system.\n" +
                        "Usage of \"delete\":\n" +
                        "  delete patientID timeslot\n");
                break;
            case "help":
                System.out.println("\"help\" -- shows this help message.\n" +
                        "Usage of \"help\":\n" +
                        "  help\n" +
                        "  help command\n" +
                        "\n");
                break;
            case "logout":
                System.out.println("\"logout\" -- logout from Room Booking System.\n" +
                        "Usage of \"logout\":\n" +
                        "  logout\n" +
                        "\n");
                break;
            default:
                System.out.println("Unknown Command");
        }
    }
}
