package Entity;
import java.sql.Time;
import java.util.ArrayList;

public class ProjectPlanner extends Planner {
    public ArrayList<String> tasks;
    public ProjectPlanner() {
        this.tasks = new ArrayList<String>();
    }

    /**
     * Show the current planner
     *
     * @return a string represent this planner's content
     */
    @Override
    public String Display() {
        return this.tasks.toString();
    }

    /**
     * add agenda to current planner
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
            return true;
        }
        }

    /** add agenda to current planner - index specified
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
            return true;
        }
    }



    /**
     * edit agenda to current planner
     * @param i index of the agenda user wish to edit
     * @param s content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    //@Override
    public Boolean Edit(int i, String s) {
        if (i >= this.tasks.size() - 1){ // if i is over the size limit
            return false;
        }
        else if (s.length() == 0){  // if the new agenda is empty
            return false;
        }
        else{
            this.tasks.set(i, s);
            return true;
        }
    }

    /**
     * delete agenda to current planner
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
            return true;
        }
    }
}
