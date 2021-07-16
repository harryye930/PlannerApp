package Interface;

import Controller.AccessController;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Scanner;

public class TextUI {
    public void showMenu() {
        System.out.println("Welcome to your planner!");
        System.out.println("Do you have an existing account? (options: yes / no)");
        Scanner scanner = new Scanner(System.in);
        String existAccount = scanner.nextLine();

        switch (existAccount){
            case "no":  // the user does not have a existing account
                System.out.println("Do you like to create an account, or login as guest? " +
                        "(options: create new account / login as guest");
                String newUser = scanner.nextLine();
                switch (newUser){
                    case "create new account": // TODO
                        break;
                    case "login as guest":  // TODO
                        break;
            }
            case "yes":  // the user have exsiting account
                // authenticating username and password
                System.out.println("User Name:");
                String username = scanner.nextLine();
                System.out.println("Password:");
                String password = scanner.nextLine();
                AccessController accessController = new AccessController();
                boolean authResult = accessController.logIn(username, password);

                do{
                    System.out.println("Sorry, your username and password don't match! Please try again.");
                    System.out.println("User Name:");
                    username = scanner.nextLine();
                    System.out.println("Password:");
                    password = scanner.nextLine();
                    authResult = accessController.logIn(username, password);
                }while(!authResult);
                System.out.println("You are now logged in!");




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

            String accountMenu =
                    "-------------------------------------------------------------------------\n" +
                    "Account Menu -- Please choose the letter associated to the option\n" +
                    "A. Create a new account\n" +
                    "B. Create a new admin account\n" +
                    "C. Edit your user name\n" +
                    "D. Edit your password\n" +
                    "E. Exist to Main Menu\n";
            char mainMenuOption = '\0';
            char plannerMenuOption = '\0';
            char templateMenuOption = '\0';
            char accountMenuOption = '\0';


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
                                    break;
                                case 'B':
                                    // TODO: create a new planner from template
                                    break;
                                case 'C':
                                    // TODO: edit planner
                                    break;
                                case 'D':
                                    // TODO: delete planner
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
                        System.out.println(templateMenu);
                        do{
                            System.out.println(plannerMenu);
                            plannerMenuOption = scanner.next().charAt(0);
                            switch (plannerMenuOption){
                                case 'A':
                                    // TODO: print all templates
                                    break;
                                case 'B':
                                    // TODO: check admin and create new template
                                    break;
                                case 'C':
                                    // TODO: check admin and edit one template
                                    break;
                                case 'D':
                                    // TODO: check admin and delete one template
                                    break;
                                default:
                                    System.out.println("Invalid Option! Please enter again. ");
                                    break;
                            }
                        }while(plannerMenuOption != 'E');
                        // return to main menu
                        break;

                    case 'C': // user select account
//                        "A. Create a new account\n" +
//                        "B. Create a new admin account\n" +
//                        "C. Edit your user name\n" +
//                        "D. Edit your password\n" +
//                        "E. Exist to Main Menu\n"

                        do{
                            System.out.println(accountMenu);
                            accountMenuOption = scanner.next().charAt(0);
                            switch (accountMenuOption){
                                case 'A':
                                    // TODO: print user's planner
                                    break;
                                case 'B':
                                    // TODO: create a new planner from template
                                    break;
                                case 'C':
                                    // TODO: edit planner
                                    break;
                                case 'D':
                                    // TODO: delete planner
                                    break;
                                default:
                                    System.out.println("Invalid Option! Please enter again. ");
                                    break;
                            }
                        }while(accountMenuOption != 'E');
                        // return to main menu
                        break;

                        default:
                        System.out.println("Invalid Option! Please enter again. ");
                        break;



                }

            }while (mainMenuOption != 'D');
        System.out.println("You are logged out, see you next time.");

    }
}
