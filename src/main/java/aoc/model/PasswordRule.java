package aoc.model;

/**
 * PasswordRules used in Day 2.
 */
public class PasswordRule {

    private int min;
    private int max;
    private char character;

    /**
     * Parses a rule from an input string. Input can be a full input string like: 8-11 l: qllllqllklhlvtl
     * or just the rule portion: 8-1 l
     *
     * @param input
     */
    public PasswordRule(String input) {

        String[] parts = input.split(":");
        String[] ruleParts = parts[0].split(" ");
        String[] range = ruleParts[0].split("-");
        min = Integer.parseInt(range[0]);
        max = Integer.parseInt(range[1]);
        character = ruleParts[1].toCharArray()[0];
    }

    /**
     * Verifies that the password has between min and max occurrences of the character in the string (inclusive).
     *
     * @param password
     * @return
     */
    public boolean hasNumberOfOccurrences(String password) {
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == character) {
                count++;
            }
        }
        return (count <= max && count >= min);
    }

    /**
     * Verifies that the password has the character specified in the rule at EITHER the min or the max index (1-based
     * index) but not both.
     *
     * @param password
     * @return
     */
    public boolean hasCharacterInPosition(String password) {
        //XOR
        return (password.length() >= min && password.charAt(min - 1) == character) ^
            (password.length() >= max && password.charAt(max - 1) == character);
    }

}
