package aoc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Model of a train ticket. Used in Day 16.
 */
public class Ticket {
    List<Integer> fieldValues;

    /**
     * Initializes the ticket with a list of integer values from the input string. Input is a comma-delimited list of
     * integers.
     *
     * @param input
     */
    public Ticket(String input) {
        String[] parts = input.split(",");
        fieldValues = new ArrayList<>();
        for (String v : parts) {
            fieldValues.add(Integer.parseInt(v));
        }
    }

    /**
     * Returns values from this ticket's fields that are not valid for ANY of the rules passed in.
     *
     * @param rules
     * @return
     */
    public List<Integer> getInvalidValues(Collection<RangeValidationRule> rules) {
        List<Integer> invalid = new ArrayList<>();
        for (int val : fieldValues) {
            if (!isValidValue(val, rules)) {
                invalid.add(val);
            }
        }
        return invalid;
    }

    /**
     * Gets the indexes of fields for this ticket that are valid for the rule passed in.
     *
     * @param rule
     * @return
     */
    public Set<Integer> getValidFieldIndexes(RangeValidationRule rule) {
        Set<Integer> validSet = new HashSet<>();
        for (int i = 0; i < fieldValues.size(); i++) {
            if (rule.isValid(fieldValues.get(i))) {
                validSet.add(i);
            }
        }
        return validSet;
    }

    /**
     * Returns the value of a field at the given index.
     *
     * @param idx
     * @return
     */
    public Integer getField(int idx) {
        return fieldValues.get(idx);
    }

    /**
     * Validates that a value falls within the set of rules passed in.
     *
     * @param val
     * @param rules
     * @return
     */
    private boolean isValidValue(int val, Collection<RangeValidationRule> rules) {
        for (RangeValidationRule r : rules) {
            if (r.isValid(val)) {
                return true;
            }
        }
        return false;
    }


}
