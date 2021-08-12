package UseCase;

import Entity.*;
import nl.flotsam.xeger.Xeger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;


/**
 * Manages Accounts.
 */
public class AccountManager implements Serializable{
    private final HashMap<String, Account> idToAccount;
    private final HashMap<String, Account> emailToAccount;

    /**
     * Create an AccountManager Object.
     */
    public AccountManager(){
        this.emailToAccount = new HashMap<>();
        this.idToAccount = new HashMap<>();
        }

    /**
     * change the userName of an account
     * @param retriever A String representing the user ID or Email.
     * @param userName the userName user enters that they want to change to
     */
    public void setUserName(String retriever, String userName){
        Account account = this.findAccount(retriever);
        account.setUserName(userName);
    }

    /***
     * Creates randomly generated password that satisfies the specified complexityLevel.
     * @param complexityLevel Desired complexity level for the password. Must be "weak" or "good", as we do not allow
     *                        users to create passwords that are too weak.
     * @return A randomly generated string that satisfies the complexityLevel.
     */
    public String generatePassword(String complexityLevel){
        // two special chars, satisfies the "at least 4 characters" requirement when combined with any two of the other
        // patterns
        String twoSpecialChars = "[?+!@#]{2}";
        // includes at least one upper case letter
        String atLeastOneUpperCase = "[A-Z]{1,3}";
        // includes at least one lower case letter
        String atLeastOneLowerCase = "[a-z]{1,3}";
        // includes at least one number
        String atLeastOneNumber = "[0-9]{1,3}";

        List<String> meetThreeCriteria =  new ArrayList<>();
        meetThreeCriteria.add(twoSpecialChars + atLeastOneUpperCase + atLeastOneLowerCase);
        meetThreeCriteria.add(twoSpecialChars + atLeastOneUpperCase + atLeastOneNumber);
        meetThreeCriteria.add(twoSpecialChars + atLeastOneLowerCase + atLeastOneNumber);
        meetThreeCriteria.add(atLeastOneUpperCase + atLeastOneLowerCase + atLeastOneNumber);

        List<String> meetFourCriteria = new ArrayList<>();
        meetFourCriteria.add(twoSpecialChars + atLeastOneUpperCase + atLeastOneLowerCase + atLeastOneNumber);

        List<String> chosenList = null;
        if (complexityLevel.equals("weak")) {
            chosenList = meetThreeCriteria;
        } else if (complexityLevel.equals("good")) {
            chosenList = meetFourCriteria;
        }

        Collections.shuffle(chosenList);
        String selectedPattern = chosenList.get(0);

        Xeger generator = new Xeger(selectedPattern);
        String generatedString = generator.generate(); // the randomly created password
        return shuffleCharacters(generatedString); // randomly shuffle the characters in generatedString

    }

    /**
     * Randomly shuffles the characters in input to create and return a reordered string.
     * @param input Input string whose characters will be shuffled.
     * @return A string with shuffled characters from input string.
     */
    private String shuffleCharacters(String input){
        List<Character> list = new ArrayList<>();
        for(char c : input.toCharArray())
            list.add(c);
        Collections.shuffle(list);

        StringBuilder stringBuilder = new StringBuilder();
        for(char c : list)
            stringBuilder.append(c);

        return stringBuilder.toString();
    }

    // set password for new account
    // - check that the password is complicated enough
    // update password for existing account
    // - sends a temporary password that the user can use to log back into the system --- controller
    // - check that the new password is different from the old password
    // - check that the password is complicated enough

    /***
     * Sets the password if the password meets the complexity criteria.
     * @param retriever A string representing the user ID or email.
     * @param password The password user wants to set for the account.
     * @return Boolean value indicating if the password is successfully set. If false, then the password provided is
     * not complex enough and therefore not set; if true, the password is complex enough and is set successfully.
     */
    public boolean setPassword(String retriever, String password){
        Account account = this.findAccount(retriever);
        if (checkPasswordComplexity(password)){
            account.setPassword(password);
            return true;
        } else {
            return false;
        }
    }

