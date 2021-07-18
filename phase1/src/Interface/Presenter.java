package Interface;

public class Presenter {

    /**
     * Prints out message for welcome screen at the start of the program.
     */
    public void showWelcomeScreen(){
        String welcomeScreen = "Welcome to your planner manager!";
        System.out.println(welcomeScreen);
    }

    /**
     * Prints out message for closing screen at the end of the program.
     */
    public void showClosingScreen(){
        String closingScreen = "Thank you for using the program, have a productive day!";
        System.out.println(closingScreen);
    }

    /**
     * Prints out log in options.
     */
    public void showLoginOptions(){
        String loginOptions = "=========================================================================\n" +
                "Select one of the options below to get started! " +
                "Please enter the letter associated with that option.\n"+
                "A. Create a new account\n" +
                "B. Log in (have an existing account)\n" +
                "C. Continue as guest (note that any changes made won't be saved)\n" +
                "D. Quit\n" +
                "=========================================================================";
        System.out.println(loginOptions);
    }

    /**
     * Prints out message showing that a new account has been created.
     */
    public void showAccountCreatedScreen(){
        String accountCreatedScreen = "A new account has been created.";
        System.out.println(accountCreatedScreen);
    }

    /**
     * Prints out message showing that user has successfully logged in.
     */
    public void showLoginSuccessfulScreen(){
        String loginSuccessfulScreen = "Login successful.";
        System.out.println(loginSuccessfulScreen);
    }

    /**
     * Prints out main menu after user is logged in.
     */
    public void showMainMenu(){
        String mainMenu = "=========================================================================\n" +
                "Here is the main menu. " +
                "Please choose one of the options below and enter the letter associated with it\n" +
                "A. Planners options\n" +
                "B. Templates options\n" +
                "C. Account options\n" +
                "D. Log out\n" +
                "=========================================================================";
        System.out.println(mainMenu);
    }

    /**
     * Prints out message showing that the program is saving information.
     */
    public void showSavingInfoScreen(){
        String savingInfoScreen = "Saving information...";
        System.out.println(savingInfoScreen);
    }

    /**
     * Prints out message showing that information has been successfully saved in the program.
     */
    public void showSavingSuccessfulScreen(){
        String savingSuccessfulScreen = "Saving successful!";
        System.out.println(savingSuccessfulScreen);
    }



}