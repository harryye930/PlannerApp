package Controller;

import Entity.DailyPlanner;
import Entity.ProjectPlanner;
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
    private Presenter presenter;
    private Scanner scanner;
    private UserActionController userActionController;
    private AccessController accessController;


    public PlannerController(){
        this.plannerManager = new PlannerManager();


    }

    public int createNewDailyPlanner(){
        return plannerManager.newDailyPlanner("daily planner", "09:00", "18:00");
    }


    public int createNewProjectPlanner(){
        return plannerManager.newProjectPlanner("project planner");
    }

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

    public boolean deletePlanner(int id){
        return this.plannerManager.deletePlanner(id);
    }

    public String showAllPlanners (){
        return plannerManager.showAllPlanners();
    }

    public ArrayList<Integer> getPlannerByAuthor(String author){
        return plannerManager.getPlannersByAuthor(author);
    }

    public ArrayList<Integer> getPublicPlanners(){
        return plannerManager.getPublicPlanners();
    }

    public String getType(int id){
        return plannerManager.plannerType(id);
    }

    public int getNumAgendas(int id) {
        return plannerManager.getNumAgendas(id);
    }
    
    boolean plannerEditOptions(String currentRetriever, String userInput){
        String[] plannerEditOptions = {"A", "B", "C"};

        presenter.showEditPlannerMenu(); // display planner edit menu
        int plannerID;

        switch(userInput){
            case "A": //edit personal planners
                presenter.showAllPersonalPlanners(currentRetriever); // display all personal planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                userActionController.personalPlannerEditOptions(plannerID);
                break;
            case "B":  //edit other public planners
                presenter.showAllPublicPlanners(); // display all public planners
                presenter.showIDForEditQuestion("planner"); // display message asking user to enter ID of planner to edit
                plannerID = Integer.parseInt(scanner.nextLine()); // gets ID of planner user wants to edit
                userActionController.publicPlannerEditOptions(plannerID);
                break;
            case "C":
                return false;
        }
        // We know that the user didn't select return to prev. menu and that the requested action by the user is completed.
        // The user will wants to remain in the <plannerEditOptions> menu until they explicitly indicate their interest to
        // return to the previous menu.
        return true;
    }
    void personalPlannerEditOptions(int plannerID, String userInput){
        switch (userInput) {
            case "A": // edit planner agenda
                userActionController.editPlannerAgendaOptions(plannerID);
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

    void publicPlannerEditOption(int plannerID, String userInput){
        switch (userInput){
            case "A": // edit planner agenda
                userActionController.editPlannerAgendaOptions(plannerID);
                break;
            case "B": // return to edit planner menu
                break;
        }
    }

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

        void plannerCreateOptions (String userId, String currentRetriever){
            String userInput;
            String[] createOptions = {"A", "B", "C"};

            presenter.showPlannerCreateMenu(); // display planner creation options: daily, project, exit to planner menu
            userInput = userActionController.validInput(createOptions);
            switch (userInput) {
                case "A": // daily
                    int dailyPlannerId = createNewDailyPlanner();
                    setPlannerAuthor(dailyPlannerId, userId);
                    accessController.setPlanner(currentRetriever, ((Integer) dailyPlannerId).toString());
                    presenter.showPlannerCreatedMessage(StringUtils.capitalize(getType(dailyPlannerId)));
                    presenter.showDetailViewPlanner(dailyPlannerId);
                    break;
                case "B": // project
                    int projectPlannerId = createNewProjectPlanner();
                    setPlannerAuthor(projectPlannerId, userId);
                    accessController.setPlanner(currentRetriever, ((Integer) projectPlannerId).toString());
                    presenter.showPlannerCreatedMessage(StringUtils.capitalize(getType(projectPlannerId)));
                    presenter.showDetailViewPlanner(projectPlannerId);
                    break;
                case "C": // exit to planner menu
                    break;
            }
        }
        void plannerViewOption(String userID){
            String userInput;
            String[] viewOptions = {"A", "B", "C"};

            presenter.showPlannerViewMenu(); // display planner view options: personal, public, exit to planner menu
            userInput = userActionController.validInput(viewOptions);
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
    }
