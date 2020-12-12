package aoc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Used in Day 6. Tracks custom form responses for a passenger group.
 */
public class PassengerGroup {

    private List<String> passengerResponses;

    public PassengerGroup() {
        passengerResponses = new ArrayList<>();
    }

    public void addPassengerResponse(String responses) {
        this.passengerResponses.add(responses);
    }

    /**
     * Counts the total number of questions that have a "yes" answer for this group. If more than one passenger answered
     * "yes" to a question, it is only counted once.
     *
     * @return
     */
    public int countAffirmatives() {
        Set<Character> yesses = new HashSet<>();
        for (String pr : passengerResponses) {
            for (char c : pr.toCharArray()) {
                yesses.add(c);
            }
        }
        return yesses.size();
    }

    /**
     * Counts the number of questions to which every member of the group answered in the affirmative.
     *
     * @return
     */
    public int countAllAffirmatives() {
        Map<Character, Integer> counts = new HashMap<>();
        for (String pr : passengerResponses) {
            for (char c : pr.toCharArray()) {
                Integer curCount = counts.getOrDefault(c, 0);
                counts.put(c, curCount + 1);
            }
        }
        return (int) counts.values().stream().filter(v -> v.equals(passengerResponses.size())).count();
    }

}
