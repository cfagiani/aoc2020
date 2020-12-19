package aoc.days;

import aoc.DaySolution;
import aoc.model.MessageRule;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19SolutionImpl implements DaySolution {

    private List<String> messages;
    private Map<Integer, MessageRule> rules;

    @Override
    public void part1(String input) {
        init(input);
        List<String> possible = rules.get(0).getMatchingSubstrings(rules);
        int count = 0;
        for (String m : messages) {
            if (possible.contains(m)) {
                count++;
            }
        }
        System.out.println(count + " messages match rule 0");

    }

    @Override
    public void part2(String input) {
        if (this.messages == null) {
            init(input);
        }

        // update the rules with the new rules that create cycles
        rules.put(8, new MessageRule("42 | 42 8"));
        rules.put(11, new MessageRule("42 31 | 42 11 31"));

        // we know we want to check rule 0 which is 0: 8 11 so all valid solutions
        // will have 2 strings from rule 42 at the beginning and 1 string from rule 31 at the end
        List<String> possible42 = rules.get(42).getMatchingSubstrings(rules);
        List<String> possible31 = rules.get(31).getMatchingSubstrings(rules);

        List<String> candidates = new ArrayList<>();
        for (String possibleEnd : possible31) {
            candidates.addAll(messages.stream().filter(m -> m.endsWith(possibleEnd)).map(m -> m.substring(0, m.lastIndexOf(possibleEnd))).collect(Collectors.toList()));
        }
        // now filter those candidates to see if any of them start with 2 elements from possible42
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (String possibleStart : possible42) {
                temp.addAll(candidates.stream().filter(m -> m.startsWith(possibleStart)).map(m -> m.substring(possibleStart.length())).collect(Collectors.toList()));
            }
            candidates = temp;
            temp = new ArrayList<>();
        }


        // all entries are 8 chars so count the number of starts
        // candidates can only have 42's and 31's, and the amount of 31's cannot be higher than the amount of 42's
        // we already stripped off the start/end so candidate is already a substring of the message
        int okCount = 0;
        for (String candidate : candidates) {
            int countStart = 0;
            int countEnd = 0;
            while (candidate.length() > 0 && possible42.contains(candidate.substring(0, 8))) {
                candidate = candidate.substring(8);
                countStart++;
            }
            while (candidate.length() > 0 && possible31.contains(candidate.substring(0, 8))) {
                candidate = candidate.substring(8);
                countEnd++;
            }
            if (candidate.length() == 0 && countEnd <= countStart) {
                okCount++;
            }
        }
        System.out.println(okCount + " messages match rule 0");
    }


    public void init(String input) {

        List<String> lines = StringUtil.splitOnLines(input);
        rules = new HashMap<>();
        for (String line : lines.subList(0, lines.indexOf(""))) {
            String[] parts = line.split(": ");
            rules.put(Integer.parseInt(parts[0]), new MessageRule(parts[1]));

        }
        messages = lines.subList(lines.indexOf(""), lines.size());
    }
}
