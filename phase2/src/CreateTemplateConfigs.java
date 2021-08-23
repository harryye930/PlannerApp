import entity.DailyTemplate;
import entity.ProjectTemplate;
import entity.RemindersTemplate;
import entity.Template;
import gateway.TemplateGateway;
import use_case.TemplateManager;

/**
 * Creates template config file.
 */
public class CreateTemplateConfigs {
    public static void main(String[] args) {
        // Create a daily template
        String dailyTemplateName = "Default Daily Template";
        String dailyPlannerNamePrompt = "What is the name of the planner that will be created from this Daily Template?";
        String startTimePrompt = "What is the start hour of the planner? " +
                "Enter in the form of hh:00 in 24-hour clock (e.g., 09:00 means 9am)";
        String endTimePrompt = "What is the end hour of the planner? " +
                "Enter in the form of hh:00 in 24-hour clock (e.g., 21:00 means 9pm)";
        String timeIncrementPrompt = "What is the time increment of the planner (in hour)? Enter an integer. " +
                "(e.g., if it's 1 hour, enter 1)";
        Template dailyTemplate = new DailyTemplate(dailyTemplateName, dailyPlannerNamePrompt, startTimePrompt,
                endTimePrompt, timeIncrementPrompt);
        dailyTemplate.setPublishedStatus(true);

        // Create a project template
        String projectTemplateName = "Default Project Template";
        String projectPlannerNamePrompt = "What is the name of the planner that will be created from this Project Template?";
        String firstStatusPrompt = "Enter the column name for the first status column of the planner (e.g., \"TODO\")";
        String secondStatusPrompt = "Enter the column name for the second status column of the planner (e.g., \"DOING\")";
        String thirdStatusPrompt = "Enter the column name for the third status column of the planner (e.g., \"DONE\")";
        Template projectTemplate = new ProjectTemplate(projectTemplateName, projectPlannerNamePrompt,
                firstStatusPrompt, secondStatusPrompt, thirdStatusPrompt);
        projectTemplate.setPublishedStatus(true);

        String remindersTemplateName = "Default Reminders Template";
        String remindersPlannerNamePrompt = "What is the name of the planner that will be created from this Reminders Template?";
        String taskHeadingPrompt = "Enter the column name for the column containing task names in the planner " +
                "(e.g., \"Tasks\", \"Review Items\", \"Assignments\", etc.)";
        String dateHeadingPrompt = "Enter the column name for the column containing deadlines/dates in the planner </br>" +
                "(e.g., \"Deadline\", \"Due Date\", etc.)";
        String completionStatusHeadingPrompt = "Enter the column name for the column containing completion status in the planner </br>" +
                "(e.g., \"Completion Status\", \"Completed?\", etc.)";
        Template remindersTemplate = new RemindersTemplate(remindersTemplateName, remindersPlannerNamePrompt,
                taskHeadingPrompt, dateHeadingPrompt, completionStatusHeadingPrompt);
        remindersTemplate.setPublishedStatus(true);

        // Add templates to TemplateManager
        TemplateManager tm = new TemplateManager();
        tm.addTemplate(dailyTemplate);
        tm.addTemplate(projectTemplate);
        tm.addTemplate(remindersTemplate);
        System.out.println(tm.detailViewAllTemplates(true));

        TemplateGateway templateGateway = new TemplateGateway(tm);
        templateGateway.save();
    }
}
