package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.List;

/**
 * Problem: find numbers from input that that add up to 2020 and return their product.
 */
public class Day1SolutionImpl implements DaySolution {

    /**
     * This finds 2 numbers from the input that add up to 2020 and then outputs the
     * product of the 2.
     *
     * @param input
     */
    @Override
    public void part1(String input) {
        List<Integer> intList = StringUtil.convertToInts(input);
        int target = 2020;
        for (int i = 0; i < intList.size(); i++) {
            int val = intList.get(i);
            if (intList.contains(target - val)) {
                System.out.println(val * (target - val));
                return;
            }
        }
    }

    /**
     * Brute force solution to part 2. Finds 3 numbers that add up to 2020 and then outputs their product.
     *
     * @param input
     */
    @Override
    public void part2(String input) {
        List<Integer> intList = StringUtil.convertToInts(input);
        int target = 2020;
        for (int i = 0; i < intList.size(); i++) {
            for (int j = i + 1; j < intList.size(); j++) {
                for (int k = j + 1; k < intList.size(); k++) {
                    if (intList.get(i) + intList.get(j) + intList.get(k) == target) {
                        System.out.println(intList.get(i) * intList.get(j) * intList.get(k));
                        return;
                    }
                }
            }
        }
    }
}
