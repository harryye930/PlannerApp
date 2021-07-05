package UseCase;
import Entity.DailyPlanner;
import Entity.Planner;
import Entity.ProjectPlanner;

public class PlannerManager {
    Planner planner;

    public boolean newDailyPlanner(){
        this.planner = new DailyPlanner("09:00", "17:00", 60);
        return true;
    }
    public boolean newDailyPlanner(String startTime, String endTime){
        this.planner = new DailyPlanner(startTime, endTime, 60);
        return true;
    }
    public boolean newDailyPlanner(String startTime, String endTime, int Interval){
        this.planner = new DailyPlanner(startTime, endTime, Interval);
        return true;
    }
    public boolean newProjectPlanner(){
        this.planner = new ProjectPlanner();
        return true;
    }
    public boolean edit(int i, String s){
        planner.Edit(i, s);
        return true;
    }
    public boolean edit(String)

}
