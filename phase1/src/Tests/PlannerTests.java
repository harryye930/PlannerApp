package Tests;

import Entity.DailyPlanner;
import UseCase.PlannerManager;

public class PlannerTests {
    public static void main(String[] args) {
        System.out.println("Testing Planner...");
        DailyPlanner dailyPlanner = new DailyPlanner("09:00", "20:00", 60);

        System.out.println(dailyPlanner);
        dailyPlanner.Add("12:01","CSC207");
        System.out.println(dailyPlanner);
        dailyPlanner.Edit("12:00", "CSC343");
        dailyPlanner.Edit("29:00", "Fail");
        System.out.println(dailyPlanner);
        dailyPlanner.Add("Dinner");
        System.out.println(dailyPlanner);
    }
}