    /***
     * Returns true if the password is complex enough, i.e., the complexity level is NOT Too Weak.
     * Returns false if the password's complexity level is Too Weak.
     * @param password Password to check for complexity.
     * @return A boolean value indicating if the password satisfies the complexity requirement.
     */
    public boolean checkPasswordComplexity(String password){
        return !getPasswordComplexityLevel(getNumberOfCriteriaMet(password)).equals("Too Weak");
        //return getPasswordComplexityLevel(getNumberOfCriteriaMet(password));
    }

    /**
     * Returns the number of password criteria met by the provided password.
     * Password criteria include:
     * (1) at least 4 characters long,
     * (2) must include at least one upper case letter,
     * (3) must include at least one lower case letter, and
     * (4) must include at least one number.
     * @param password is the password to be checked whether it meets the criteria or not.
     * @return An integer indicating how many of the criteria are met by the password.
     */
    public int getNumberOfCriteriaMet(String password){
        String atLeastFour = ".{4,}";
        String atLeastOneUpper = ".*[A-Z].*";
        String atLeastOneLower = ".*[a-z].*";
        String atLeastOneNumber = ".*[0-9].*";

        int numberOfCriteriaMet = 0;

        if (Pattern.matches(atLeastFour, password)) {
            numberOfCriteriaMet++;
        }
        if (Pattern.matches(atLeastOneUpper, password)) {
            numberOfCriteriaMet++;
        }
        if (Pattern.matches(atLeastOneLower, password)) {
            numberOfCriteriaMet++;
        }
        if (Pattern.matches(atLeastOneNumber, password)) {
            numberOfCriteriaMet++;
        }
        return numberOfCriteriaMet;
    }

    /**
     * Returns complexity level (i.e., too weak, weak, or good) depending on the given number of criteria met
     * by a password.
     * Too weak: two or less criteria met
     * Weak: three criteria met
     * Good: all four criteria met
     * @param numberOfCriteriaMet number of criteria met by a password.
     * @return A string that's the complexity level of the password.
     */
    public String getPasswordComplexityLevel(int numberOfCriteriaMet) {
        if (numberOfCriteriaMet <= 2) {
            return "Too Weak";
        } else if (numberOfCriteriaMet <= 3) {
            return "Weak";
        } else { // All the criteria are met.
            return "Good";
        }
    }

    /**
     * Returns a random number between min and max, inclusive.
     * @param min A lowest number that can be generated.
     * @param max A highest number that can be generated.
     * @return An integer that is generated at random between min and max, both inclusive.
     */
    private int randomNumberGenerator(int min, int max) {
        Random randomNumberGenerator = new Random();
        return randomNumberGenerator.nextInt(max - min + 1) + min;
    }

    /**
     * Returns a random upper case or lower case letter.
     * @param isUpper A boolean indicating whether it returns an upper case letter or lower case letter.
     * @return A String that's a random upper case or lower case letter.
     */
    private String randomAlphabet(boolean isUpper) {
        int letterASCII; // ASCII code
        int randomAlphabet = randomNumberGenerator(1, 26);
        if (isUpper) {
            // ASCII code for A-Z is 65 to 90, inclusive
            letterASCII = randomAlphabet + 64;
        } else {
            // ASCII code for a-z is 97 to 122, inclusive
            letterASCII = randomAlphabet + 96;
        }
        return Character.toString((char)letterASCII);
    }

    /**
     * Returns a random integer between 0-9 (inclusive).
     * @return A String that's a random integer between 0-9 (inclusive).
     */
    private String randomNumber() {
        int randomNumber = randomNumberGenerator(0, 9);
        return Integer.toString(randomNumber);
    }

