package UseCase;
import Entity.DailyPlanner;
import Entity.Planner;
import Entity.ProjectPlanner;

import java.util.ArrayList;
import java.util.HashMap;

public class PlannerManager {
    private HashMap<String, Planner> idToPlanner;

    public PlannerManager() {
        this.idToPlanner = new HashMap<>();
    }

//    /** Create new DailyPlanner -- default start at 09:00, end at 17:00, interval 60 mins
//     *
//     * @param plannerName: name of the planner
//     * @return true iff a new DailyPlanner is created
//     */
//    public String NewDailyPlanner(String plannerName){
////        this.planner = new DailyPlanner("09:00", "17:00", 60);
////        return true;
//        DailyPlanner nd = new DailyPlanner(plannerName, "09:00", "17:00", 60);
//        this.idToPlanner.put(nd.getID(), nd);
//        return nd.getID();
//
//    }
//
//    public String NewDailyPlanner(){
//        DailyPlanner nd = new DailyPlanner();
//        nd.setPlannerInfo("Unititled", "09:00", "17:00", 60);
//        this.idToPlanner.put(nd.getID(), nd);
//        return nd.getID();
//    }

    /** Create new DailyPlanner -- default interval 60 mins
     *
     * @param plannerName name of the planner
     * @param startTime start time of planner
     * @param endTime end time of planner
     * @return true iff a new DailyPlanner is created
     */
    public String NewDailyPlanner(String plannerName, String startTime, String endTime){
//        this.planner = new DailyPlanner(startTime, endTime, 60);
//        return true;
        DailyPlanner nd = new DailyPlanner(plannerName, startTime, endTime, 60);
        this.idToPlanner.put(nd.getID(), nd);
        return nd.getID();
    }

//    /** Create new DailyPlanner
//     *
//     * @param startTime start time of planner
//     * @param endTime end time of planner
//     * @param Interval interval
//     * @return true
//     */
//    public String NewDailyPlanner(String plannerName, String startTime, String endTime, int Interval){
////        this.planner = new DailyPlanner(startTime, endTime, Interval);
////        return true;
//        DailyPlanner nd = new DailyPlanner(plannerName, startTime, endTime, Interval=60);
//        this.idToPlanner.put(nd.getID(), nd);
//        return nd.getID();
//    }

    /** Create an empty Project Planner
     *
     * @param plannerName name of planner
     * @return true iff a new ProjectPlanner is correctly created
     */
    public String NewProjectPlanner(String plannerName){
//        this.planner = new ProjectPlanner();
//        return true;
        ProjectPlanner pp = new ProjectPlanner(plannerName);
        this.idToPlanner.put(pp.getID(), pp);
        return pp.getID();
    }

//    /** Create a Project Planner with Agendas
//     *
//     * @param plannerName name of planner
//     * @param Agendas: agendas for this agenda
//     * @return true iff a new ProjectPlanner is correctly created and agendas are added
//     */
//    public String  NewProjectPlanner(String plannerName, ArrayList<String> Agendas){
////        this.planner = new ProjectPlanner();
////        for (String Agenda: Agendas){
////            this.planner.Add(Agenda);
////        }
////        return true;
//        ProjectPlanner pp = new ProjectPlanner(plannerName);
//        for (String Agenda: Agendas){
//            pp.Add(Agenda);
//        }
//        return pp.getID();
//    }


    /** Create a string representation of planner tasks
     *
     * @param id A String representing the id number.
     * @return a string representation of the planner
     */
    public String toString(String id){ return this.findPlanner(id).toString(); }


    /** Create a string representation of daily planner remain tasks
     *
     * @return a string representation of the remain tasks for the daily planners.
     */
    public String DailyPlannerRemainTasks(String id){
        if (this.findPlanner(id).getClass() == DailyPlanner.class) {
            return ((DailyPlanner) this.findPlanner(id)).RemainTasks();
        }
        return null;
    }


    /** Set the idToPlanner Attributes
     *
     * @param hm The HashMap object we want to assign.
     */
    public void setIdToPlanner(HashMap<String, Planner> hm) {
        this.idToPlanner = hm;
    }

    /** Edit agenda on current planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly edited on current planner
     */
    public boolean Edit(String id, int i, String agenda){
        this.findPlanner(id).Edit(i, agenda);
        return true;
    }

    /** Edit agenda on DailyPlanner base on time stamp
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly edited
     */
    public boolean Edit(String id, String time, String newAgenda){
//        if (this.planner.getClass() == DailyPlanner.class){
//            ((DailyPlanner)this.planner).Edit(time, newAgenda);
//            return true;
//        }
//        else{
//            return false;
//        }
        if (this.findPlanner(id).getClass() == DailyPlanner.class){
            ((DailyPlanner) this.findPlanner(id)).Edit(time, newAgenda);
            return true;
        }
        else{
            return false;
        }
    }


    /** Return the Planner with given ID
     *
     * @param id A String representing the id number.
     * @return A Planner object with given ID.
     */
    public Planner findPlanner(String id) {
        return this.idToPlanner.get(id);
    }


    /** Change privacy status of current planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly changed (from "public to "private or vise versa)
     */
    public boolean ChangePrivacyStatus(String id, String status){
        return this.findPlanner(id).ChangePrivacyStatus(status);
    }


    /** Return all the planner in a Array List.
     *
     * @return An ArrayList containing all the Planners.
     */
    public ArrayList<Planner> getAllPlanner() {
        return new ArrayList<>(this.idToPlanner.values());
    }

}
