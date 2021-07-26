package Controller;

import Entity.DailyPlanner;
import Entity.ProjectPlanner;
import Gateway.AccountGateway;
import Interface.Presenter;
import UseCase.PlannerManager;
import Entity.Planner;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *  the Planner controller.
 */
public class PlannerController {
    private PlannerManager plannerManager;
    private AccountGateway accGateway;
    private Presenter presenter;
    private Scanner scanner;
    private UserActionController userActionController;
    private AccessController accessController;

    /**
     * Initialize the PlannerController. Create a new PlannerManager.
     */
    public PlannerController(){
        this.plannerManager = new PlannerManager();
    }

    /**
     * Save the data to the database, call this function when a saving is needed. Must be called
     * when exit the application.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean save() {
        return this.accGateway.save();
    }

    /**
     * Load in the data from database to AccountManager.
     * @return A boolean value representing whether the loading process is successful or not.
     */
    public boolean load() {
        return this.accGateway.load();
    }

    /**
     * Create a new DailyPlanner with default name "daily planner", start time at 9 am, and endtime at 9 pm.
     * @return an integer representing the id of the planner
     */
    public int createNewDailyPlanner(){
        return plannerManager.newDailyPlanner("daily planner", "09:00", "18:00");
    }

    /**
     * Create a new Project Planner with default name "project planner".
     * @return the integer id of the planner
     */
    public int createNewProjectPlanner(){
        return plannerManager.newProjectPlanner("project planner");
    }

    /**
     * Set the author of the planner (a user)
     * @param id the id of the planner
     * @param userRetriever The userId of the user
     */
    public void setPlannerAuthor(int id, String userRetriever){
        plannerManager.setPlannerAuthor(id, userRetriever);
    }

    /** Pass on request to get a string representation of a planner
     *
     * @return a string of the planner tasks
     */
    public String toString(int id){
        return plannerManager.findPlanner(id).toString();
    }


    /** Pass on request to get a string representation of the daily planner remain tasks
     *
     * @return a string of the daily planner remain tasks.
     */
    public String DailyPlannerRemainTasks(int id){
        return plannerManager.dailyPlannerRemainTasks(id);
    }

    /** Pass on request to edit planner
     *
     * @param i index of the agenda user wish to edit
     * @param agenda content of the agenda user wish to edit
     * @return true iff the agenda is correctly requested to change based on current planner
     */
    public boolean edit(int id, int i, String agenda){
        return plannerManager.edit(id, i, agenda);
    }

    /** Pass on request to edit daily planner.
     *
     * @param time: time slot on DailyPlanner, HH:MM
     * @param newAgenda: new agenda item
     * @return true iff is correctly request to change.
     */
    public boolean edit(int id, String time, String newAgenda){
        return plannerManager.edit(id, time, newAgenda);
    }


    /** Pass on request to change their own planner
     *
     * @param status "private" or "public"
     * @return true iff the status is correctly requested to change. (from "public to "private or vise versa)
     */
    public boolean changePrivacyStatus(int id, String status){
        return plannerManager.changePrivacyStatus(id, status);
    }

    /**
     * delete the planner corresponding to the given id.
     * @param id the integer id of the planner
     * @return true if successfully deleted, false if otherwise.
     */
    public boolean deletePlanner(int id){
        return this.plannerManager.deletePlanner(id);
    }

    /**
     * print all planners to the screen.
     * @return String representation of all planners.
     */
    public String showAllPlanners (){
        return plannerManager.showAllPlanners();
    }

    /**
     * Show all the planners id of one author.
     * @param author the userId of the author.
     * @return the ArrayList of integer id of planners.
     */
    public ArrayList<Integer> getPlannerByAuthor(String author){
        return plannerManager.getPlannersByAuthor(author);
    }

    /**
     * return an ArrayList of all integer id of all planners made public by all authors.
     * @return the ArrayList of all public planner's id
     */
    public ArrayList<Integer> getPublicPlanners(){
        return plannerManager.getPublicPlanners();
    }

    /**
     * get the type of the planner. could be "daily" or "project" planner.
     * @param id the id of the planner
     * @return the String representation of the type of planner.
     */
    public String getType(int id){
        return plannerManager.plannerType(id);
    }

    /**
     * Get the number of agendas of a planner corresponding to given integer id.
     * @param id the integer id of the planner
     * @return the number of agendas of the planner
     */
    public int getNumAgendas(int id) {
        return plannerManager.getNumAgendas(id);
    }