    /**
     * Returns a random special characters from the following:
     */
    private String randomSpecial() {
        String[] randomCharacters = {"!", "@", "#", "$", "%", "&"};
        int randomIndex = randomNumberGenerator(0, 5);
        return randomCharacters[randomIndex];
    }

    /**
     * Returns random String from the following options:
     * (1) An upper case
     * (2) A lower case
     * (3) An integer between 0 and 9, inclusive
     * (4) A special character (i.e., !, @, #, $, %, or &)
     * @return A String that's a randomly generated an upper case, lower case, integer or special character.
     */
    private String generateRandomCharacter() {
        String randomString;
        int randomCriterion = randomNumberGenerator(1, 4); // Random number between 1 - 4
        if (randomCriterion == 1) {
            randomString = randomAlphabet(true); // Upper case
        } else if (randomCriterion == 2) {
            randomString = randomAlphabet(false); // Lower case
        } else if (randomCriterion == 3) {
            randomString = randomNumber();
        } else {
            randomString = randomSpecial();
        }
        return randomString;
    }

    /**
     * Returns true if it meets a given criterion.
     * Precondition: Criterion must be between 2-4; otherwise, returns false
     * @param s A string that is being checked.
     * @param criterionNumber A criterion it is checking for.
     * @return A boolean which indicates whether the provided string matches the given criterion or not.
     */
    public boolean matchesCriteria(String s, int criterionNumber) {
        String atLeastOneUpper = ".*[A-Z].*";
        String atLeastOneLower = ".*[a-z].*";
        String atLeastOneNumber = ".*[0-9].*";

        if (criterionNumber == 2) {
            return Pattern.matches(atLeastOneUpper, s);
        } else if (criterionNumber == 3) {
            return Pattern.matches(atLeastOneLower, s);
        } else if (criterionNumber == 4) {
            return Pattern.matches(atLeastOneNumber, s);
        } else {
            return false;
        }
    }

    /**
     * Returns a good random password that fits following password criteria.
     * Password criteria include:
     * (1) at least 4 characters long,
     * (2) must include at least one upper case letter,
     * (3) must include at least one lower case letter, and
     * (4) must include at least one number.
     */
    public String goodRandomPasswordGenerator() {
        StringBuilder randomPassword = new StringBuilder();
        ArrayList<String> temporaryRandomPassword = new ArrayList<>();

        // First, randomly select password length between 4 and 8 and fulfill the remaining criterion.
        int passwordLength = randomNumberGenerator(4, 8);  // Criteria 1 - length
        temporaryRandomPassword.add(randomAlphabet(true));  // Criteria 2 - upper case
        temporaryRandomPassword.add(randomAlphabet(false)); // Criteria 3 - lower case
        temporaryRandomPassword.add(randomNumber());  // Criteria 4 - number
        // We know that all criteria for the good password is met. Now generate random upper character, lower character,
        // number, or a special character to meet the pre-generated random length of the password.
        String randomString;
        for (int i = 3; i < passwordLength; i++) {
            // Randomly select from either upper or lower character, number, or special character.
            randomString = generateRandomCharacter();
            temporaryRandomPassword.add(randomString);
        }
        // Randomly shuffle the order
        Collections.shuffle((temporaryRandomPassword));

        // Finally, build the randomPassword
        for (String s: temporaryRandomPassword) {
            randomPassword.append(s);
        }
        return randomPassword.toString();
    }

