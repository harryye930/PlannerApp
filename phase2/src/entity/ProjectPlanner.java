package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A type of Planner - Project Planner. Contains three status columns for tasks, so users can place the tasks for a
 * project in an appropriate column.
 */
public class ProjectPlanner extends Planner{
    /**
     * tasks: A Map containing all the status columns and the tasks in them.
     * numTasks: Total number of tasks in this planner.
     */
    private Map<String, List<String>> tasks;
    private final int ID;
    private int numTasks = 0;

    /**
     * Initializes a ProjectPlanner.
     * @param plannerName Name of the planner.
     * @param firstColName Name of the first status column.
     * @param secondColName Name of the second status column.
     * @param thirdColName Name of the third status column.
     */
    public ProjectPlanner(String plannerName, String firstColName, String secondColName, String thirdColName) {
        super(plannerName);
        this.ID = super.getID();
        initializePlannerVars(firstColName, secondColName, thirdColName);
    }

    /**
     * Initializes a ProjectPlanner
     * @param numPlannersLoaded Number of planners already loaded in the program. So the ID of the planner will start
     *                            from numPlannersLoaded + 1.
     */
    public ProjectPlanner(int numPlannersLoaded, String plannerName, String firstColName, String secondColName,
                          String thirdColName) {
        super(numPlannersLoaded, plannerName);
        this.ID = super.getID();
        initializePlannerVars(firstColName, secondColName, thirdColName);
    }

    private void initializePlannerVars(String firstColName, String secondColName, String thirdColName) {
        this.tasks = new HashMap<>();
        tasks.put(firstColName, new ArrayList<>());
        tasks.put(secondColName, new ArrayList<>());
        tasks.put(thirdColName, new ArrayList<>());
    }

    /**
     * @return A string representing this Project Planner.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        String separator = "====================\n";
        res.append("Status: ").append(this.getPrivacyStatus()).append("\n");
        res.append("Planner ID:").append(this.getID()).append("\n");
        res.append("Planner name: ").append(this.plannerName).append("\n");
        for (String columnName: tasks.keySet()) {
            res.append("The \"").append(columnName).append("\" column has following tasks:\n");
            for (String task: tasks.get(columnName)) {
                res.append(task).append("\n");
            }
            res.append(separator);
        }
        return res.toString();
    }

    /**
     * @return A String representing the type of the planner.
     */
    @Override
    public String getType() {
        return "project";
    }

    /**
     * Returns the total number of agendas stored in this Project Planner object.
     * @return An integer representing the total number of agendas.
     */
    @Override
    public int getNumAgendas() {
        return numTasks;
    }

    /**
     * Adds agenda to the column with columnName in a Project Planner.
     * @param columnName The name of the column where the agenda will be added.
     * @param agenda The agenda to be added.
     * @return true iff the agenda is successfully added to the planner.
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
     * Edits this ProjectPlanner.
     * @param columnName The name of the status column to move agenda into.
     * @param agenda The agenda to be moved.
     * @return true iff the agenda is correctly edited in this planner.
     */
    @Override
    public Boolean edit(String columnName, String agenda) {
        return this.ChangeTaskStatus(agenda, columnName);
    }

    /**
     * Moves the agenda from its current column to newColumn.
     * @param newColumn The name of the column that the user wants to move agenda into.
     * @param agenda The agenda to change status for.
     * @return true iff the agenda is successfully moved to newColumn.
     */
    @Override
    public Boolean ChangeTaskStatus(String agenda, String newColumn) {
        String currColumn = null;
        for (String columnN: tasks.keySet()) {
            if (tasks.get(columnN).contains(agenda)) {
                currColumn = columnN;
            }
        }
        if (currColumn == null || currColumn.equals(newColumn) || !tasks.containsKey(newColumn)) {
            return false;
        } else {
            tasks.get(newColumn).add(agenda);
            tasks.get(currColumn).remove(agenda);
            return true;
        }
    }

    /**
     * @return Integer id of the planner.
     */
    @Override
    public int getID(){
        return ID;
    }

    /**
     * Deletes the agenda from its current column.
     * @param agenda A String representing the agenda.
     * @return A boolean value representing whether the deletion is successful.
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
