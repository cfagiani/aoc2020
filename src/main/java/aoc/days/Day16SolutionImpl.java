package aoc.days;

import aoc.DaySolution;
import aoc.model.RangeValidationRule;
import aoc.model.Ticket;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validates train tickets according to rules in the input file. In the first part, just get the sum of all invalid
 * values (values that are not valid for ANY field). In the second part, find the right ordering of fields on the
 * tickets and then return the product of all the fields that start with "destination" from yourTicket.
 */
public class Day16SolutionImpl implements DaySolution {
    private Map<String, RangeValidationRule> rules;
    private List<Ticket> tickets;
    private Ticket yourTicket;

    @Override
    public void part1(String input) {
        readInput(input);
        List<Integer> invalidList = new ArrayList<>();
        for (Ticket t : tickets) {
            invalidList.addAll(t.getInvalidValues(rules.values()));
        }
        long sum = invalidList.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum of all invalid values is " + sum);
    }

    @Override
    public void part2(String input) {
        // if we haven't run part1 yet, then load the input
        if (tickets == null) {
            readInput(input);
        }

        List<Ticket> validTickets = getValidTickets();
        Map<String, Set<Integer>> possibleIndexes = getPossibleIndexes(validTickets);
        Map<String, Integer> fieldOrder = getFieldOrder(possibleIndexes);

        // now compute the product of all the "departure" fields
        long product = 1;
        for (Map.Entry<String, Integer> fieldEntry : fieldOrder.entrySet()) {
            if (fieldEntry.getKey().startsWith("departure")) {
                product *= yourTicket.getField(fieldEntry.getValue());
            }
        }
        System.out.println("Product of departure values: " + product);
    }

    /**
     * Computes a mapping of fieldName to field index using the set of possible indexes passed in. This method will
     * look for any field where there is only 1 possible index. It will record that mapping and then remove that index
     * from the set of possible indexes for all other fields.
     *
     * @param possibleIndexes
     * @return
     */
    private Map<String, Integer> getFieldOrder(Map<String, Set<Integer>> possibleIndexes) {
        Map<String, Integer> fieldOrder = new HashMap<>();
        while (fieldOrder.size() != rules.size()) {
            // find any entry that has only 1 possible entry
            List<Map.Entry<String, Set<Integer>>> singleValueEntries = possibleIndexes.entrySet().stream()
                .filter(kv -> kv.getValue().size() == 1).collect(Collectors.toList());
            // we know the mapping for these fields. We also need to remove these indexes from any other possible mappings
            for (Map.Entry<String, Set<Integer>> knownEntry : singleValueEntries) {
                Integer idx = knownEntry.getValue().stream().findFirst().get();
                fieldOrder.put(knownEntry.getKey(), idx);
                possibleIndexes.remove(knownEntry.getKey());
                possibleIndexes.values().stream().forEach(s -> s.remove(idx));
            }
        }
        return fieldOrder;
    }

    /**
     * Returns a mapping of field to the set of possible indexes (indexes that COULD be that field). The set of possible
     * indexes are found by evaluating the rules against each ticket. For each ticket, we get the set of index values
     * that are valid for a given rule. We take the intersection of this set across all tickets to find the possible
     * index values for each field.
     *
     * @param validTickets
     * @return
     */
    private Map<String, Set<Integer>> getPossibleIndexes(List<Ticket> validTickets) {
        Map<String, Set<Integer>> possibleIndexes = new HashMap<>();
        for (String field : rules.keySet()) {
            for (Ticket t : validTickets) {
                Set<Integer> validSoFar = possibleIndexes.computeIfAbsent(field, (k) -> new HashSet<>());
                Set<Integer> validFieldIndexes = t.getValidFieldIndexes(rules.get(field));
                if (validSoFar.isEmpty()) {
                    validSoFar.addAll(validFieldIndexes);
                } else {
                    // take the intersection
                    validSoFar.retainAll(validFieldIndexes);
                }
            }
        }
        return possibleIndexes;
    }

    /**
     * Initializes this class using the input file. This will populate the yourTicket, tickets, and rules instance
     * variables.
     *
     * @param input
     */
    private void readInput(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        rules = new HashMap<>();
        tickets = new ArrayList<>();
        for (int i = 0; i < lines.indexOf("your ticket:"); i++) {
            if (!lines.get(i).isBlank()) {
                String[] parts = lines.get(i).split(": ");
                rules.put(parts[0], new RangeValidationRule(parts[1]));
            }
        }
        yourTicket = new Ticket(lines.get(lines.indexOf("your ticket:") + 1));
        for (int i = lines.indexOf("nearby tickets:") + 1; i < lines.size(); i++) {
            if (!lines.get(i).isBlank()) {
                tickets.add(new Ticket(lines.get(i)));
            }
        }

    }


    /**
     * Returns a new list consisting of yourTicket and all the tickets from the tickets instance variable that are
     * valid.
     *
     * @return
     */
    private List<Ticket> getValidTickets() {
        List<Ticket> validTickets = new ArrayList<>();
        validTickets.add(yourTicket);
        for (Ticket t : tickets) {
            if (t.getInvalidValues(rules.values()).isEmpty()) {
                validTickets.add(t);
            }
        }
        return validTickets;
    }
}