    /**
     * Returns a weak random password that meets 3 of the following criteria.
     * Password criteria include:
     * (1) at least 4 characters long,
     * (2) must include at least one upper case letter,
     * (3) must include at least one lower case letter, and
     * (4) must include at least one number.
     * @return weakPassword A String that's a weak password.
     */
    public String weakRandomPasswordGenerator() {
        StringBuilder randomPassword = new StringBuilder();
        ArrayList<String> temporaryRandomPassword = new ArrayList<>();
        int passwordLength;

        int randomCriterionToBeNotMet = randomNumberGenerator(1, 4);
        if (randomCriterionToBeNotMet == 1) {
            passwordLength = 3;
            temporaryRandomPassword.add(randomAlphabet(true));
            temporaryRandomPassword.add(randomAlphabet(false));
            temporaryRandomPassword.add(randomNumber());
        } else {
            passwordLength = randomNumberGenerator(4, 8);
            if (randomCriterionToBeNotMet != 2) {
                temporaryRandomPassword.add(randomAlphabet(true));
            }
            if (randomCriterionToBeNotMet != 3) {
                temporaryRandomPassword.add(randomAlphabet(false));
            }
            if (randomCriterionToBeNotMet != 4) {
                temporaryRandomPassword.add(randomNumber());
            }
        }
        // We know that all randomly selected criteria are met. Now generate random upper character, lower character,
        // number, or a special character to meet the pre-generated random length of the password.
        String randomString;
        int currentLength = temporaryRandomPassword.size();
        for (int i = currentLength; i < passwordLength; i++) {
            // Randomly select from either upper or lower character, number, or special character.
            do {
                randomString = generateRandomCharacter();
            } while (matchesCriteria(randomString, randomCriterionToBeNotMet));
            temporaryRandomPassword.add(randomString);
        }
        // Randomly shuffle the order
        Collections.shuffle((temporaryRandomPassword));

        // Finally, build the randomPassword
        for (String s: temporaryRandomPassword) {
            randomPassword.append(s);
        }
        return randomPassword.toString();
    }

    /**
     * Returns either a good or weak random password. A password is determined good or weak based on the following
     * password criteria:
     * (1) at least 4 characters long,
     * (2) must include at least one upper case letter,
     * (3) must include at least one lower case letter, and
     * (4) must include at least one number.
     * @param isGood A boolean indicating whether a randomly generated password should be good or weak.
     * @return A String that's a randomly generated password that is either good or weak.
     */
    public String randomPasswordGenerator(boolean isGood) {
        StringBuilder randomPassword = new StringBuilder();
        ArrayList<String> temporaryRandomPassword = new ArrayList<>();
        int passwordLength, randomCriterionToBeNotMet;

        // First, if the password is weak then a criterion that should not be meet is selected at random; otherwise,
        // all criteria should be met.
        if (isGood) {
            randomCriterionToBeNotMet = 0;  // Non-existing criterion (i.e., all criteria should be met)
        } else {
            randomCriterionToBeNotMet = randomNumberGenerator(1, 4);
        }
        // Meet the necessary password criteria.
        if (randomCriterionToBeNotMet == 1) {
            passwordLength = 3;
            temporaryRandomPassword.add(randomAlphabet(true));
            temporaryRandomPassword.add(randomAlphabet(false));
            temporaryRandomPassword.add(randomNumber());
        } else {
            passwordLength = randomNumberGenerator(4, 8);
            if (randomCriterionToBeNotMet != 2) {
                temporaryRandomPassword.add(randomAlphabet(true));
            }
            if (randomCriterionToBeNotMet != 3) {
                temporaryRandomPassword.add(randomAlphabet(false));
            }
            if (randomCriterionToBeNotMet != 4) {
                temporaryRandomPassword.add(randomNumber());
            }
        }
        // We know that all required criteria are met. Now generate random upper character, lower character,
        // number, or a special character to meet the pre-generated random length of the password.
        String randomString;
        int currentLength = temporaryRandomPassword.size();
        for (int i = currentLength; i < passwordLength; i++) {
            // Randomly select from either upper or lower character, number, or special character.
            do {
                randomString = generateRandomCharacter();
            } while (matchesCriteria(randomString, randomCriterionToBeNotMet));
            temporaryRandomPassword.add(randomString);
        }
        // Randomly shuffle the order
        Collections.shuffle((temporaryRandomPassword));

        // Finally, build the randomPassword
        for (String s: temporaryRandomPassword) {
            randomPassword.append(s);
        }
        return randomPassword.toString();
    }

