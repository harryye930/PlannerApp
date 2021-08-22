package Tests;

import entity.DailyPlanner;
import entity.ProjectPlanner;
import UseCase.PlannerManager;

public class PlannerTests {
    public static void main(String[] args) {
        // Testing DailyPlanner entity
        System.out.println("Testing DailyPlanner...");
        DailyPlanner dailyPlanner = new DailyPlanner("Test Daily Planner", "09:00", "24:00", 60);
        System.out.println(dailyPlanner);
        dailyPlanner.add("12:01","CSC207");
        System.out.println(dailyPlanner);
        dailyPlanner.edit("12:00", "CSC343");
        dailyPlanner.edit("29:00", "Fail");
        System.out.println(dailyPlanner);
        dailyPlanner.add("Dinner");
        System.out.println(dailyPlanner);
        dailyPlanner.delete("55:99");
        dailyPlanner.delete("12:00");
        System.out.println(dailyPlanner);
        dailyPlanner.add("18:05","A");
        dailyPlanner.add("18:20","B");
        dailyPlanner.add("19:55","C");
        System.out.println(dailyPlanner);
        System.out.println(dailyPlanner.remainTasks());

        //Testing ProjectPlanner.java
        System.out.println("Testing ProjectPlanner.java...");
        ProjectPlanner projectPlanner = new ProjectPlanner("Test Task Planner");
        System.out.println(projectPlanner);
        projectPlanner.add("Write Entity");
        projectPlanner.add("Write Use Case");
        System.out.println(projectPlanner);
        projectPlanner.complete("Write Entity");
        projectPlanner.add("Write Entity"); // will return false and do nothing
        System.out.println(projectPlanner);
        projectPlanner.add(1, "Meeting at 10");
        projectPlanner.edit(1, "Meeting at 11");
        System.out.println(projectPlanner);
        projectPlanner.delete(2);
        projectPlanner.delete("Write Entity");
        System.out.println(projectPlanner);


    }
}
