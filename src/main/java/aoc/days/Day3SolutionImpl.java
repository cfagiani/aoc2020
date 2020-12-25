package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem: given a map of tree positions, determine how many trees you'd encounter if you followed a slope.
 */
public class Day3SolutionImpl implements DaySolution {

    private List<List<Boolean>> map;

    @Override
    public void part1(String input) {
        initializeMap(StringUtil.splitOnLines(input));
        int treeCount = countTreesForSlope(3, 1);
        System.out.println("Encountered " + treeCount + " trees");
    }

    @Override
    public void part2(String input) {
        if (map == null) {
            initializeMap(StringUtil.splitOnLines(input));
        }
        int[][] slopes = {{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2}};
        long product = 1;
        for (int[] slope : slopes) {
            product *= countTreesForSlope(slope[0], slope[1]);
        }
        System.out.println("Product of tree counts: " + product);
    }

    /**
     * Counts the number of trees encountered if you followed a given slope (specified as horizontal and vertical steps)
     * down the hill.
     *
     * @param horizStep
     * @param vertStep
     * @return
     */
    private int countTreesForSlope(int horizStep, int vertStep) {
        int treeCount = 0;
        int curHoriz = 0;
        for (int i = 0; i < map.size(); i += vertStep) {
            // the row pattern repeats but we only need to store it once. take the modulo of the size
            if (map.get(i).get(curHoriz % map.get(i).size())) {
                treeCount++;
            }
            curHoriz += horizStep;
        }
        return treeCount;
    }

    /**
     * Parses the input and initializes a class member to hold a "map" of the tree positions. A value of True in the
     * map indicates that a tree is at that position.
     *
     * @param lines
     */
    private void initializeMap(List<String> lines) {
        map = new ArrayList<>();
        for (String line : lines) {
            List<Boolean> row = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                row.add(line.charAt(i) == '#');
            }
            map.add(row);
        }
    }
}