    /**
     * Add an Account object to the HashMaps.
     * @param acc A Account object we want to assign.
     */
    public void addAccount(Account acc) {
        this.idToAccount.put(acc.getUserId(), acc);
        this.emailToAccount.put(acc.getEmail(), acc);
    }

    /**
     * return whether user's entered password is same as the password of the account
     * @param retriever A String representing the user ID or Email.
     * @param enteredPassword the password that user enters
     * @return true if user's entered password is correct, false otherwise
     */
    public boolean checkPassword(String retriever, String enteredPassword){
        Account account = this.findAccount(retriever);
        return account.getPassword().equals(enteredPassword);
    }

    /**
     * find an account by email or userId. Since both email and id are String, we differentiate
     * them by the keyword "@". If the input contains @, then it is email. else, it is userId.
     * @param userInput the input from user. May be an email or an userId.
     * @return the account if the account is found, null if otherwise.
     */
    public Account findAccount(String userInput){
        Account res = emailToAccount.getOrDefault(userInput, null);
        if (res == null) {
            return idToAccount.getOrDefault(userInput, null);
        } else {
            return res;
        }
    }

    /**
     * create a regular account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    private String createRegAcc(String email){
        UserAccount newAccount = new UserAccount(email);
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a admin account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    private String createAdminAcc(String email){
        AdminAccount newAccount = new AdminAccount(email);
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a trial account. The trial account is not added to emailToAccount.
     * @return the userId of the new account.
     */
    private String createTrialAcc(){
        TrialAccount newAccount = new TrialAccount();
        newAccount.setEmail(newAccount.getUserId());
        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create a temporary account, add it to all accounts and the hashmaps.
     * @return the userId of the new account.
     */
    public String createTempAcc(String email){
        TemporaryAccount newAccount = new TemporaryAccount(email);
        LocalDateTime startDate = newAccount.getStartDate();
        long days = 30;

        newAccount.setEndDate(startDate.plusDays(days));

        this.addAccount(newAccount);
        return newAccount.getUserId();
    }

    /**
     * create an account which type is determined by user's email. if user's email contains "@admin.com",
     * we think that it would be an admin. If the user has no email (email is empty), then we create
     * trial account. Else, it would be a regular account.
     * @param email: the email that user enters
     * @return userId: the userId of the user so they can log in the future.
     */
    public String createAccount(String email){
        String userId;
        if (email.contains("@admin.com")){
            userId = createAdminAcc(email);
        } else if (email.equals("")){
            userId = createTrialAcc();
        } else {
            userId = createRegAcc(email);
        }
        return userId;
    }

    /**
     * remove an account from allAccount, emailToAccount, and idToAccount. If successfully
     * removed, return true, else return false.
     * @param retriever A String representing the user ID or Email.
     * @return true if successfully removed account, false otherwise.
     */
    public boolean removeAccount(String retriever){
        Account account = this.findAccount(retriever);
        if (this.getAllAccount().contains(account)) {
            idToAccount.remove(account.getUserId());
            emailToAccount.remove(account.getEmail());
            return true; //Return true if the account object is in the collection.
        } else {
            return false; //Return false if the account object is not in the collection.
        }
    }

    /**
     * delete a temporary account from allAccount, emailToAccount, and idToAccount
     * when the end date has passed.
     * @param retriever A String representing the user ID or Email.
     * @return true if successfully removed account, false otherwise.
     */
    public boolean deleteTempAccount(String retriever){
        TemporaryAccount account = (TemporaryAccount) this.findAccount(retriever);
        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime endDate = account.getEndDate();

        boolean isAfter = todayDate.isAfter(endDate);

        if (this.getAllAccount().contains(account) && isAfter) {
            idToAccount.remove(account.getUserId());
            emailToAccount.remove(account.getEmail());
            return true; //Return true if the account object is deleted.
        } else {
            return false; //Return false if the account object is not deleted.
        }
    }

    /**
     * return the list of all accounts
     * @return allAccount: the list that contains all accounts.
     */
    public ArrayList<Account> getAllAccount() {
        return new ArrayList<>(this.idToAccount.values());
    }

    /**
     * find the role of the account
     * @param retriever A String representing the user ID or Email.
     * @return the String that represents the role of the account ("regular", "admin", or "trial").
     */
    public String checkAccountRole(String retriever){
        Account account = this.findAccount(retriever);
        return account.getAccountType();
    }

    /**
     * Add new planner to a given account. return true if any one of the planners is added.
     * @param retriever A String representing the user ID or Email.
     * @param plannerIds An array list of Planner id that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     */
    public boolean setPlanners(String retriever, ArrayList<String > plannerIds){
        Account account = this.findAccount(retriever);
        String status = account.getAccountType();

        if (status.equals("regular") | status.equals("temporary")){
            return ((UserAccount) account).setPlanners(plannerIds);
        } else {
            return false;
        }
    }

    /**
     * Add new planner to a given account. return true if the planner is added.
     * @param retriever A String representing the user ID or Email.
     * @param plannerId An Planner id that need to be added.
     * @return A boolean value representing whether the adding is successful or not.
     */
    public boolean setPlanners(String retriever, String  plannerId){
        Account account = this.findAccount(retriever);
        String status = account.getAccountType();

        if (status.equals("regular") | status.equals("temporary")){
            return ((UserAccount) account).setPlanners(plannerId);
        } else {
            return false;
        }
    }

    /**
     * Get all planners created by the given user
     * @param retriever A String representing the User ID or Email.
     * @return An ArrayList of Planner that owned by this account, if the account is regular. Else, return
     * null.
     */
    public ArrayList<String> getPlanners(String retriever) {
        Account account = this.findAccount(retriever);
        String status = account.getAccountType();

        if (status.equals("regular") || status.equals("temporary") || status.equals("trial") ){
            return ((UserAccount) account).getPlanner();
        } else if (status.equals("admin")){
            HashSet<String> ids = new HashSet<>();
            for (String id: this.idToAccount.keySet()) {
                if (!checkAccountRole(id).equals("admin")) {
                    ids.addAll(this.getPlanners(id));
                }
            }
            return new ArrayList<>(ids);
        }
        return new ArrayList<>();
    }

    /**
     * Remove given planner from user's planners
     * @param retriever A String representing the User ID or Email.
     * @param plannerId A String representing the planner id.
     */
    public void removePlanner(String retriever, String plannerId) {
        Account account = this.findAccount(retriever);
        String status = account.getAccountType();

//        if (status.equals("regular") | status.equals("temporary")){
//            ((UserAccount) account).removePlanner(plannerId);
//        }
        if (!Objects.equals(status, "admin")) {
            ((UserAccount) account).removePlanner(plannerId);
        }
    }

    /**
     * Get the String representation of an account
     * @param retriever A String representing the User ID or Email.
     * @return The String representation of the account
     */
    public String toString(String retriever){
        Account acc = findAccount(retriever);
        return acc.toString();
    }

    /**
     * Suspend the given user for x amount of days
     * @param retriever A String representing the User ID or Email.
     */
    public void suspendUser(String retriever, long days){
        Account acc = findAccount(retriever);
        acc.setSuspendedTime(days);
    }

    /**
     * Reverse the suspension of a user
     * @param retriever A String representing the User ID or Email.
     */
    public void unSuspendUser(String retriever){
        Account acc = findAccount(retriever);
        acc.removeSuspendTime();
    }

    /**
     * Return whether a user is suspended or not
     * @param retriever A String representing the User ID or Email.
     */
    public boolean suspendedStatus(String retriever){
        Account acc = findAccount(retriever);
        return acc.getSuspendedTime().isAfter(LocalDateTime.now());
    }

    /**
     * return whether the 2 users are friends or not
     * @param selfId the id of the first user
     * @param friendId the id of the 2nd user
     * @return whether the 2 users are friends or not
     */
    public boolean isFriend(String selfId, String friendId){
        return getAllAccount().contains(findAccount(friendId)) && getAllAccount().contains(findAccount(selfId))
                && findAccount(selfId).findFriend(findAccount(friendId));
    }

    /**
     * add 2 users to friend of each other
     * @param selfId the id of the first user
     * @param friendId the id of the 2nd user
     * @return whether adding friend is successful or not
     */
    public boolean addFriend(String selfId, String friendId){
        if (getAllAccount().contains(findAccount(friendId))
                && !findAccount(selfId).getFriends().contains(findAccount(friendId))){
            findAccount(selfId).addFriend(findAccount(friendId));
            findAccount(friendId).addFriend(findAccount(selfId));
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete friends of both users
     * @param selfId the id of the first user
     * @param friendId the id of the 2nd user
     * @return whether deleting friend is successful or not
     */
    public boolean deleteFriend(String selfId, String friendId){
        if (getAllAccount().contains(findAccount(friendId)) && isFriend(selfId, friendId)){
            findAccount(selfId).removeFriend(findAccount(friendId));
            findAccount(friendId).removeFriend(findAccount(selfId));
            return true;
        } else {
            return false;
        }
    }

    /**
     * return the ArrayList of friend ids of a user
     * @param selfId the user
     * @return the ArrayList of friend ids (String) of the user
     */
    public ArrayList<String> getFriends(String selfId){
        ArrayList<Account> friends = findAccount(selfId).getFriends();
        ArrayList<String> friendIds = new ArrayList<>();
        for (Account i : friends){
            friendIds.add(i.getUserId());
        }
        return friendIds;
    }

    /**
     * send mail to the a user
     * @param senderId the id of the sender
     * @param revieveId the id of the receiver
     * @param mail the String message to be sent
     */
    public void sendMail(String senderId, String revieveId, String mail){
        Account acc = findAccount(revieveId);
        acc.receiveMail(senderId, mail);
    }

    /**
     * return the mailbox of a specific user
     * @param userId the user id to be seen the mailbox
     * @return the hashmap representing the mailbox
     */
    public HashMap<String, ArrayList<String>> getMailbox(String userId){
        return findAccount(userId).getMailbox();
    }

    /**
     * see all the mail of one sender of one user's mailbox
     * @param selfId the user who owns the mailbox
     * @param senderId the sender of messages
     * @return String of all mails of the sender sent to the user
     */
    public String seeOnesMail(String selfId, String senderId){
        return findAccount(selfId).seeOnesMail(senderId);
    }

    /**
     * see all the mails of one user
     * @param userId the user to be seen all mails
     * @return the String representation of all mails of the user
     */
    public String seeAllMail(String userId){
        return findAccount(userId).seeAllMail();
    }

    /**
     * return the trashed planners of the given user
     * @param userId the user id of the user
     * @return the ArrayList<String>, trashed planner of the user
     */
    public ArrayList<String> getTrashPlanner(String userId){
        UserAccount acc = (UserAccount) findAccount(userId);
        return acc.getTrashPlanner();
    }

    /**
     * remove the planner from trash, add it to all the planners of the user
     * @param userId the id of the user
     * @param plannerId the id of the planner
     * @return whether the removing and adding are successful or not
     */
    public boolean unTrashPlanner(String userId, String plannerId){
        UserAccount acc = (UserAccount) findAccount(userId);
        if (acc.getTrashPlanner().contains(plannerId)){
            acc.setPlanners(plannerId);
            acc.removeFromTrash(plannerId);
            return true;
        } else {
            return false;
        }
    }

}
