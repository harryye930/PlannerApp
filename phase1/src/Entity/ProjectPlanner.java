package Entity;
import java.util.ArrayList;

public class ProjectPlanner extends Planner {
    private ArrayList<String> tasks;
    private ArrayList<String> taskStatus;
    private int ID;
    private int NumAgendas;

    public ProjectPlanner(String plannerName) {
        super(plannerName);
        this.tasks = new ArrayList<>();
        this.taskStatus = new ArrayList<>();
        this.ID = super.getID();
        this.NumAgendas = 0;


    }

    public String getType(){
        return "project";
    }

    /** Show the current planner
     *
     * @return a string representation of this planner's content
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String plannerInfo = this.plannerName + "\n" + "ID: " + ID + "\n" + "\nTasks: \n";
        sb.append(plannerInfo);
        if (this.tasks.isEmpty()){
            sb.append("N/A");
        }
        for (int i = 0; i < this.tasks.size(); i++){
            sb.append(this.tasks.get(i));
            sb.append("--");
            sb.append(this.taskStatus.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    /** Add agenda to current planner
     *
     * @param agenda the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    @Override
    public Boolean add(String agenda){
        if (this.tasks.contains(agenda)){
            return false;
        }
        else if (agenda.length() == 0){
            return false;
        }
        else{
            this.tasks.add(agenda);
            this.taskStatus.add("not completed");
            this.NumAgendas ++;
            return true;
        }
    }

    /** Add agenda to current planner - index specified
     *
     * @param i index of new agenda item
     * @param agenda content of new item
     * @return true iff the agenda is correctly added
     */
    public Boolean add(int i, String agenda) {
        if (i > this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else{
            this.tasks.add(i, agenda);
            this.taskStatus.add(i, "not completed");
            this.NumAgendas ++;
            return true;
        }
    }

    /** Complete certain agenda item
     *
     * @param Agenda: the agenda item to change status to completed
     * @return true iff the agenda is successfully changed to complete
     */
    public Boolean complete(String Agenda){
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
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean edit(int i, String agenda) {
        if (i > this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else if (agenda.length() == 0){  // if the new agenda is empty
            return false;
        }

        else{
            this.tasks.set(i, agenda);
            this.taskStatus.set(i, "not completed");
            return true;
        }
    }

    /** Delete agenda item on current planner
     *
     *
     * @return true iff the agenda is correctly deleted from current planner
     */
    public boolean delete(int i) {
        if (i > this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else{
            this.tasks.remove(i);
            this.taskStatus.remove(i);
            return true;
        }
    }

    public boolean delete(String agenda) {
        if (!this.tasks.contains(agenda)){ // if agenda is not one of the task
            return false;
        }
        else{

            this.taskStatus.remove(this.tasks.indexOf(agenda));
            this.tasks.remove(agenda);
            return true;
        }
    }

    public int getNumAgendas(){
        return this.NumAgendas;
    }
}
