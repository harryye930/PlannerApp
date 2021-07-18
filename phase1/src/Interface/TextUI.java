package Interface;

import Controller.AccessController;
import Controller.TemplateController;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TextUI {

    private AccessController ac = new AccessController();
    private TemplateController tc = new TemplateController();

    public TextUI showMenu() {
        this.ac = new AccessController();
        String retriever = ""; // The ID or Email of the Account we currently work on.
        this.ac.load();
        System.out.println("Welcome to your planner!");
        System.out.println("What do you want to do (login / create new account / login as guest)");
        Scanner scanner = new Scanner(System.in);
        String existAccount = scanner.nextLine();

        switch (existAccount){
            case "login":
                System.out.println("Please enter your ID or Email:");
                String userRetriever = scanner.nextLine();
                System.out.println("Please enter your password:");
                String userPassWord = scanner.nextLine();
                if (this.ac.logIn(userRetriever, userPassWord)) {
                    System.out.println("Login success.");
                    retriever = userRetriever;
                } else {
                    System.out.println("Invalid input, please try again.");
                }

            case "create new account":
                System.out.println("Email:");
                String email = scanner.nextLine();
                System.out.println("User Name:");
                String username = scanner.nextLine();
                System.out.println("Password:");
                String password = scanner.nextLine();
                System.out.println("Please enter your password again:");
                String tPassword = scanner.nextLine();
                assert tPassword.equals(password);
                String id = ac.createAccount(email, username, password);
                System.out.println("Please remember your ID:" + id);

//                do{
//                    System.out.println("Sorry, your username and password don't match! Please try again.");
//                    System.out.println("User Name:");
//                    username = scanner.nextLine();
//                    System.out.println("Password:");
//                    password = scanner.nextLine();
//                    authResult = accessController.logIn(username, password);
//                    System.out.println("");
//                }while(!authResult);





        }
            // Create menu
            String mainMenu = "=========================================================================\n" +
                    "Main Menu -- Please choose the letter associated to the option\n" +
                    "A. Planners Options\n" +
                    "B. Templates Options\n" +
                    "C. Account Options\n" +
                    "D. Log out\n" +
                    "=========================================================================";

            String personalInfo = "You are logged in as TODO: UserName";
            String plannerMenu =
                    "-------------------------------------------------------------------------\n" +
                    "Planner Menu -- Please choose the letter associated to the option\n" +
                    "A. View all personal planners \n" +
                    "B. Create a new Planner from template \n" +
                    "C. Edit a current planner \n" +
                    "D. Delete a current planner \n" +
                    "E. Exit to Main Menu\n" +
                    "-------------------------------------------------------------------------\n";

            String templateMenu =
                    "-------------------------------------------------------------------------\n"+
                    "Template Menu -- Please choose the letter associated to the option\n" +
                    "A. View all templates\n" +
                    "B. Create a new template (Admin Only) \n" +
                    "C. Edit a current template (Admin Only) \n" +
                    "D. Delete a current template (Admin Only) \n" +
                    "E. Exist to Main Menu\n"+
                    "-------------------------------------------------------------------------\n";

            String viewAllTemplatesMenu =
                    "-------------------------------------------------------------------------\n"+
                            "Options for viewing all templates -- Please choose the letter associated to the option\n" +
                            "A. Summary view \n" +
                            "B. Detailed view \n" +
                            "C. Return to previous menu \n" +
                            "-------------------------------------------------------------------------\n";

             String editTemplateMenu =
                "-------------------------------------------------------------------------\n"+
                        "Options for editing a template -- Please choose the letter associated to the option\n" +
                        "A. Template name \n" +
                        "B. Template prompts \n" +
                        "C. Return to previous menu \n" +
                        "-------------------------------------------------------------------------\n";

            String editPromptMenu =
                "-------------------------------------------------------------------------\n"+
                        "Options for editing a prompt -- Please choose the letter associated to the option\n" +
                        "A. Rename prompt \n" +
                        "B. Add prompt \n" +
                        "C. Delete prompt \n" +
                        "D. Return to previous menu \n" +
                        "-------------------------------------------------------------------------\n";

            String accountMenu =
                    "-------------------------------------------------------------------------\n" +
                    "Account Menu -- Please choose the letter associated to the option\n" +
                    "A. logout\n" +
                    "B. Edit your user name\n" +
                    "C. Edit your password\n" +
                    "D. Exist to Main Menu\n";

            char mainMenuOption;
            char plannerMenuOption;
            char templateMenuOption;
            char accountMenuOption;


            do{
                System.out.println(personalInfo);
                System.out.println(mainMenu);
                mainMenuOption = scanner.next().charAt(0);


                switch (mainMenuOption){
                    case 'A': // user select planner
//                        "Planner Menu -- Please choose the letter associated to the option\n" +
//                        "A. View all personal planners \n" +
//                        "B. Create a new Planner from template \n" +
//                        "C. Edit a current planner \n" +
//                        "D. Delete a current planner \n" +
//                        "E. Exit to Main Menu\n"
                        do{
                            System.out.println(plannerMenu);
                            plannerMenuOption = scanner.next().charAt(0);
                            switch (plannerMenuOption){
                                case 'A':
                                    // TODO: print user's planner
                                    System.out.println("PlannerA");
                                    break;
                                case 'B':
                                    // TODO: create a new planner from template
                                    System.out.println("PlannerB");
                                    break;
                                case 'C':
                                    // TODO: edit planner
                                    System.out.println("PlannerC");
                                    break;
                                case 'D':
                                    // TODO: delete planner
                                    System.out.println("PlannerD");
                                    break;
                                case 'E':
                                    System.out.println("Returning to Main Menu...");
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid Option! Please enter again. ");
                                    break;

                            }
                        }while(plannerMenuOption != 'E');
                        // return to main menu
                        break;

                    case 'B':  // user select template
//                        "A. View all templates\n" +
//                        "B. Create a new template (Admin Only) \n" +
//                        "C. Edit a current template (Admin Only) \n" +
//                        "D. Delete a current template (Admin Only) \n" +
//                        "E. Exist to Main Menu\n"
                        do{
                            System.out.println(templateMenu);
                            templateMenuOption= scanner.next().charAt(0);
                            switch (templateMenuOption) {
                                case 'A':
                                    char viewAllTemplatesOption;
                                    do {
                                        System.out.println(viewAllTemplatesMenu);
                                        viewAllTemplatesOption = scanner.next().charAt(0);
                                        switch (viewAllTemplatesOption) {
                                            case 'A':
                                                System.out.println(tc.previewAllTemplates());
                                                break;

                                            case 'B':
                                                System.out.println(tc.detailViewAllTemplates());
                                                break;

                                            case 'C':
                                                System.out.println("Returning to previous menu...");
                                                try {
                                                    TimeUnit.SECONDS.sleep(1);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                break;

                                            default:
                                                System.out.println("Invalid option! Please enter again. ");
                                                break;
                                        }
                                    } while (viewAllTemplatesOption != 'C');
                                    break;
                                case 'B':
                                    System.out.println("Feature currently not available. Check back later!");
                                    // Just putting a place holder for now - this feature is not required in phase 1
                                    break;
                                case 'C':
                                    String username = "abc";
                                    // TODO: update this placeholder for username - currently throwing NullPointerException
                                    // TODO: after fixing username, add do{}while() to allow for multiple edits
                                    //  (allows user to stay in the same menu after they've chosen an option and the
                                    //  activity in that chosen option is done)
                                    if (ac.isAdmin(username).equals("admin")) {
                                        System.out.println(
                                                "Which template would you like to edit? Enter the template ID");
                                        int templateID = scanner.nextInt();

                                        System.out.println("This is the what the template currently looks like:\n");
                                        System.out.println(tc.detailViewTemplate(templateID));

                                        // print options for editing template - name or prompts
                                        System.out.println(editTemplateMenu);
                                        char editTemplateOption = scanner.next().charAt(0);
                                        switch (editTemplateOption) {
                                            case 'A': // choose to edit template name
                                                System.out.println("Enter new template name");
                                                String newTemplateName = scanner.nextLine();
                                                tc.editTemplateName(templateID, newTemplateName);

                                            case 'B': // choose to edit prompts
                                                System.out.println("Here are the current prompts: \n");
                                                System.out.println(tc.detailViewTemplate(templateID));
                                                System.out.println(editPromptMenu); //print options for editing a prompt
                                                char editPromptOption = scanner.next().charAt(0);
                                                switch (editPromptOption) {
                                                    case 'A': // choose to rename prompt
                                                        System.out.println(
                                                                "Enter the ID of the prompt you'd like to rename");
                                                        int promptID = scanner.nextInt();
                                                        System.out.println("Enter the desired new name for the prompt");
                                                        String newPromptName = scanner.nextLine();
                                                        tc.renameTemplatePrompt(templateID, promptID, newPromptName);

                                                    case 'B': // choose to add prompt
                                                        System.out.println(
                                                                "Would you like to add a new prompt to the end of prompts? " +
                                                                        "(yes/no)");
                                                        String addToLastPrompt = scanner.nextLine();
                                                        switch (addToLastPrompt) {
                                                            case "yes":
                                                                System.out.println("Enter the name for the new prompt");
                                                                String nameForNewPrompt = scanner.nextLine();
                                                                tc.addTemplatePrompt(templateID, nameForNewPrompt);

                                                            case "no":
                                                                System.out.println(
                                                                        "Enter the desired ID for the new prompt");
                                                                int idForNewPrompt = scanner.nextInt();
                                                                System.out.println("Enter the name for the new prompt");
                                                                nameForNewPrompt = scanner.nextLine();
                                                                tc.addTemplatePrompt(templateID,
                                                                        idForNewPrompt, nameForNewPrompt);
                                                        }

                                                    case 'C': // choose to delete prompt
                                                        System.out.println(
                                                                "Enter the ID of the prompt you'd like to delete");
                                                        int promptIDToDelete = scanner.nextInt();
                                                        tc.removeTemplatePrompt(templateID, promptIDToDelete);
                                                }

                                        }
                                        System.out.println("Edit successful!");
                                    }
                                    break;
                                case 'D':
                                    System.out.println("Feature currently not available. Check back later!");
                                    // Just putting a place holder for now - this feature is not required in phase 1
                                    break;
                                case 'E':
                                    System.out.println("Returning to Main Menu...");
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid Option! Please enter again. ");
                                    break;
                            }
                        }while(templateMenuOption != 'E');
                        // return to main menu
                        break;

                    case 'C': // user select account
//                        "A. logout\n" +
//                        "B. Edit your user name\n" +
//                        "C. Edit your password\n" +
//                        "D. Exist to Main Menu\n";

                        do{
                            System.out.println(accountMenu);
                            accountMenuOption = scanner.next().charAt(0);
                            switch (accountMenuOption){
                                case 'A':
                                    ac.logOut(retriever);
                                    break;
                                case 'B':
                                    System.out.println("Please enter your new user name:");
                                    String newName = scanner.nextLine();
                                    ac.changeUserName(retriever, newName);
                                    break;
                                case 'C':
                                    System.out.println("Please enter your original password:");
                                    String oldPassword = scanner.nextLine();
                                    System.out.println("Please enter your new password:");
                                    String newPassword = scanner.nextLine();
                                    ac.changePassword(retriever, oldPassword, newPassword);
                                    break;
                                case 'D':
                                    System.out.println("Returning to Main Menu...");
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid Option! Please enter again! ");
                                    break;
                            }
                        }while(accountMenuOption != 'E');
                        // return to main menu
                        break;

                    case 'D':
                        break;

                    default:
                        System.out.println("Invalid Option! Please enter again. ");
                        break;
                }
            }while (mainMenuOption != 'D');
        System.out.println("You are logged out, see you next time!");

        return null;
    }
}
