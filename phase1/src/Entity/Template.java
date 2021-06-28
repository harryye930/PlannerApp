package Entity;


/**
 * Represents a Template in the program. Can be a Daily Template, or a Project Template, and so on, which are
 * implemented as subclasses.
 */
public abstract class Template {
    protected String name; // name of the template
    private String lastEditTime; // last time the template was edited by a user in the program
    private String lastEditUser; // the ID of the user who last edited the template in the program

    public Template(String initEditTime, String initEditUser){
        this.name = "Template";
        this.lastEditTime = initEditTime;
        this.lastEditUser = initEditUser;
    }

    /**
     * Returns a string representation of the Template object.
     * @return String that represents the Template object.
     */
    public String toString(){
        String stringRep = "Name: " + this.getName() + System.lineSeparator();
        stringRep += "Last Edit Time: " + this.getLastEditTime() + System.lineSeparator();
        stringRep += "Last Edit User: " + this.getLastEditUser() + System.lineSeparator();
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

    /**
     * Getter for retrieving the time when the template was last edited.
     * @return String that represents the time.
     */
    public String getLastEditTime() {
        return lastEditTime;
    }

    /**
     * Getter for retrieving the user who last edited the template.
     * @return String that represents the user ID.
     */
    public String getLastEditUser() {
        return lastEditUser;
    }


}
