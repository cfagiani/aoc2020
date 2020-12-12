package aoc.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Passport used in Day 4.
 */
public class Passport {
    private static final List<String> VALID_EYE_COLORS =
        Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    private static final Map<String, Predicate<String>> validatorMap;

    static {
        validatorMap = new HashMap<>();
        validatorMap.put("byr", p -> isValidInt(p, 1920, 2002));
        validatorMap.put("iyr", p -> isValidInt(p, 2010, 2020));
        validatorMap.put("eyr", p -> isValidInt(p, 2020, 2030));
        validatorMap.put("hgt", Passport::isValidHeight);
        validatorMap.put("hcl", Passport::isValidHairColor);
        validatorMap.put("ecl", Passport::isValidEyeColor);
        validatorMap.put("pid", Passport::isValidPassportId);
    }

    private final Map<String, String> fields;

    public Passport(List<String> lines) {
        fields = new HashMap<>();
        for (String line : lines) {
            String[] tokens = line.split(" ");
            for (String token : tokens) {
                String[] kvp = token.split(":");
                if (kvp[1].isBlank()) {
                    continue;
                }
                fields.put(kvp[0], kvp[1]);
            }
        }
    }

    /**
     * Checks whether this passport is valid or not. In SIMPLE mode, it just checks all the fields have a value except
     * for the country ID (cid) which is ignored.
     * <p>
     * In non-simple mode, it will validate each field according to the rules that apply to that field.
     *
     * @param simple
     * @return
     */
    public boolean isValid(boolean simple) {
        boolean onlyCidMissing = false;

        if (fields.size() == 8 || (fields.size() == 7 && fields.get("cid") == null)) {
            onlyCidMissing = true;
        }
        if (simple || !onlyCidMissing) {
            return onlyCidMissing;
        }

        // non-simple; validate the individual fields
        for (Map.Entry<String, String> field : this.fields.entrySet()) {
            Predicate<String> validator = validatorMap.get(field.getKey());
            if (validator != null && !validator.test(field.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that a passport id consists of exactly 9 digits.
     *
     * @param val
     * @return
     */
    private static boolean isValidPassportId(String val) {
        return val.matches("[0-9]{9}$");
    }

    /**
     * Checks that a hair color string starts with the # character followed by exactly 6 hex digits (0-9 or a-f).
     *
     * @param val
     * @return
     */
    private static boolean isValidHairColor(String val) {
        return val.matches("#([0-9a-f]){6}$");
    }

    /**
     * Validates that the eye color is among the valid color codes defined in VALID_EYE_COLORS.
     *
     * @param val
     * @return
     */
    private static boolean isValidEyeColor(String val) {
        return VALID_EYE_COLORS.contains(val);
    }

    /**
     * Checks that the height consists of an integer followed by a unit. The only units allowed are cm and in. Based
     * on the unit specified, the integer is validated to ensure it falls within the allowable range.
     *
     * @param val
     * @return
     */
    private static boolean isValidHeight(String val) {
        String unit = val.substring(val.length() - 2);
        String num = val.substring(0, val.length() - 2);
        if (unit.equals("cm")) {
            return isValidInt(num, 150, 193);
        } else if (unit.equals("in")) {
            return isValidInt(num, 59, 76);
        } else {
            return false;
        }
    }

    /**
     * Validates that the string passed in is an integer AND that it falls between the min and max (inclusive).
     *
     * @param val
     * @param min
     * @param max
     * @return
     */
    private static boolean isValidInt(String val, int min, int max) {
        try {
            int intVal = Integer.parseInt(val);
            return intVal >= min && intVal <= max;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
