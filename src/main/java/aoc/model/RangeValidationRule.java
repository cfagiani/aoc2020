package aoc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rule for validating an integer according to 2 ranges.
 * Used in Day 16.
 */
public class RangeValidationRule {
    private List<Integer> mins;
    private List<Integer> maxes;

    /**
     * Initializes this rule from a definition string in the form "x1-x2 or y1-y2" where x1, x2, y1, and y2 are all
     * integers.
     *
     * @param rule
     */
    public RangeValidationRule(String rule) {
        mins = new ArrayList<>();
        maxes = new ArrayList<>();
        String[] rules = rule.split(" or ");
        for (String r : rules) {
            String[] range = r.split("-");
            mins.add(Integer.parseInt(range[0].trim()));
            maxes.add(Integer.parseInt(range[1].trim()));
        }
    }

    /**
     * Returns true if the value passed in is within the ranges for the rule instance.
     *
     * @param num
     * @return
     */
    public boolean isValid(int num) {
        for (int i = 0; i < mins.size(); i++) {
            if (num >= mins.get(i) && num <= maxes.get(i)) {
                return true;
            }
        }
        return false;
    }
}
