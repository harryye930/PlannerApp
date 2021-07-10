package Entity;


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

    public ProjectTemplate(String name, String[] prompts) {
        super(name, prompts);
    }

    @Override
    public String isType() {
        return "Project";
    }

    // TODO: override getPrompts
}
