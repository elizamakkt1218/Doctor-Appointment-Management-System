import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class AppointmentDB {
    //appointment array
    private Appointment[] appointmentArray = new Appointment[0];

    //loadIn file
    public int loadIn(String filename) throws Exception{
        File appointDbFile = new File(filename);
        Scanner appointDbScanner = new Scanner(appointDbFile);
        int appointmentNum = 0;
        while(appointDbScanner.hasNextLine()){
            //Create appointment object
            String doctorID = appointDbScanner.next();
            String patientID = appointDbScanner.next();
            String timeslot = appointDbScanner.next();
            appointDbScanner.nextLine();
            if (add(doctorID, patientID, timeslot)) {
                appointmentNum++;
            }
        }
        appointDbScanner.close();
        return appointmentNum;
    }

    //adding new appointment
    public boolean add(String doctorID, String patientID, String timeslot){
        if (!search(doctorID, patientID, timeslot)) {
            Appointment[] newAppointmentArray = new Appointment[appointmentArray.length + 1];
            Appointment newAppointment = new Appointment(doctorID, patientID, timeslot);
            for (int i = 0; i < appointmentArray.length; i++) {
                newAppointmentArray[i] = appointmentArray[i];
            }
            newAppointmentArray[newAppointmentArray.length - 1] = newAppointment;
            appointmentArray = newAppointmentArray;
            return true;
        }
        return false;
    }

    //searching for appointment
    //return boolean
    public boolean search(String doctorID, String patientID, String timeslot){
        for (int i = 0; i < appointmentArray.length; i++){
            if (doctorID.equals(appointmentArray[i].getDoctorID())){
                if (patientID.equals(appointmentArray[i].getPatientID())){
                    if (timeslot.equals(appointmentArray[i].getTimeslot())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //search for appointment
    //return appointment
    public Appointment search(String doctorID){
        for (int i = 0; i < appointmentArray.length; i++){
            if (doctorID.equals(appointmentArray[i].getDoctorID())){
                return appointmentArray[i];
            }
        }
        return null;
    }

    //toString method
    public String toString(){
        String output = "";
        for (int i = 0; i < appointmentArray.length; i++){
            output += appointmentArray[i].toString() + "\n";
        }
        return output;
    }

    //saving array to file
    public int save(String filename)throws Exception{
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter out = new PrintWriter(fileWriter);
        out.print(toString());
        out.close();
        return appointmentArray.length;
    }

    //del appointment
    public Appointment del(String doctorID,String patientID, String timeslot) {
        int pos = 0;
        Appointment appointment = new Appointment(doctorID, patientID,timeslot);
        for (int i = 0; i < appointmentArray.length; i++){
            if (appointment.getDoctorID().equals(appointmentArray[i].getDoctorID())) {
                if (appointment.getPatientID().equals(appointmentArray[i].getPatientID())) {
                    if (appointment.getTimeslot().equals(appointmentArray[i].getTimeslot())) {
                        pos = i;
                    }
                }
            }
        }

        Appointment[] newAppointmentArray = new Appointment[appointmentArray.length-1];
        for (int i = 0; i < appointmentArray.length; i++){
            if (i < pos){
                newAppointmentArray[i] = appointmentArray[i];
            }else if (i == pos){
                appointment = appointmentArray[i];
            }else if (i > pos){
                newAppointmentArray[i-1] = appointmentArray[i];
            }
        }
        appointmentArray = newAppointmentArray;
        return appointment;
    }

    //get specific user's appointment
    public Appointment[] getUserAppointment(String userID){
        Appointment[] appointments = new Appointment[0];
        for (int i = 0; i < appointmentArray.length; i++){
            if (userID.equals(appointmentArray[i].getPatientID()) ||
                    userID.equals(appointmentArray[i].getDoctorID())){
                Appointment[] newAppointmentArray = new Appointment[appointments.length + 1];
                for (int j = 0; j < appointments.length; j++){
                    newAppointmentArray[j] = appointments[j];
                }
                newAppointmentArray[appointments.length] = appointmentArray[i];
                appointments = newAppointmentArray;
            }
        }
        return appointments;
    }

    //get Array length
    public int getArrayLength(){
        int arrayLength = 0;
        int pos = 0;
        while (pos < appointmentArray.length){
            arrayLength++;
            pos++;
        }
        return arrayLength;
    }
}
