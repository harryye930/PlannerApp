package Entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents one type of Template in the program - a Daily Template, which allows users to create a Planner where
 * users can fill in tasks planned for hours in a particular day.
 */
public class DailyTemplate extends Template{
    /*
     * Indexes of of specific setting required for all DailyTemplates (i.e., start time, end time, increment), and
     * an index indicating where the set of prompts asked for every events starts.
     */
    private final int START_TIME = 0;
    private final int END_TIME = 1;
    private final int INCREMENT = 2;

    public DailyTemplate(String name, ArrayList<String> prompts) {
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
        return this.getPrompts().get(START_TIME);
    }

    /**
     * @return the setting prompt for end time
     */
    public String endTimePrompt() {
        return this.getPrompts().get(END_TIME);
    }

    /**
     * @return the setting prompt for time increment
     */
    public String incrementPrompt() {
        return this.getPrompts().get(INCREMENT);
    }

}
