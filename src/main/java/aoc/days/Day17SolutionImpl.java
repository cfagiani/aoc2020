package aoc.days;

import aoc.DaySolution;
import aoc.model.PocketDimension;
import aoc.util.StringUtil;

import java.util.List;

public class Day17SolutionImpl implements DaySolution {

    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        PocketDimension dimension = new PocketDimension(lines, false);
        int rounds = 6;
        for (int i = 0; i < rounds; i++) {
            dimension.advanceCycle();
        }
        System.out.println("After " + rounds + " rounds, there are " + dimension.getActiveCubes().size() + " active cubes");
    }

    //922 too low
    @Override
    public void part2(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        PocketDimension dimension = new PocketDimension(lines, true);
        int rounds = 6;
        for (int i = 0; i < rounds; i++) {
            dimension.advanceCycle();
        }
        System.out.println("After " + rounds + " rounds, there are " + dimension.getActiveCubes().size() + " active cubes");
    }
}
