package aoc.days;

import aoc.DaySolution;
import aoc.model.Seat;
import aoc.util.StringUtil;

import java.util.List;
import java.util.TreeMap;

/**
 * Uses binary partitioning to build a list of occupied airline seats. Then finds the one un-occupied seat.
 */
public class Day5SolutionImpl implements DaySolution {

    private TreeMap<Integer, Seat> seats;

    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        Seat max = null;
        seats = new TreeMap();
        for (String line : lines) {
            Seat s = new Seat(line);
            seats.put(s.getId(), s);
            if (max == null || s.getId() > max.getId()) {
                max = s;
            }
        }
        System.out.println("Max seat id is " + max.getId());
    }

    @Override
    public void part2(String input) {
        Seat[] orderedSeats = seats.values().toArray(new Seat[] {});
        //look at a sliding window of seats
        for (int i = 1; i < orderedSeats.length; i++) {
            if (orderedSeats[i].getId() - orderedSeats[i - 1].getId() == 2) {
                System.out.println("Your seat is " + (orderedSeats[i].getId() - 1));
                return;
            }
        }
    }
}
