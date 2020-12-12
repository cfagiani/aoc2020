package aoc.days;

import aoc.DaySolution;
import aoc.Part1RequiredException;
import aoc.util.StringUtil;

import java.util.Comparator;
import java.util.List;

/**
 * Finds the "encryption weakness" in the XMAS cipher. This is done in two parts: part 1 finds the first number from the
 * input that is not the sum of 2 of the preceding 25 numbers in the input list. Part 2 uses that same value and finds
 * a contiguous set of numbers that sum up to that value. It then outputs the sum of the minimum and maximum of those
 * contiguous values.
 */
public class Day9SolutionImpl implements DaySolution {

    private long targetValue;

    /**
     * Finds the first number that isn't a sum of 2 of the 25 numbers that immediately precede it. This result is
     * set in the targetValue member variable.
     *
     * @param input
     */
    @Override
    public void part1(String input) {
        List<Long> longList = StringUtil.convertToLong(input);
        int preambleLength = 25;
        for (int i = preambleLength; i < longList.size(); i++) {
            long candidate = longList.get(i);
            List<Long> searchList = longList.subList(i - preambleLength, i);
            if (!containsSum(searchList, candidate)) {
                targetValue = candidate;
                System.out.println("The value " + candidate + " does not have the property");
                return;
            }
        }
    }


    /**
     * Finds a contiguous set of numbers from the input that adds up to the targetValue from part 1 and then outputs
     * the sum of the minimum and maximum of those values.
     *
     * @param input
     */
    @Override
    public void part2(String input) {
        if (targetValue == 0L) {
            throw new Part1RequiredException();
        }
        List<Long> longList = StringUtil.convertToLong(input);
        for (int i = 0; i < longList.size(); i++) {
            long sum = longList.get(i);
            for (int j = i + 1; j < longList.size(); j++) {
                long nextVal = longList.get(j);
                if (sum + nextVal == targetValue) {
                    List<Long> sumList = longList.subList(i, j);
                    long val = sumList.stream().min(Comparator.comparingLong(Long::valueOf)).get();
                    val += sumList.stream().max(Comparator.comparingLong(Long::valueOf)).get();
                    System.out.println("The sum of min and max are " + val);
                } else if (sum + nextVal < targetValue) {
                    sum += nextVal;
                } else {
                    // we've already exceeded the target so break the inner loop
                    break;
                }
            }
        }
    }

    /**
     * Checks if the list passed in contains 2 different entries that add up to the target.
     *
     * @param longList
     * @param target
     * @return
     */
    private boolean containsSum(List<Long> longList, long target) {
        for (int i = 0; i < longList.size(); i++) {
            long val = longList.get(i);
            long diff = target - val;
            if (longList.contains(diff) && longList.indexOf(diff) != i) {
                return true;
            }
        }
        return false;
    }
}
