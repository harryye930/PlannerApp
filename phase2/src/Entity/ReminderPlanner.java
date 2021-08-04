package Entity;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents one type of Planner in the program - a Reminder Planner, it corresponds to the Reminder Template.
 */
public class ReminderPlanner extends Planner{


    public ReminderPlanner(){

    }

    @Override
    public String toString(){
        return "111";
    }

    @Override
    public String getType(){
        return "Reminder";
    }


    @Override
    public int getNumAgendas(){
        return 1;
    }

    @Override
    public Boolean add (String s){
        return true;
    }


    @Override
    public Boolean edit(int i, String agenda){
        return true;
    }
}
