package Entity;

import java.util.Arrays;

/**
 * Represents one type of Template in the program - a Daily Template, which allows users to create a Planner where
 * users can fill in tasks planned for hours in a particular day.
 */
public class DailyTemplate extends Template{
//    private String startTime; //using 24-hour clock, e.g., "9:00"
//    private String endTime; // using 24-hour clock, e.g., "21:00"
//    private String increment; // can be 1 hour, or 30 minutes
//
//    public DailyTemplate(String initEditTime, String initEditUser,
//                         String startTime, String endTime, String increment){
//        super(initEditTime, initEditUser);
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.increment = increment;
//        this.name = "Daily Template";
//    }
//
//    @Override
//    public String toString(){
//        String stringRep = super.toString();
//        stringRep += "Start Time: " + this.startTime + System.lineSeparator();
//        stringRep += "End Time: " + this.endTime + System.lineSeparator();
//        stringRep += "Increment: " + this.increment + System.lineSeparator();
//        return stringRep;
//    }

    /**
     * Indexes of of specific setting required for all DailyTemplates (i.e., start time, end time, increment), and
     * an index indicating where the set of prompts asked for every events starts.
     */
    private final int START_TIME = 0;
    private final int END_TIME = 1;
    private final int INCREMENT = 2;

    private final int PROMPTS_START = 3;

    public DailyTemplate(String name, String[] prompts) {
        super(name, prompts);
    }

    @Override
    public String isType() {
        return "Daily";
    }

    /**
     * @return the setting prompt for start time
     */
    public String startTimePrompt() {
        return this.getPrompts()[START_TIME];
    }

    /**
     * @return the setting prompt for end time
     */
    public String endTimePrompt() {
        return this.getPrompts()[END_TIME];
    }

    /**
     * @return the setting prompt for time increment
     */
    public String incrementPrompt() {
        return this.getPrompts()[INCREMENT];
    }

    /**
     * @return the prompts for event detail (i.e., set of prompts asked for each event)
     */
    public String[] eventPrompts() {
        String[] holder = this.getPrompts();
        return Arrays.copyOfRange(holder, PROMPTS_START, holder.length);
    }
}
