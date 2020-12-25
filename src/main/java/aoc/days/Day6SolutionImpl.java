package aoc.days;

import aoc.DaySolution;
import aoc.Part1RequiredException;
import aoc.model.PassengerGroup;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds a data structure representing how a group of airline passengers responded to custom form questions. It then
 * outputs first the number of "yes" answers (count of number of questions at least 1 group member answered "YES" to)
 * in part 1 and then, in part 2, the sum of "yes" answers where every member of the group answered the same question in
 * the affirmative.
 */
public class Day6SolutionImpl implements DaySolution {

    List<PassengerGroup> groups;

    @Override
    public void part1(String input) {
        groups = new ArrayList<>();
        List<String> lines = StringUtil.splitOnLines(input);
        PassengerGroup group = new PassengerGroup();
        int yesCount = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                groups.add(group);
                yesCount += group.countAffirmatives();
                group = new PassengerGroup();
            } else {
                group.addPassengerResponse(line);
            }
        }
        groups.add(group);
        yesCount += group.countAffirmatives();
        System.out.println("Total yes count: " + yesCount);
    }

    @Override
    public void part2(String input) {
        if (groups == null) {
            throw new Part1RequiredException();
        }
        int count = 0;
        for (PassengerGroup group : groups) {
            count += group.countAllAffirmatives();
        }
        System.out.println("Total of unanimous affirmatives: " + count);
    }
}
