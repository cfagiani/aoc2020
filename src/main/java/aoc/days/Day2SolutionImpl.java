package aoc.days;

import aoc.DaySolution;
import aoc.model.PasswordRule;
import aoc.util.StringUtil;

import java.util.List;

/**
 * Problem: validate whether passwords adhere to rule about character position and frequency.
 */
public class Day2SolutionImpl implements DaySolution {

    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        int validCount = 0;
        for (String line : lines) {
            if (!line.isBlank()) {
                String[] parts = line.split(":");
                PasswordRule rule = new PasswordRule(parts[0].trim());
                if (rule.hasNumberOfOccurrences(parts[1].trim())) {
                    validCount++;
                }
            }
        }
        System.out.println("There are " + validCount + " valid passwords");
    }

    @Override
    public void part2(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        int validCount = 0;
        for (String line : lines) {
            if (!line.isBlank()) {
                String[] parts = line.split(":");
                PasswordRule rule = new PasswordRule(parts[0].trim());
                if (rule.hasCharacterInPosition(parts[1].trim())) {
                    validCount++;
                }
            }
        }
        System.out.println("There are " + validCount + " valid passwords");
    }
}
