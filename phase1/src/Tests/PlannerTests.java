package Tests;

import Entity.DailyPlanner;
import Entity.ProjectPlanner;
import UseCase.PlannerManager;

public class PlannerTests {
    public static void main(String[] args) {
        // Testing DailyPlanner entity
        System.out.println("Testing DailyPlanner...");
        DailyPlanner dailyPlanner = new DailyPlanner("09:00", "24:00", 60);
        System.out.println(dailyPlanner);
        dailyPlanner.Add("12:01","CSC207");
        System.out.println(dailyPlanner);
        dailyPlanner.Edit("12:00", "CSC343");
        dailyPlanner.Edit("29:00", "Fail");
        System.out.println(dailyPlanner);
        dailyPlanner.Add("Dinner");
        System.out.println(dailyPlanner);
        dailyPlanner.Delete("55:99");
        dailyPlanner.Delete("12:00");
        System.out.println(dailyPlanner);
        dailyPlanner.Add("18:05","A");
        dailyPlanner.Add("18:20","B");
        dailyPlanner.Add("19:55","C");
        System.out.println(dailyPlanner);
        dailyPlanner.RemainTasks();


        //Testing ProjectPlanner
        System.out.println("Testing ProjectPlanner...");
        ProjectPlanner projectPlanner = new ProjectPlanner();
        System.out.println(projectPlanner);
        projectPlanner.Add("Write Entity");
        projectPlanner.Add("Write Use Case");
        System.out.println(projectPlanner);
        projectPlanner.Complete("Write Entity");
        projectPlanner.Add("Write Entity"); // will return false and do nothing
        System.out.println(projectPlanner);
        projectPlanner.Add(1, "Meeting at 10");
        projectPlanner.Edit(1, "Meeting at 11");
        System.out.println(projectPlanner);
        projectPlanner.Delete(2);
        projectPlanner.Delete("Write Entity");
        System.out.println(projectPlanner);


    }
}
