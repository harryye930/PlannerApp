package Entity;

/**
 * Represents one type of Template in the program - a Daily Template, which allows users to create a Planner where
 * users can fill in tasks planned for hours in a particular day.
 */
public class DailyTemplate extends Template{
    private String startTime; //using 24-hour clock, e.g., "9:00"
    private String endTime; // using 24-hour clock, e.g., "21:00"
    private String increment; // can be 1 hour, or 30 minutes

    public DailyTemplate(String initEditTime, String initEditUser,
                         String startTime, String endTime, String increment){
        super(initEditTime, initEditUser);
        this.startTime = startTime;
        this.endTime = endTime;
        this.increment = increment;
        this.name = "Daily Template";
    }

    @Override
    public String toString(){
        String stringRep = super.toString();
        stringRep += "Start Time: " + this.startTime + System.lineSeparator();
        stringRep += "End Time: " + this.endTime + System.lineSeparator();
        stringRep += "Increment: " + this.increment + System.lineSeparator();
        return stringRep;
    }

}
