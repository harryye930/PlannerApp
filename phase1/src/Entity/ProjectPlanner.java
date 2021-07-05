package Entity;
import java.util.ArrayList;

public class ProjectPlanner extends Planner {
    public ArrayList<String> tasks;
    public ArrayList<String> taskStatus;

    public ProjectPlanner() {
        super();
        this.tasks = new ArrayList<String>();
        this.taskStatus = new ArrayList<String>();

    }

    /** Show the current planner
     *
     * @return a string representation of this planner's content
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Project tasks: \n");
        for (int i = 0; i < tasks.size(); i++){
            sb.append(this.tasks.get(i));
            sb.append("--");
            sb.append(this.taskStatus.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    /** Add agenda to current planner
     *
     * @param s the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    @Override
    public Boolean Add(String s){
        if (this.tasks.contains(s)){
            return false;
        }
        else if (s.length() == 0){
            return false;
        }
        else{
            this.tasks.add(s);
            this.taskStatus.add("not completed");
            return true;
            }
        }

    /** Add agenda to current planner - index specified
     *
     * @param i index of new agenda item
     * @param s content of new item
     * @return true iff the agenda is correctly added
     */
    public Boolean Add(int i, String s) {
        if (i >= this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else{
            this.tasks.add(i, s);
            this.taskStatus.add(i, "not completed");
            return true;
        }
    }

    /** Complete certain agenda item
     *
     * @param Agenda: the agenda item to change status to completed
     * @return true iff the agenda is successfully changed to complete
     */
    public Boolean Complete(String Agenda){
        if (this.tasks.contains(Agenda)){
            int index = this.tasks.indexOf(Agenda);
            if (this.taskStatus.get(index).equals("not completed")){
                this.taskStatus.set(index, "completed");
                return true;
            }

        }
        return false;
    }


    /** Edit agenda to current planner
     *
     * @param i index of the agenda user wish to edit
     * @param s content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean Edit(int i, String s) {
        if (i >= this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else if (s.length() == 0){  // if the new agenda is empty
            return false;
        }
        else{
            this.tasks.set(i, s);
            this.taskStatus.set(i, "not completed");
            return true;
        }
    }

    /** Delete agenda item on current planner
     *
     *
     * @return true iff the agenda is correctly deleted from current planner
     */
    @Override
    public boolean Delete(int i) {
        if (i >= this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else{
            this.tasks.remove(i);
            this.taskStatus.remove(i);
            return true;
        }
    }
}
