package aoc.days;

import aoc.DaySolution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Calculates the Nth number in a series where the rules are as follows:
 * start by "saying" all the number in the input
 * for each subsequent turn, if the last number said was said for the first time, say "0" otherwise say the difference
 * between the last two turn numbers on which that number was said.
 * <p>
 * Part 1 outputs the 2020th number. Part 2 outputs the 30000000th number.
 */
public class Day15SolutionImpl implements DaySolution {

    @Override
    public void part1(String input) {
        int maxTurn = 2020;
        int nextNum = getNthNumber(input, maxTurn);
        System.out.println("The " + maxTurn + " number is " + nextNum);
    }

    @Override
    public void part2(String input) {
        int maxTurn = 30000000;
        int nextNum = getNthNumber(input, maxTurn);
        System.out.println("The " + maxTurn + " number is " + nextNum);
    }

    /**
     * computes the nth number according to the memory game rules (see class-level comment for description).
     * <p>
     * This takes about 6 seconds to compute the 30,000,000th number.
     *
     * @param input
     * @param maxTurn
     * @return
     */
    private int getNthNumber(String input, int maxTurn) {
        Map<Integer, Deque<Integer>> numberToTurn = new HashMap<>();
        String[] startingNums = input.split(",");
        int turn = 1;
        int nextNum = 0;
        for (int i = 0; i < startingNums.length; i++) {
            nextNum = Integer.parseInt(startingNums[i]);
            Deque<Integer> turnList = numberToTurn.computeIfAbsent(nextNum, k -> new ArrayDeque<>());
            turnList.add(turn);
            turn++;
        }

        while (turn <= maxTurn) {
            Deque<Integer> turnList = numberToTurn.computeIfAbsent(nextNum, k -> new ArrayDeque<>());
            if (turnList.size() == 1) {
                // this was a new number
                nextNum = 0;
            } else {
                nextNum = turnList.getLast() - turnList.getFirst();
            }
            turnList = numberToTurn.computeIfAbsent(nextNum, k -> new ArrayDeque<>());
            turnList.add(turn);
            if (turnList.size() > 2) {
                // we only care about the last 2 times this number showed up so discard older ones
                turnList.poll();
            }
            turn++;
        }
        return nextNum;
    }


}
