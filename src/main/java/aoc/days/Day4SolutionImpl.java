package aoc.days;

import aoc.DaySolution;
import aoc.model.Passport;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds up a set of Passport objects based on the content of then input file and then reports the number of valid
 * passports. There are two sets of rules for validation. Simple (used in part 1) and per-field (used in part 2).
 */
public class Day4SolutionImpl implements DaySolution {

    private List<Passport> passports;

    @Override
    public void part1(String input) {
        this.passports = buildPassportList(StringUtil.splitOnLines(input));
        long validCount = passports.stream().filter(p -> p.isValid(true)).count();
        System.out.println("There are " + validCount + " valid passports");
    }

    @Override
    public void part2(String input) {
        if (this.passports == null) {
            this.passports = buildPassportList(StringUtil.splitOnLines(input));
        }
        long validCount = this.passports.stream().filter(p -> p.isValid(false)).count();
        System.out.println("There are " + validCount + " valid passports");
    }


    /**
     * Iterate over the input list and initialize a Passport object whenever a blank line is encountered.
     *
     * @param lines
     * @return
     */
    private List<Passport> buildPassportList(List<String> lines) {
        List<String> temp = new ArrayList<>();
        List<Passport> passports = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty() && temp.size() > 0) {
                passports.add(new Passport(temp));
                temp.clear();
            } else {
                temp.add(line);
            }
        }
        if (!temp.isEmpty()) {
            passports.add(new Passport(temp));

        }
        return passports;
    }
}
