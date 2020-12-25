package aoc.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates a rule for validating a message. Used in Day 19.
 */
public class MessageRule {
    private List<List<Integer>> subrules;
    private String literal;

    public MessageRule(String rule) {
        String[] parts = rule.split(" \\| ");
        subrules = new ArrayList<>();
        for (String p : parts) {
            if (p.contains("\"")) {
                literal = p.replace("\"", "");
            } else {
                List<Integer> subNums = new ArrayList<>();
                String[] subParts = p.split(" ");
                for (String num : subParts) {
                    subNums.add(Integer.parseInt(num));
                }
                subrules.add(subNums);
            }
        }
    }


    public List<String> getMatchingSubstrings(Map<Integer, MessageRule> allRules) {
        List<String> perms = new ArrayList<>();
        if (this.literal != null) {
            return Arrays.asList(this.literal);
        } else {
            for (List<Integer> rule : subrules) {
                List<List<String>> subLists = new ArrayList<>();
                for (Integer rNum : rule) {
                    MessageRule r = allRules.get(rNum);
                    subLists.add(r.getMatchingSubstrings(allRules));
                }
                generatePermutations(subLists, perms, 0, "");
            }
        }
        return perms;
    }

    private void generatePermutations(List<List<String>> lists, List<String> result, int depth, String current) {
        if (depth == lists.size()) {
            result.add(current);
            return;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            generatePermutations(lists, result, depth + 1, current + lists.get(depth).get(i));
        }
    }
}
