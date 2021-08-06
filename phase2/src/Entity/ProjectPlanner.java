package Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectPlanner extends Planner{
    private final HashMap<String, ArrayList<String>> tasks;
    private int numTasks = 0;

    public ProjectPlanner(String plannerName, String firstColName, String secondColName, String thirdColName) {
        this.tasks = new HashMap<>();
        tasks.put(firstColName, new ArrayList<>());
        tasks.put(secondColName, new ArrayList<>());
        tasks.put(thirdColName, new ArrayList<>());
    }

    /**
     * Show the current planner
     *
     * @return a string represent this planner's content
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        String separator = "====================\n";
        for (String columnName: tasks.keySet()) {
            res.append("The ").append(columnName).append("has following tasks:\n");
            for (String task: tasks.get(columnName)) {
                res.append(task).append("\n");
            }
            res.append(separator);
        }
        return res.toString();
    }

    /**
     * @return a String representing of the planner type.
     */
    @Override
    public String getType() {
        return "project";
    }

    /**
     * Show the number of planner agendas.
     *
     * @return an int representing of the number of planner agendas.
     */
    @Override
    public int getNumAgendas() {
        return numTasks;
    }

    /**
     * Add agenda to current planner
     *
     * @param columnName the first input of the add method.
     * @param agenda the second input of the add method.
     * @return true iff the agenda is correctly added to current planner.
     */
    @Override
    public Boolean add(String columnName, String agenda) {
        if (tasks.containsKey(columnName) && !tasks.get(columnName).contains(agenda)) {
            tasks.get(columnName).add(agenda);
            numTasks++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Edit agenda to current planner
     *
     * @param columnName the original agenda the user wants to change
     * @param agenda the new content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean edit(String columnName, String agenda) {
        return this.ChangeTaskStatus(columnName, agenda);
    }

    /**
     * @param agenda   the task name the user wants to change status
     * @param columnName the status the user wants to change
     * @return true iff the planner is correctly changed to the right status
     */
    @Override
    public Boolean ChangeTaskStatus(String columnName, String agenda) {
        String currColumn = null;
        for (String columnN: tasks.keySet()) {
            if (tasks.get(columnN).contains(agenda)) {
                currColumn = columnN;
            }
        }
        if (currColumn == null || currColumn.equals(columnName) || !tasks.containsKey(columnName)) {
            return false;
        } else {
            tasks.get(columnName).add(agenda);
            tasks.get(currColumn).remove(agenda);
            return true;
        }
    }

    /**
     * Delete the agenda in chosen column.
     * @param agenda A String representing the agenda.
     * @return A boolean value representing whether the delete is successful or not.
     */
    public boolean delete(String agenda) {
        String currColumn = null;
        for (String columnN: tasks.keySet()) {
            if (tasks.get(columnN).contains(agenda)) {
                currColumn = columnN;
            }
        }
        if (currColumn == null) {
            return false;
        } else {
            tasks.get(currColumn).remove(agenda);
            return true;
        }
    }
}
