public class Appointment {
    private final String doctorID;
    private final String patientID;
    private final Timeslot timeslot;
    private Appointment[] appointments = new Appointment[0];

    public Appointment(String doctorID, String patientID, String timeslot){
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.timeslot = Timeslot.strToTimeslot(timeslot);
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public String toString(){
        return doctorID + "\t" + "\t" + patientID + "\t" + timeslot;
    }
}
