package Controller;

import Gateway.PlannerGateway;
import Presenter.Presenter;
import UseCase.PlannerManager;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *  the Planner controller.
 */
public class PlannerController {
    private PlannerManager plannerManager;
    private PlannerGateway accGateway;
    private UserInputController userInputController;

    /**
     * Initialize the PlannerController. Create a new PlannerManager.
     */
    public PlannerController(){
        this.plannerManager = new PlannerManager();
        this.accGateway = new PlannerGateway(plannerManager);
        this.userInputController = new UserInputController();
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
     * Planner options helper method. Enables several options for editing planners.
     * Planner edit options includes edit personal planners, edit other public planners, return to planner menu.
     * Returns true if the user wants to stay in this menu; otherwise, returns false.
     * @return a boolean value indicating their interest to remain in the current menu.
     */
    public boolean plannerEditOptions(Presenter presenter, String currentRetriever) {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        String[] plannerEditOptions = {"A", "B", "C"};

        presenter.showEditPlannerMenu(); // display planner edit menu
        userInput = this.userInputController.validInput(plannerEditOptions);
        int plannerID;

        switch(userInput){
            case "A": //edit personal planners
                presenter.showAllPersonalPlanners(currentRetriever); // display all personal planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                personalPlannerEditOptions(plannerID, presenter);
                break;
            case "B":  //edit other public planners
                presenter.showAllPublicPlanners(); // display all public planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                publicPlannerEditOptions(plannerID, presenter);
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
     * Provides different edit options for editing a personal planner.
     * @param plannerID is the unique id of the planner being edited.
     */
    public void personalPlannerEditOptions(int plannerID, Presenter presenter){
        Scanner scanner = new Scanner(System.in);
        String userInput;
        String[] plannerEditOptions = {"A", "B", "C", "D"}; //options user can choose from

        presenter.showObjIntroMessage("planner", plannerID);
        presenter.showDetailViewPlanner(plannerID);
        presenter.showEditPersonalPlannerMenu(); // display personal planner edit menu
        userInput = this.userInputController.validInput(plannerEditOptions);

        switch (userInput) {
            case "A": // edit planner agenda
                editPlannerAgendaOptions(plannerID, presenter);
                break;
            case "B": // change privacy setting
                System.out.println("select private/public");
                String privacyState = scanner.nextLine();
                this.changePrivacyStatus(plannerID, privacyState);
                break;
            case "C": // delete planner
                presenter.showFeatureUnavailableScreen(); // display message showing feature not yet available
                break;
            case "D": //return to edit planner menu
                break;
        }
    }


    /**
     * Provides different edit options for editing a public planner.
     * @param plannerID is the unique id of the planner being edited.
     */
    private void publicPlannerEditOptions(int plannerID, Presenter presenter){
        String userInput;
        String[] plannerEditOptions = {"A", "B"};

        presenter.showObjIntroMessage("planner", plannerID);
        presenter.showDetailViewPlanner(plannerID);
        presenter.showEditPublicPlannerMenu(); // display public planner edit menu
        userInput = this.userInputController.validInput(plannerEditOptions);

        switch (userInput){
            case "A": // edit planner agenda
                editPlannerAgendaOptions(plannerID, presenter);
                break;
            case "B": // return to edit planner menu
                break;
        }
    }


    /**
     * Provides different edit options for editing a planner agenda.
     * @param plannerID is the unique id of the planner being edited.
     */
    public void editPlannerAgendaOptions(int plannerID, Presenter presenter){
        Scanner scanner = new Scanner(System.in);
        String type = this.getType(plannerID);

        presenter.showObjIntroMessage("planner", plannerID); // intro message showing what planner looks like now
        presenter.showDetailViewPlanner(plannerID);
        switch (type){
            case "daily":
                presenter.showPlannerEditTimeQuestion();
                String time = scanner.nextLine();
                presenter.showPlannerEditAgendaQuestion(); // show message asking user to edit the new agenda for the
                // time chosen
                String agenda = scanner.nextLine();
                this.edit(plannerID, time, agenda);
                presenter.showUpdateCompletedMessage();
                break;
            case "project":
                presenter.showPlannerEditIndexQuestion();
                int i = scanner.nextInt();
                scanner.nextLine();
                if (i - 1 <= this.getNumAgendas(plannerID)){
                    presenter.showPlannerEditAgendaQuestion();
                    String projectAgenda = scanner.nextLine();
                    this.edit(plannerID, i - 1, projectAgenda);
                    presenter.showUpdateCompletedMessage();
                }
                else{
                    presenter.showPlannerReEnterIndexMessage();
                }
                break;

        }
    }


    /**
     * Planner options helper method. Allows different options for planner creation.
     */
    public String plannerCreateOptions(String userId, Presenter presenter) {
        String userInput;
        String[] createOptions = {"A", "B", "C"};

        presenter.showPlannerCreateMenu(); // display planner creation options: daily, project, exit to planner menu
        userInput = this.userInputController.validInput(createOptions);
        switch (userInput) {
            case "A": // daily
                int dailyPlannerId = this.createNewDailyPlanner();
                this.setPlannerAuthor(dailyPlannerId, userId);
                presenter.showPlannerCreatedMessage(StringUtils.capitalize(this.getType(dailyPlannerId)));
                presenter.showDetailViewPlanner(dailyPlannerId);
                return ((Integer) dailyPlannerId).toString();
            case "B": // project
                int projectPlannerId = this.createNewProjectPlanner();
                this.setPlannerAuthor(projectPlannerId, userId);
                presenter.showPlannerCreatedMessage(StringUtils.capitalize(this.getType(projectPlannerId)));
                presenter.showDetailViewPlanner(projectPlannerId);
                return ((Integer) projectPlannerId).toString();
            case "C": // exit to planner menu
                return null;
        }
        return  null;
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
