package aoc.days;

import aoc.DaySolution;
import aoc.model.Ship;
import aoc.util.StringUtil;

import java.util.List;


/**
 * Finds the Manhattan distance from the starting point after processing a series of movement commands. Part 1 moves
 * the ship directly whereas part 2 moves both a waypoint and the ship to the waypoint.
 */
public class Day12SolutionImpl implements DaySolution {

    @Override
    public void part1(String input) {
        List<String> instructions = StringUtil.splitOnLines(input);
        Ship ferry = new Ship();
        for (String ins : instructions) {
            ferry.move(ins);
        }
        System.out.println("Distance from start: " + ferry.getDistance());
    }

    @Override
    public void part2(String input) {
        List<String> instructions = StringUtil.splitOnLines(input);
        Ship ferry = new Ship();
        for (String ins : instructions) {
            ferry.moveWithWaypoints(ins);
        }
        System.out.println("Distance from start: " + ferry.getDistance());
    }
}