    /**
     * print the possible options user have for planners to the user.
     * @param currentRetriever String representing userId
     * @param userInput User's choice
     * @return true if successfully did something, false if otherwise (i.e. user chose to go back to menu)
     */
    boolean plannerEditOptions(String currentRetriever, String userInput){
        String[] plannerEditOptions = {"A", "B", "C"};

        presenter.showEditPlannerMenu(); // display planner edit menu
        int plannerID;

        switch(userInput){
            case "A": //edit personal planners
                presenter.showAllPersonalPlanners(currentRetriever); // display all personal planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                //userActionController.personalPlannerEditOptions(plannerID);
                break;
            case "B":  //edit other public planners
                presenter.showAllPublicPlanners(); // display all public planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                //userActionController.publicPlannerEditOptions(plannerID);
                break;
            case "C":
                return false;
        }
        // We know that the user didn't select return to prev. menu and that the requested action by the user is completed.
        // The user will wants to remain in the <plannerEditOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }

    /**
     * give user options of editing their personal planner. i.e. the planners they created.
     * @param plannerID The integer id of the planner
     * @param userInput the choice user made.
     */
    void personalPlannerEditOptions(int plannerID, String userInput){
        switch (userInput) {
            case "A": // edit planner agenda
                //userActionController.editPlannerAgendaOptions(plannerID);
                break;
            case "B": // change privacy setting
                System.out.println("select private/public");
                String privacyState = scanner.nextLine();
                plannerManager.changePrivacyStatus(plannerID, privacyState);
                break;
            case "C": // delete planner
                presenter.showFeatureUnavailableScreen(); // display message showing feature not yet available
                break;
            case "D": //return to edit planner menu
                break;
        }
    }

    /**
     * print the editing options of public planners to the user.
     * @param plannerID the integer id of the planner
     * @param userInput user's choice, expected to be "A" or "B"
     */
    void publicPlannerEditOption(int plannerID, String userInput){
        switch (userInput){
            case "A": // edit planner agenda
                //userActionController.editPlannerAgendaOptions(plannerID);
                break;
            case "B": // return to edit planner menu
                break;
        }
    }

    /**
     * print the editing options of planner agenda to user.
     * @param plannerID the integer id of the planner
     */
    void editPlannerAgendaOptions(int plannerID) {
        String type = getType(plannerID);

        presenter.showObjIntroMessage("planner", plannerID); // intro message showing what planner looks like now
        presenter.showDetailViewPlanner(plannerID);
        switch (type) {
            case "daily":
                presenter.showPlannerEditTimeQuestion();
                String time = scanner.nextLine();
                presenter.showPlannerEditAgendaQuestion(); // show message asking user to edit the new agenda for the
                // time chosen
                String agenda = scanner.nextLine();
                edit(plannerID, time, agenda);
                presenter.showUpdateCompletedMessage();
                break;
            case "project":
                presenter.showPlannerEditIndexQuestion();
                int i = scanner.nextInt();
                scanner.nextLine();
                if (i <= getNumAgendas(plannerID)) {
                    presenter.showPlannerEditAgendaQuestion();
                    String projectAgenda = scanner.nextLine();
                    edit(plannerID, i, projectAgenda);
                    presenter.showUpdateCompletedMessage();
                } else {
                    presenter.showPlannerReEnterIndexMessage();
                }
                break;
        }
    }

    /**
     * print Options of creating planners to user.
     * @param currentRetriever the String representation of userId or email. The String that can identify a user.
     */
    void plannerCreateOptions (String currentRetriever){
            String userInput;
            String[] createOptions = {"A", "B", "C"};

            presenter.showPlannerCreateMenu(); // display planner creation options: daily, project, exit to planner menu
            userInput = "";//userActionController.validInput(createOptions);
            switch (userInput) {
                case "A": // daily
                    int dailyPlannerId = createNewDailyPlanner();
                    setPlannerAuthor(dailyPlannerId, currentRetriever);
                    accessController.setPlanner(currentRetriever, ((Integer) dailyPlannerId).toString());
                    presenter.showPlannerCreatedMessage(StringUtils.capitalize(getType(dailyPlannerId)));
                    presenter.showDetailViewPlanner(dailyPlannerId);
                    break;
                case "B": // project
                    int projectPlannerId = createNewProjectPlanner();
                    setPlannerAuthor(projectPlannerId, currentRetriever);
                    accessController.setPlanner(currentRetriever, ((Integer) projectPlannerId).toString());
                    presenter.showPlannerCreatedMessage(StringUtils.capitalize(getType(projectPlannerId)));
                    presenter.showDetailViewPlanner(projectPlannerId);
                    break;
                case "C": // exit to planner menu
                    break;
            }
        }

    /**
     * Print to user the view options of planner.
     * @param userID the unique String of userId.
     */
    void plannerViewOption(String userID){
            String userInput;
            String[] viewOptions = {"A", "B", "C"};

            presenter.showPlannerViewMenu(); // display planner view options: personal, public, exit to planner menu
            userInput = "";//userActionController.validInput(viewOptions);
            switch (userInput) {
                case "A": // personal planners
                    getPlannerByAuthor(userID);
                    break;
                case "B": // public planners created by others
                    getPublicPlanners();
                    break;
                case "C": // exit to planner menu
                    break;  // this is all this is required here for case C - don't worry!
            }
        }

    /**
     * Return the privacy status of the planner corresponding to the given id.
     * @param id the integer id of the planner
     * @return the String "private" or "public".
     */
    public String  getPrivacyStatus(int id) {
        return this.plannerManager.getPrivacyStatus(id);
    }
}
