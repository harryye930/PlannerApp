package Entity;


import java.util.Arrays;
import java.util.List;

/**
 * Represents one type of Template in the program - a Task Template, which allows users to create a Planner where
 * users can fill in tasks for a particular project based on the status of the tasks.
 */
public class ProjectTemplate extends Template{
//    private List<String> statusOptions; // status options user wants to set for the project, e.g., "to do", "doing"
//
//    public ProjectTemplate(String initEditTime, String initEditUser,
//                         List<String> statusOptions){
//        super(initEditTime, initEditUser);
//        this.statusOptions = statusOptions;
//        this.name = "Project Template";
//    }

//    @Override
//    public String toString(){
//        String stringRep = super.toString();
//        stringRep += "Status Options specified by the user: " + this.statusOptions + System.lineSeparator();
//        return stringRep;
//    }

    /**
     * Indexes of of specific setting required for all ProjectTemplates (i.e., 3 status headings should be specified),
     * and an index indicating where the set of prompts asked for every events starts.
     */
    private final int STATUS_START = 0; // e.g., to do
    private final int STATUS_MIDDLE = 1; // e.g., doing
    private final int STATUS_END = 2; // e.g., done

    private final int PROMPTS_START = 3;

    public ProjectTemplate(String name, String[] prompts) {
        super(name, prompts);
    }

    @Override
    public String isType() {
        return "Project";
    }

    /**
     * @return the setting prompt for the first heading (i.e., start)
     */
    public String firstStatusPrompt() {
        return this.getPrompts()[STATUS_START];
    }

    /**
     * @return the setting prompt for the second heading (i.e., middle)
     */
    public String secondStatusPrompt() {
        return this.getPrompts()[STATUS_MIDDLE];
    }

    /**
     * @return the setting prompt for the third heading (i.e., end)
     */
    public String thirdStatusPrompt() {
        return this.getPrompts()[STATUS_END];
    }

    /**
     * @return the prompts for event detail (i.e., set of prompts asked for each event)
     */
    public String[] eventPrompts() {
        String[] holder = this.getPrompts();
        return Arrays.copyOfRange(holder, PROMPTS_START, holder.length);
    }
}
