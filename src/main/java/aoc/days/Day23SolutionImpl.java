package aoc.days;

import aoc.DaySolution;
import aoc.model.CupList;

import java.util.List;

/**
 * Assembles a list of "cups" in a circular linked list. Plays a number of rounds (100 in part 1, 10000000 in part 2)
 * where we remove 3 cups from the list and insert them elsewhere.
 */
public class Day23SolutionImpl implements DaySolution {

    @Override
    public void part1(String input) {
        CupList cupList = new CupList(input);
        for (int turn = 0; turn < 100; turn++) {
            List<Integer> cups = cupList.remove(3);
            cupList.insertAfterValue(cupList.getCurrentValue() - 1, cups);
            cupList.advanceCurrent();
        }
        System.out.print("Cup order: ");
        cupList.printStartingFrom(1);
    }

    @Override
    public void part2(String input) {
        CupList cupList = new CupList(input, 1000000);
        for (long turn = 0; turn < 10000000; turn++) {
            List<Integer> cups = cupList.remove(3);
            cupList.insertAfterValue(cupList.getCurrentValue() - 1, cups);
            cupList.advanceCurrent();
        }
        System.out.println("Product of 2 cups after 1: " + cupList.getProductStartingAfter(1, 2));
    }
}
