package aoc.days;

import aoc.DaySolution;
import aoc.Part1RequiredException;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Considers combinations of power adapters. In part one, we find the distribution of "joltage" differences between
 * all adapters. In part 2, we compute all the possible combinations of power adapters that can be used to power the
 * device.
 */
public class Day10SolutionImpl implements DaySolution {

    private int MAX_DIFF = 3;
    private int targetJoltage;

    @Override
    public void part1(String input) {
        int curJoltage = 0;
        List<Integer> adapters = StringUtil.convertToInts(input);
        Collections.sort(adapters);
        Map<Integer, Integer> diffCount = new HashMap<>();
        for (int nextVal : adapters) {
            int diff = nextVal - curJoltage;
            if (diff == 0 || diff > MAX_DIFF) {
                System.out.println("Invalid joltage gap from " + curJoltage + " to " + nextVal);
                return;
            }
            int prevVal = diffCount.computeIfAbsent(diff, p -> Integer.valueOf(0));
            diffCount.put(diff, prevVal + 1);
            curJoltage = nextVal;
        }
        // the last one is always +3
        diffCount.put(3, diffCount.get(3) + 1);
        targetJoltage = curJoltage + 3;
        System.out.println("Product differences is " + (diffCount.get(1) * diffCount.get(3)));
    }

    @Override
    public void part2(String input) {
        if (targetJoltage == 0) {
            throw new Part1RequiredException();
        }
        List<Integer> adapters = StringUtil.convertToInts(input);
        Collections.sort(adapters);
        adapters.add(0, 0); //add starting joltage
        adapters.add(targetJoltage); // add ending joltage
        Map<Integer, Long> pathsFromNode = new HashMap<>();
        System.out.println("There are " + countPaths(0, adapters, pathsFromNode) + " possible combinations of adapters");
    }

    /**
     * Recursively computes the paths to the final node from starting index passed in. This method keeps track of the
     * nodes it has seen before in the pathsFromNode lookup map so we don't perform unneeded recomputations.
     *
     * @param startIndex
     * @param adapters
     * @param pathsFromNode
     * @return
     */
    private Long countPaths(int startIndex, List<Integer> adapters, Map<Integer, Long> pathsFromNode) {
        long count = 0L;
        if (!pathsFromNode.containsKey(startIndex)) {
            // if we are here, it's the first time we've seen this node, so we have to compute

            // first build up the list of indexes that are valid next steps from this node.
            // we only need to look MAX_DIFF nodes ahead since all steps need to be at least 1 jolt
            List<Integer> nodesToVisit = new ArrayList<>();
            for (int i = startIndex + 1; i < startIndex + MAX_DIFF + 1 && i < adapters.size(); i++) {
                int diff = adapters.get(i) - adapters.get(startIndex);
                if (diff >= 0 && diff <= MAX_DIFF) {
                    nodesToVisit.add(i);
                }
            }
            // now go through candidates and recursively find the paths
            if (nodesToVisit.size() > 0) {
                for (int i : nodesToVisit) {
                    count += countPaths(i, adapters, pathsFromNode);
                    pathsFromNode.put(startIndex, count);
                }
            } else {
                return 1L;
            }
        }
        return pathsFromNode.get(startIndex);
    }
}
