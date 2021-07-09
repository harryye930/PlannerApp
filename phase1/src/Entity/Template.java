package Entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a Template in the program. Can be a Daily Template, or a Project Template, and so on, which are
 * implemented as subclasses.
 */
public abstract class Template {
//    protected String name; // name of the template
//    private String lastEditTime; // last time the template was edited by a user in the program
//    private String lastEditUser; // the ID of the user who last edited the template in the program

//    public Template(String initEditTime, String initEditUser){
//        this.name = "Template";
//        this.lastEditTime = initEditTime;
//        this.lastEditUser = initEditUser;
//    }

    private String name; // name of this template
    private ArrayList<String> prompts; // template prompts of this template

    private static int id; // id of this template

    public Template(String name, String[] prompts) {
        this.name = name;

        this.prompts = new ArrayList<>();
        this.prompts.addAll(Arrays.asList(prompts));

        id++;
    }

    /**
     * Returns a string representation of the Template object.
     * @return String that represents the Template object.
     */
//    public String toString(){
//        String stringRep = "Name: " + this.getName() + System.lineSeparator();
//        stringRep += "Last Edit Time: " + this.getLastEditTime() + System.lineSeparator();
//        stringRep += "Last Edit User: " + this.getLastEditUser() + System.lineSeparator();
//        return stringRep;
//    }

    public String toString() {
        String stringRep = "Name: " + this.getName() + System.lineSeparator();
        stringRep += "Type: " + this.isType() + System.lineSeparator();
        stringRep += "Number of Prompts: " + this.numPrompts() + System.lineSeparator();
        return stringRep;
    }

    /**
     * Setter for changing the name of the template.
     * @param newName The new name for template.
     */
    public void setName(String newName){
        name = newName;
    }

    /**
     * Getter for retrieving the name of the template.
     * @return String that's the name of the template.
     */
    public String getName(){
        return name;
    }

//    /**
//     * Getter for retrieving the time when the template was last edited.
//     * @return String that represents the time.
//     */
//    public String getLastEditTime() {
//        return lastEditTime;
//    }

//    /**
//     * Getter for retrieving the user who last edited the template.
//     * @return String that represents the user ID.
//     */
//    public String getLastEditUser() {
//        return lastEditUser;
//    }

    /**
     * Setter for changing the prompts of the template.
     * @param newPrompts the new prompts of the template.
     */
    public void setPrompts(String[] newPrompts) {
        this.prompts = new ArrayList<>();
        this.prompts.addAll(Arrays.asList(newPrompts));
    }

    /**
     * Getter for retrieving the prompts of the template.
     * @return String[] that contains prompts of the template.
     */
    public String[] getPrompts() {
        String[] temp = new String[this.numPrompts()];
        for (int i = 0; i < this.numPrompts(); i++) {
            temp[i] = prompts.get(i);
        }
        return temp;
    }

    /**
     * Getter for retrieving the id of this template.
     * @return int that represent the id of this template.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the number of prompts of the template.
     * @return int that represents the number of prompts.
     */
    public int numPrompts() {
        return prompts.size();
    }

    /**
     * Returns the type of the template.
     * @return String that's the type of the template.
     */
    public abstract String isType();
}
