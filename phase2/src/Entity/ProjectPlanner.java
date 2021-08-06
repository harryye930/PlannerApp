package Entity;

import java.util.ArrayList;
import java.util.Objects;

public class ProjectPlanner extends Planner {
    String plannerName;
    String firstColName;
    String secondColName;
    String thirdColName;
    ArrayList<String> allProjectEvents;
    ArrayList<String> firstColEvents;
    ArrayList<String> secondColEvents;
    ArrayList<String> thirdColEvents;

    int ID;

    public ProjectPlanner(String plannerName, String firstColName, String secondColName, String thirdColName) {
        this.plannerName = plannerName;
        this.firstColName = firstColName;
        this.secondColName = secondColName;
        this.thirdColName = thirdColName;

        this.firstColEvents = new ArrayList<String>();
        this.secondColEvents = new ArrayList<String>();
        this.thirdColEvents = new ArrayList<String>();
        this.allProjectEvents = new ArrayList<String>();

        this.ID = super.getID();
    }


    /**
     * Show the current planner
     *
     * @return a string represent this planner's content
     */
    @Override
    public String toString() {
        String allEvent ="All Project Planner events:" + this.allProjectEvents.toString() + "\n";
        String firstColumnEvents = "All events in "+ this.firstColName+ ": " + this.firstColEvents.toString() + "\n";
        String secondColumnEvents = "All events in "+ this.secondColName+ ": " + this.secondColEvents.toString() + "\n";
        String thirdColumnEvents = "All events in "+ this.thirdColName+ ": " + this.thirdColEvents.toString() + "\n";
        String plannerName = this.plannerName + "\n";
        return plannerName +
                allEvent +
                firstColumnEvents +
                secondColumnEvents +
                thirdColumnEvents;


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
     * @return a int representing of the number of planner agendas.
     */
    @Override
    public int getNumAgendas() {
        return this.allProjectEvents.size();
    }

    /**
     * Add agenda to current planner -- default to first column
     *
     * @param s agenda of the agenda user wish to add
     * @return true iff the agenda is correctly added to current planner
     */
    @Override
    public Boolean add(String s) {
        return this.add(s, this.firstColName);
    }

    public Boolean add(String s, String columnName) {
        if (Objects.equals(this.firstColName, columnName)) {
            if (!this.allProjectEvents.contains(s)) {
                this.firstColEvents.add(s);
                this.allProjectEvents.add(s);
                return true;
            } else {
                return false;
            }
        } else if (Objects.equals(this.secondColName, columnName)) {
            if (!this.allProjectEvents.contains(s)) {
                this.secondColEvents.add(s);
                this.allProjectEvents.add(s);
                return true;
            } else {
                return false;
            }
        } else if (Objects.equals(this.thirdColName, columnName)) {
            if (!this.allProjectEvents.contains(s)) {
                this.thirdColEvents.add(s);
                this.allProjectEvents.add(s);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Edit agenda to current planner
     *
     * @param i        index of the agenda user wish to edit
     * @param newEvent content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    @Override
    public Boolean edit(int i, String newEvent) {
        if (i < this.allProjectEvents.size() && !this.allProjectEvents.contains(newEvent)) {
            String oldAgenda = this.allProjectEvents.get(i);
            this.allProjectEvents.set(i, newEvent);
            if (this.firstColEvents.contains(oldAgenda)) {
                this.firstColEvents.set(this.firstColEvents.indexOf(oldAgenda), newEvent);
            } else if (this.secondColEvents.contains(oldAgenda)) {
                this.secondColEvents.set(this.secondColEvents.indexOf(oldAgenda), newEvent);
            } else if (this.thirdColEvents.contains(oldAgenda)) {
                this.thirdColEvents.set(this.thirdColEvents.indexOf(oldAgenda), newEvent);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * edit agenda item by providing existing event name and new event name
     *
     * @param oldEvent name of old event name
     * @param newEvent name of new event name
     * @return true iff an event is been changed.
     */
    public Boolean edit(String oldEvent, String newEvent) {
        if (this.allProjectEvents.contains(oldEvent)) {
            int index = this.allProjectEvents.indexOf(oldEvent);
            return this.edit(index, newEvent);
        } else {
            return false;
        }
    }

    public Boolean editColumn(String eventName, String newColumnName) {
        if (this.getColumnName(eventName) != "ERROR"){  // the event exist and is in one of the column
           String oldColumnName = this.getColumnName(eventName);
           if (oldColumnName != newColumnName){ // the desire column is different tha current event column
               if (this.validColumn(newColumnName)){  // if the new column is valid
                   ArrayList<String> oldColumnEvents = this.getColumnEvent(oldColumnName);
                   ArrayList<String> newColumnEvents = this.getColumnEvent(newColumnName);
                   oldColumnEvents.remove(eventName);
                   newColumnEvents.add(eventName);
                   return true;
               }
               else{
                   return false;
               }
           }
           else{
               return false;
           }
        }
        else{
            return false;
        }
    }

    public Boolean delete(String eventName) {
        if (!this.getColumnName(eventName).equals("ERROR")) { //means event exist and we can have the col name
            String columnName = this.getColumnName(eventName);
            ArrayList<String> columnEvents = this.getColumnEvent(columnName);
            columnEvents.remove(eventName);
            this.allProjectEvents.remove(eventName);
            return true;

        }
        else{
            return false;
        }

    }

    private String getColumnName(String eventName) {
        if (this.allProjectEvents.contains(eventName)) {
            if (this.firstColEvents.contains(eventName)) {
                return this.firstColName;
            } else if (this.secondColEvents.contains(eventName)) {
                return this.secondColName;

            } else if (this.thirdColEvents.contains(eventName)) {
                return this.thirdColName;
            } else {
                return "ERROR";
            }

        } else {
            return "ERROR";
        }
    }

    private ArrayList<String> getColumnEvent(String colName) {
        if (colName.equals(this.firstColName)) {
            return this.firstColEvents;
        } else if (colName.equals(this.secondColName)) {
            return this.secondColEvents;
        } else {  // if (colName.equals(this.thirdColName))
            return this.thirdColEvents;
        }
    }

    private Boolean validColumn(String columnName){
        return (columnName == this.firstColName ||
                columnName == this.secondColName ||
                columnName == this.thirdColName);
    }
}

