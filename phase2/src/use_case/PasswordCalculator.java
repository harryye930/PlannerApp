package use_case;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * A class dedicated to evaluating complexity level of passwords and generating passwords that satisfy specified complexity.
 */
public class PasswordCalculator {

    /**
     * Returns complexity level (i.e., too weak, weak, or good) depending on the given number of criteria met
     * by a password.
     * Too weak: two or less criteria met
     * Weak: three criteria met
     * Good: all four criteria met
     * @param password A String representing the password.
     * @return A string that's the complexity level of the password.
     */
    public String getPasswordComplexityLevel(String password) {
        int numberOfCriteriaMet = this.getNumberOfCriteriaMet(password);
        if (numberOfCriteriaMet <= 2) {
            return "Too Weak";
        } else if (numberOfCriteriaMet <= 3) {
            return "Weak";
        } else { // All the criteria are met.
            return "Good";
        }
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
        List<String> temporaryRandomPassword = new ArrayList<>();
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
    private boolean matchesCriteria(String s, int criterionNumber) {
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
     * Returns the number of password criteria met by the provided password.
     * Password criteria include:
     * (1) at least 4 characters long,
     * (2) must include at least one upper case letter,
     * (3) must include at least one lower case letter, and
     * (4) must include at least one number.
     * @param password is the password to be checked whether it meets the criteria or not.
     * @return An integer indicating how many of the criteria are met by the password.
     */
    private int getNumberOfCriteriaMet(String password){
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
}
