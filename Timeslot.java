public class Timeslot {
    private int day;
    private int hour;

    public Timeslot(char day,int hour){
        this.day = dayToInt(day);
        this.hour = hour;
    }

    public char getDayChar(){
        switch(day){
            case 0:
                return 'M';
            case 1:
                return 'T';
            case 2:
                return 'W';
            case 3:
                return 'R';
            default:
                return 'F';
        }
    }

    public int getDay() {
        return day;
    }

    public int getHour(){
        return hour;
    }

    public static Timeslot strToTimeslot(String timeslot){
        int pos = 0;
        char weekday = timeslot.charAt(pos);
        pos++;
        int result = 0;
        for (int i = pos; i < 3; i++) {
            char c = timeslot.charAt(i);
            result = result * 10 + (c - '0');
        }
        return new Timeslot(weekday,result);
    }

    public String toString(){
        return "" + getDayChar() + (hour < 10 ? "0" + hour : "" + hour);
    }

    public static int dayToInt(char day){
        switch(day){
            case 'M':
                return 0;
            case 'T':
                return 1;
            case 'W':
                return 2;
            case 'R':
                return 3;
            default:
                return 4;
        }
    }

    public boolean equals(Timeslot timeslot){
        if (timeslot.equals(this)){
            return true;
        }
        return false;
    }
}
