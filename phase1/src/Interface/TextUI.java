package Interface;

import Controller.AccessController;
import Controller.PlannerController;
import Controller.TemplateController;
import Entity.Planner;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TextUI {

    private AccessController ac = new AccessController();
    private TemplateController tc = new TemplateController();
    private PlannerController pc = new PlannerController();

    public TextUI showMenu() {
        this.ac = new AccessController();
        String retriever = ""; // The ID or Email of the Account we currently work on.
        String userId;
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
                break;

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
                ac.save();
                System.out.println("Please remember your ID:" + id);
                break;

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
                    "B. Create a new Daily Planner from template \n" +
                    "C. Create a new Project Planner from template \n" +
                    "D. Edit a current planner \n" +
                    "E. Delete a current planner \n" +
                    "F. Exit to Main Menu\n" +
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
//                        "B. Create a new Daily Planner from template \n" +
//                        "C. Create a new Project Planner from template \n" +
//                        "D. Edit a current planner \n" +
//                        "E. Delete a current planner \n" +
//                        "F. Exit to Main Menu\n"
                        do{
                            System.out.println(plannerMenu);
                            plannerMenuOption = scanner.next().charAt(0);
                            switch (plannerMenuOption){
                                case 'A':
                                    //System.out.println(ac.getPlanners(retriever));
                                    ArrayList<String> planners = ac.getPlanners(retriever);
                                    for (String planner: planners) {
                                        System.out.println(pc.toString(planner));
                                    }
                                    break;
                                case 'B':
                                    System.out.println("Successfully created Daily Planner, " +
                                            "these are the information: \n" + pc.createNewDailyPlanner());
                                    break;
                                case 'C':
                                    System.out.println("Successfully created Project Planner, " +
                                            "these are the information: \n" + pc.createNewProjectPlanner());
                                    break;
                                case 'D':
                                    // TODO: edit planner
                                    System.out.println("Please enter the Planner ID you want to change:");
                                    String plannerId = scanner.next();
                                    break;
                                case 'E':
                                    System.out.println("Please enter the Planner ID you want to delete.");
                                    String DeleteID = scanner.nextLine();
                                    pc.DeletePlanner(DeleteID);
                                    System.out.println("Successfully delete");
                                    break;
                                case 'F':
                                    System.out.println("Returning to Main Menu...");
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    System.out.println("Invpackage Interface;\n" +
                                            "\n" +
                                            "import Controller.AccessController;\n" +
                                            "import Controller.PlannerController;\n" +
                                            "import Controller.TemplateController;\n" +
                                            "import Entity.Planner;\n" +
                                            "\n" +
                                            "import java.util.ArrayList;\n" +
                                            "import java.util.Scanner;\n" +
                                            "import java.util.concurrent.TimeUnit;\n" +
                                            "\n" +
                                            "public class TextUI {\n" +
                                            "\n" +
                                            "    private AccessController ac = new AccessController();\n" +
                                            "    private TemplateController tc = new TemplateController();\n" +
                                            "    private PlannerController pc = new PlannerController();\n" +
                                            "\n" +
                                            "    public TextUI showMenu() {\n" +
                                            "        this.ac = new AccessController();\n" +
                                            "        String retriever = \"\"; // The ID or Email of the Account we currently work on.\n" +
                                            "        String userId;\n" +
                                            "        this.ac.load();\n" +
                                            "        System.out.println(\"Welcome to your planner!\");\n" +
                                            "        System.out.println(\"What do you want to do (login / create new account / login as guest)\");\n" +
                                            "        Scanner scanner = new Scanner(System.in);\n" +
                                            "        String existAccount = scanner.nextLine();\n" +
                                            "\n" +
                                            "        switch (existAccount){\n" +
                                            "            case \"login\":\n" +
                                            "                System.out.println(\"Please enter your ID or Email:\");\n" +
                                            "                String userRetriever = scanner.nextLine();\n" +
                                            "                System.out.println(\"Please enter your password:\");\n" +
                                            "                String userPassWord = scanner.nextLine();\n" +
                                            "                if (this.ac.logIn(userRetriever, userPassWord)) {\n" +
                                            "                    System.out.println(\"Login success.\");\n" +
                                            "                    retriever = userRetriever;\n" +
                                            "                } else {\n" +
                                            "                    System.out.println(\"Invalid input, please try again.\");\n" +
                                            "                }\n" +
                                            "                break;\n" +
                                            "\n" +
                                            "            case \"create new account\":\n" +
                                            "                System.out.println(\"Email:\");\n" +
                                            "                String email = scanner.nextLine();\n" +
                                            "                System.out.println(\"User Name:\");\n" +
                                            "                String username = scanner.nextLine();\n" +
                                            "                System.out.println(\"Password:\");\n" +
                                            "                String password = scanner.nextLine();\n" +
                                            "                System.out.println(\"Please enter your password again:\");\n" +
                                            "                String tPassword = scanner.nextLine();\n" +
                                            "                assert tPassword.equals(password);\n" +
                                            "                String id = ac.createAccount(email, username, password);\n" +
                                            "                ac.save();\n" +
                                            "                System.out.println(\"Please remember your ID:\" + id);\n" +
                                            "                break;\n" +
                                            "\n" +
                                            "//                do{\n" +
                                            "//                    System.out.println(\"Sorry, your username and password don't match! Please try again.\");\n" +
                                            "//                    System.out.println(\"User Name:\");\n" +
                                            "//                    username = scanner.nextLine();\n" +
                                            "//                    System.out.println(\"Password:\");\n" +
                                            "//                    password = scanner.nextLine();\n" +
                                            "//                    authResult = accessController.logIn(username, password);\n" +
                                            "//                    System.out.println(\"\");\n" +
                                            "//                }while(!authResult);\n" +
                                            "\n" +
                                            "\n" +
                                            "\n" +
                                            "\n" +
                                            "\n" +
                                            "        }\n" +
                                            "            // Create menu\n" +
                                            "            String mainMenu = \"=========================================================================\\n\" +\n" +
                                            "                    \"Main Menu -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                    \"A. Planners Options\\n\" +\n" +
                                            "                    \"B. Templates Options\\n\" +\n" +
                                            "                    \"C. Account Options\\n\" +\n" +
                                            "                    \"D. Log out\\n\" +\n" +
                                            "                    \"=========================================================================\";\n" +
                                            "\n" +
                                            "            String personalInfo = \"You are logged in as TODO: UserName\";\n" +
                                            "            String plannerMenu =\n" +
                                            "                    \"-------------------------------------------------------------------------\\n\" +\n" +
                                            "                    \"Planner Menu -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                    \"A. View all personal planners \\n\" +\n" +
                                            "                    \"B. Create a new Daily Planner from template \\n\" +\n" +
                                            "                    \"C. Create a new Project Planner from template \\n\" +\n" +
                                            "                    \"D. Edit a current planner \\n\" +\n" +
                                            "                    \"E. Delete a current planner \\n\" +\n" +
                                            "                    \"F. Exit to Main Menu\\n\" +\n" +
                                            "                    \"-------------------------------------------------------------------------\\n\";\n" +
                                            "\n" +
                                            "            String templateMenu =\n" +
                                            "                    \"-------------------------------------------------------------------------\\n\"+\n" +
                                            "                    \"Template Menu -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                    \"A. View all templates\\n\" +\n" +
                                            "                    \"B. Create a new template (Admin Only) \\n\" +\n" +
                                            "                    \"C. Edit a current template (Admin Only) \\n\" +\n" +
                                            "                    \"D. Delete a current template (Admin Only) \\n\" +\n" +
                                            "                    \"E. Exist to Main Menu\\n\"+\n" +
                                            "                    \"-------------------------------------------------------------------------\\n\";\n" +
                                            "\n" +
                                            "            String viewAllTemplatesMenu =\n" +
                                            "                    \"-------------------------------------------------------------------------\\n\"+\n" +
                                            "                            \"Options for viewing all templates -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                            \"A. Summary view \\n\" +\n" +
                                            "                            \"B. Detailed view \\n\" +\n" +
                                            "                            \"C. Return to previous menu \\n\" +\n" +
                                            "                            \"-------------------------------------------------------------------------\\n\";\n" +
                                            "\n" +
                                            "             String editTemplateMenu =\n" +
                                            "                \"-------------------------------------------------------------------------\\n\"+\n" +
                                            "                        \"Options for editing a template -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                        \"A. Template name \\n\" +\n" +
                                            "                        \"B. Template prompts \\n\" +\n" +
                                            "                        \"C. Return to previous menu \\n\" +\n" +
                                            "                        \"-------------------------------------------------------------------------\\n\";\n" +
                                            "\n" +
                                            "            String editPromptMenu =\n" +
                                            "                \"-------------------------------------------------------------------------\\n\"+\n" +
                                            "                        \"Options for editing a prompt -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                        \"A. Rename prompt \\n\" +\n" +
                                            "                        \"B. Add prompt \\n\" +\n" +
                                            "                        \"C. Delete prompt \\n\" +\n" +
                                            "                        \"D. Return to previous menu \\n\" +\n" +
                                            "                        \"-------------------------------------------------------------------------\\n\";\n" +
                                            "\n" +
                                            "            String accountMenu =\n" +
                                            "                    \"-------------------------------------------------------------------------\\n\" +\n" +
                                            "                    \"Account Menu -- Please choose the letter associated to the option\\n\" +\n" +
                                            "                    \"A. logout\\n\" +\n" +
                                            "                    \"B. Edit your user name\\n\" +\n" +
                                            "                    \"C. Edit your password\\n\" +\n" +
                                            "                    \"D. Exist to Main Menu\\n\";\n" +
                                            "\n" +
                                            "            char mainMenuOption;\n" +
                                            "            char plannerMenuOption;\n" +
                                            "            char templateMenuOption;\n" +
                                            "            char accountMenuOption;\n" +
                                            "\n" +
                                            "\n" +
                                            "            do{\n" +
                                            "                System.out.println(personalInfo);\n" +
                                            "                System.out.println(mainMenu);\n" +
                                            "                mainMenuOption = scanner.next().charAt(0);\n" +
                                            "\n" +
                                            "\n" +
                                            "                switch (mainMenuOption){\n" +
                                            "                    case 'A': // user select planner\n" +
                                            "//                        \"Planner Menu -- Please choose the letter associated to the option\\n\" +\n" +
                                            "//                        \"A. View all personal planners \\n\" +\n" +
                                            "//                        \"B. Create a new Daily Planner from template \\n\" +\n" +
                                            "//                        \"C. Create a new Project Planner from template \\n\" +\n" +
                                            "//                        \"D. Edit a current planner \\n\" +\n" +
                                            "//                        \"E. Delete a current planner \\n\" +\n" +
                                            "//                        \"F. Exit to Main Menu\\n\"\n" +
                                            "                        do{\n" +
                                            "                            System.out.println(plannerMenu);\n" +
                                            "                            plannerMenuOption = scanner.next().charAt(0);\n" +
                                            "                            switch (plannerMenuOption){\n" +
                                            "                                case 'A':\n" +
                                            "                                    ArrayList<Planner> planners = new ArrayList<>(ac.getPlanners(retriever));\n" +
                                            "                                    for (Planner planner: planners) {\n" +
                                            "                                        System.out.println(planner.toString());\n" +
                                            "                                    }\n" +
                                            "                                    break;\n" +
                                            "                                case 'B':\n" +
                                            "                                    System.out.println(\"Successfully created Daily Planner, \" +\n" +
                                            "                                            \"these are the information: \\n\" + pc.createNewDailyPlanner());\n" +
                                            "                                    break;\n" +
                                            "                                case 'C':\n" +
                                            "                                    System.out.println(\"Successfully created Project Planner, \" +\n" +
                                            "                                            \"these are the information: \\n\" + pc.createNewProjectPlanner());\n" +
                                            "                                    break;\n" +
                                            "                                case 'D':\n" +
                                            "                                    // TODO: edit planner\n" +
                                            "                                    System.out.println(\"Please enter the Planner ID you want to change:\");\n" +
                                            "                                    String plannerId = scanner.next();\n" +
                                            "                                    break;\n" +
                                            "                                case 'E':\n" +
                                            "                                    System.out.println(\"Please enter the Planner ID you want to delete.\");\n" +
                                            "                                    String DeleteID = scanner.nextLine();\n" +
                                            "                                    pc.DeletePlanner(DeleteID);\n" +
                                            "                                    System.out.println(\"Successfully delete\");\n" +
                                            "                                    break;\n" +
                                            "                                case 'F':\n" +
                                            "                                    System.out.println(\"Returning to Main Menu...\");\n" +
                                            "                                    try {\n" +
                                            "                                        TimeUnit.SECONDS.sleep(1);\n" +
                                            "                                    } catch (InterruptedException e) {\n" +
                                            "                                        e.printStackTrace();\n" +
                                            "                                    }\n" +
                                            "                                    break;\n" +
                                            "                                default:\n" +
                                            "                                    System.out.println(\"Invalid Option! Please enter again. \");\n" +
                                            "                                    break;\n" +
                                            "\n" +
                                            "                            }\n" +
                                            "                        }while(plannerMenuOption != 'E');\n" +
                                            "                        // return to main menu\n" +
                                            "                        break;\n" +
                                            "\n" +
                                            "                    case 'B':  // user select template\n" +
                                            "//                        \"A. View all templates\\n\" +\n" +
                                            "//                        \"B. Create a new template (Admin Only) \\n\" +\n" +
                                            "//                        \"C. Edit a current template (Admin Only) \\n\" +\n" +
                                            "//                        \"D. Delete a current template (Admin Only) \\n\" +\n" +
                                            "//                        \"E. Exist to Main Menu\\n\"\n" +
                                            "                        do{\n" +
                                            "                            System.out.println(templateMenu);\n" +
                                            "                            templateMenuOption= scanner.next().charAt(0);\n" +
                                            "                            switch (templateMenuOption) {\n" +
                                            "                                case 'A':\n" +
                                            "                                    char viewAllTemplatesOption;\n" +
                                            "                                    do {\n" +
                                            "                                        System.out.println(viewAllTemplatesMenu);\n" +
                                            "                                        viewAllTemplatesOption = scanner.next().charAt(0);\n" +
                                            "                                        switch (viewAllTemplatesOption) {\n" +
                                            "                                            case 'A':\n" +
                                            "                                                System.out.println(tc.previewAllTemplates());\n" +
                                            "                                                break;\n" +
                                            "\n" +
                                            "                                            case 'B':\n" +
                                            "                                                System.out.println(tc.detailViewAllTemplates());\n" +
                                            "                                                break;\n" +
                                            "\n" +
                                            "                                            case 'C':\n" +
                                            "                                                System.out.println(\"Returning to previous menu...\");\n" +
                                            "                                                try {\n" +
                                            "                                                    TimeUnit.SECONDS.sleep(1);\n" +
                                            "                                                } catch (InterruptedException e) {\n" +
                                            "                                                    e.printStackTrace();\n" +
                                            "                                                }\n" +
                                            "                                                break;\n" +
                                            "\n" +
                                            "                                            default:\n" +
                                            "                                                System.out.println(\"Invalid option! Please enter again. \");\n" +
                                            "                                                break;\n" +
                                            "                                        }\n" +
                                            "                                    } while (viewAllTemplatesOption != 'C');\n" +
                                            "                                    break;\n" +
                                            "                                case 'B':\n" +
                                            "                                    System.out.println(\"Feature currently not available. Check back later!\");\n" +
                                            "                                    // Just putting a place holder for now - this feature is not required in phase 1\n" +
                                            "                                    break;\n" +
                                            "                                case 'C':\n" +
                                            "                                    String username = \"abc\";\n" +
                                            "                                    // TODO: update this placeholder for username - currently throwing NullPointerException\n" +
                                            "                                    // TODO: after fixing username, add do{}while() to allow for multiple edits\n" +
                                            "                                    //  (allows user to stay in the same menu after they've chosen an option and the\n" +
                                            "                                    //  activity in that chosen option is done)\n" +
                                            "                                    if (ac.isAdmin(username).equals(\"admin\")) {\n" +
                                            "                                        System.out.println(\n" +
                                            "                                                \"Which template would you like to edit? Enter the template ID\");\n" +
                                            "                                        int templateID = scanner.nextInt();\n" +
                                            "\n" +
                                            "                                        System.out.println(\"This is the what the template currently looks like:\\n\");\n" +
                                            "                                        System.out.println(tc.detailViewTemplate(templateID));\n" +
                                            "\n" +
                                            "                                        // print options for editing template - name or prompts\n" +
                                            "                                        System.out.println(editTemplateMenu);\n" +
                                            "                                        char editTemplateOption = scanner.next().charAt(0);\n" +
                                            "                                        switch (editTemplateOption) {\n" +
                                            "                                            case 'A': // choose to edit template name\n" +
                                            "                                                System.out.println(\"Enter new template name\");\n" +
                                            "                                                String newTemplateName = scanner.nextLine();\n" +
                                            "                                                tc.editTemplateName(templateID, newTemplateName);\n" +
                                            "\n" +
                                            "                                            case 'B': // choose to edit prompts\n" +
                                            "                                                System.out.println(\"Here are the current prompts: \\n\");\n" +
                                            "                                                System.out.println(tc.detailViewTemplate(templateID));\n" +
                                            "                                                System.out.println(editPromptMenu); //print options for editing a prompt\n" +
                                            "                                                char editPromptOption = scanner.next().charAt(0);\n" +
                                            "                                                switch (editPromptOption) {\n" +
                                            "                                                    case 'A': // choose to rename prompt\n" +
                                            "                                                        System.out.println(\n" +
                                            "                                                                \"Enter the ID of the prompt you'd like to rename\");\n" +
                                            "                                                        int promptID = scanner.nextInt();\n" +
                                            "                                                        System.out.println(\"Enter the desired new name for the prompt\");\n" +
                                            "                                                        String newPromptName = scanner.nextLine();\n" +
                                            "                                                        tc.renameTemplatePrompt(templateID, promptID, newPromptName);\n" +
                                            "\n" +
                                            "                                                    case 'B': // choose to add prompt\n" +
                                            "                                                        System.out.println(\n" +
                                            "                                                                \"Would you like to add a new prompt to the end of prompts? \" +\n" +
                                            "                                                                        \"(yes/no)\");\n" +
                                            "                                                        String addToLastPrompt = scanner.nextLine();\n" +
                                            "                                                        switch (addToLastPrompt) {\n" +
                                            "                                                            case \"yes\":\n" +
                                            "                                                                System.out.println(\"Enter the name for the new prompt\");\n" +
                                            "                                                                String nameForNewPrompt = scanner.nextLine();\n" +
                                            "                                                                tc.addTemplatePrompt(templateID, nameForNewPrompt);\n" +
                                            "\n" +
                                            "                                                            case \"no\":\n" +
                                            "                                                                System.out.println(\n" +
                                            "                                                                        \"Enter the desired ID for the new prompt\");\n" +
                                            "                                                                int idForNewPrompt = scanner.nextInt();\n" +
                                            "                                                                System.out.println(\"Enter the name for the new prompt\");\n" +
                                            "                                                                nameForNewPrompt = scanner.nextLine();\n" +
                                            "                                                                tc.addTemplatePrompt(templateID,\n" +
                                            "                                                                        idForNewPrompt, nameForNewPrompt);\n" +
                                            "                                                        }\n" +
                                            "\n" +
                                            "                                                    case 'C': // choose to delete prompt\n" +
                                            "                                                        System.out.println(\n" +
                                            "                                                                \"Enter the ID of the prompt you'd like to delete\");\n" +
                                            "                                                        int promptIDToDelete = scanner.nextInt();\n" +
                                            "                                                        tc.removeTemplatePrompt(templateID, promptIDToDelete);\n" +
                                            "                                                }\n" +
                                            "\n" +
                                            "                                        }\n" +
                                            "                                        System.out.println(\"Edit successful!\");\n" +
                                            "                                    }\n" +
                                            "                                    break;\n" +
                                            "                                case 'D':\n" +
                                            "                                    System.out.println(\"Feature currently not available. Check back later!\");\n" +
                                            "                                    // Just putting a place holder for now - this feature is not required in phase 1\n" +
                                            "                                    break;\n" +
                                            "                                case 'E':\n" +
                                            "                                    System.out.println(\"Returning to Main Menu...\");\n" +
                                            "                                    try {\n" +
                                            "                                        TimeUnit.SECONDS.sleep(1);\n" +
                                            "                                    } catch (InterruptedException e) {\n" +
                                            "                                        e.printStackTrace();\n" +
                                            "                                    }\n" +
                                            "                                    break;\n" +
                                            "                                default:\n" +
                                            "                                    System.out.println(\"Invalid Option! Please enter again. \");\n" +
                                            "                                    break;\n" +
                                            "                            }\n" +
                                            "                        }while(templateMenuOption != 'E');\n" +
                                            "                        // return to main menu\n" +
                                            "                        break;\n" +
                                            "\n" +
                                            "                    case 'C': // user select account\n" +
                                            "//                        \"A. logout\\n\" +\n" +
                                            "//                        \"B. Edit your user name\\n\" +\n" +
                                            "//                        \"C. Edit your password\\n\" +\n" +
                                            "//                        \"D. Exist to Main Menu\\n\";\n" +
                                            "\n" +
                                            "                        do{\n" +
                                            "                            System.out.println(accountMenu);\n" +
                                            "                            accountMenuOption = scanner.next().charAt(0);\n" +
                                            "                            scanner.nextLine();\n" +
                                            "                            switch (accountMenuOption){\n" +
                                            "                                case 'A':\n" +
                                            "                                    ac.logOut(retriever);\n" +
                                            "                                    break;\n" +
                                            "                                case 'B':\n" +
                                            "                                    System.out.println(\"Please enter your new user name:\");\n" +
                                            "                                    String newName = scanner.nextLine();\n" +
                                            "                                    ac.changeUserName(retriever, newName);\n" +
                                            "                                    break;\n" +
                                            "                                case 'C':\n" +
                                            "                                    System.out.println(\"Please enter your original password:\");\n" +
                                            "                                    String oldPassword = scanner.nextLine();\n" +
                                            "                                    System.out.println(\"Please enter your new password:\");\n" +
                                            "                                    String newPassword = scanner.nextLine();\n" +
                                            "                                    ac.changePassword(retriever, oldPassword, newPassword);\n" +
                                            "                                    break;\n" +
                                            "                                case 'D':\n" +
                                            "                                    System.out.println(\"Returning to Main Menu...\");\n" +
                                            "                                    try {\n" +
                                            "                                        TimeUnit.SECONDS.sleep(1);\n" +
                                            "                                    } catch (InterruptedException e) {\n" +
                                            "                                        e.printStackTrace();\n" +
                                            "                                    }\n" +
                                            "                                    break;\n" +
                                            "                                default:\n" +
                                            "                                    System.out.println(\"Invalid Option! Please enter again! \");\n" +
                                            "                                    break;\n" +
                                            "                            }\n" +
                                            "                        }while(accountMenuOption != 'E');\n" +
                                            "                        // return to main menu\n" +
                                            "                        break;\n" +
                                            "\n" +
                                            "                    case 'D':\n" +
                                            "                        break;\n" +
                                            "\n" +
                                            "                    default:\n" +
                                            "                        System.out.println(\"Invalid Option! Please enter again. \");\n" +
                                            "                        break;\n" +
                                            "                }\n" +
                                            "            }while (mainMenuOption != 'D');\n" +
                                            "        System.out.println(\"You are logged out, see you next time!\");\n" +
                                            "\n" +
                                            "        return null;\n" +
                                            "    }\n" +
                                            "}\nalid Option! Please enter again. ");
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
                            scanner.nextLine();
                            switch (accountMenuOption){
                                case 'A':
                                    ac.logOut(retriever);
                                    accountMenuOption = 'D';
                                    mainMenuOption = 'D';
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
                                    if (ac.changePassword(retriever, oldPassword, newPassword)) {
                                        System.out.println("Reset success.");
                                    } else {
                                        System.out.println("Invalid password.");
                                    }
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
                        }while(accountMenuOption != 'D');
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
