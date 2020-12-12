package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class Day11SolutionImpl implements DaySolution {
    @Override
    public void part1(String input) {
        List<List<Character>> seatMap = buildMap(input);
        int moves = 0;
        do {
            List<List<Character>> newMap = moveSeats(seatMap, 4, this::countAdjacentSeats);
            moves = countDifferences(newMap, seatMap);
            seatMap = newMap;

        } while (moves > 0);

        long occupied = seatMap.stream().map(
            r -> r.stream().filter(c -> c == '#').count()).mapToLong(Long::valueOf).sum();

        System.out.println("There are " + occupied + " occupied seats");
    }

    @Override
    public void part2(String input) {
        List<List<Character>> seatMap = buildMap(input);
        int moves = 0;
        do {
            List<List<Character>> newMap = moveSeats(seatMap, 5, this::countFirstSeatInDirs);
            moves = countDifferences(newMap, seatMap);
            seatMap = newMap;
        } while (moves > 0);

        long occupied = seatMap.stream().map(
            r -> r.stream().filter(c -> c == '#').count()).mapToLong(Long::valueOf).sum();

        System.out.println("There are " + occupied + " occupied seats");
    }

    /**
     * Applies the rules for seating. For each seat, if the seat is unoccupied and there are no occupied seats near it
     * (as defined by the counter function passed int), then the seat becomes occupied. For occupied seats, if the
     * counter counts more than threshold occupied seats, the seat becomes vacant.
     *
     * @param seatMap
     * @param threshold
     * @param counter
     * @return
     */
    private List<List<Character>> moveSeats(List<List<Character>> seatMap, int threshold, SeatCounter counter) {
        List<List<Character>> newMap = new ArrayList<>();
        for (int i = 0; i < seatMap.size(); i++) {
            newMap.add(new ArrayList<>());
            for (int j = 0; j < seatMap.get(i).size(); j++) {
                int adjCount = 0;
                switch (seatMap.get(i).get(j)) {
                    case 'L':
                        adjCount = counter.count(seatMap, i, j);
                        if (adjCount == 0) {
                            newMap.get(i).add('#');
                        } else {
                            newMap.get(i).add('L');
                        }
                        break;
                    case '#':
                        adjCount = counter.count(seatMap, i, j);
                        if (adjCount >= threshold) {
                            newMap.get(i).add('L');
                        } else {
                            newMap.get(i).add('#');
                        }
                        break;
                    default:
                        newMap.get(i).add(seatMap.get(i).get(j));
                }
            }
        }
        return newMap;
    }

    /**
     * Counts how many of the (up to) 8 adjacent seats are occupied.
     *
     * @param seatMap
     * @param r
     * @param c
     * @return
     */
    private int countAdjacentSeats(List<List<Character>> seatMap, int r, int c) {
        int count = 0;
        for (int i = (int) Math.max(0, r - 1); i < (int) Math.min(r + 2, seatMap.size()); i++) {
            for (int j = (int) Math.max(0, c - 1); j < (int) Math.min(c + 2, seatMap.get(i).size()); j++) {
                if (i == r && j == c) {
                    // don't count the seat itself
                    continue;
                }
                if (seatMap.get(i).get(j) == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Considers all the seats in 8 directions around the seat r,c. Counts the number of occupied seats that can be
     * seen by r,c.
     *
     * @param seatMap
     * @param r
     * @param c
     * @return
     */
    private int countFirstSeatInDirs(List<List<Character>> seatMap, int r, int c) {
        int count = 0;
        int[][] dirList = {
            {0, -1}, {0, 1}, {1, 0}, {-1, 0},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

        for (int[] dir : dirList) {
            int i = r;
            int j = c;
            while (i >= 0 && i < seatMap.size() && j >= 0 && j < seatMap.get(i).size()) {
                if (i != r || j != c) {
                    if (seatMap.get(i).get(j) == '#') {
                        count++;
                        break;
                    } else if (seatMap.get(i).get(j) == 'L') {
                        break;
                    }
                }
                i += dir[0];
                j += dir[1];
            }
        }
        return count;
    }

    /**
     * Counts the number of entries of the two lists that are not equal.
     *
     * @param seatMap
     * @param otherMap
     * @return
     */
    private int countDifferences(List<List<Character>> seatMap, List<List<Character>> otherMap) {
        int count = 0;
        for (int i = 0; i < seatMap.size(); i++) {
            for (int j = 0; j < seatMap.get(i).size(); j++) {
                if (seatMap.get(i).get(j) != otherMap.get(i).get(j)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Builds a list of lists of characters to represent the seating map.
     *
     * @param input
     * @return
     */
    private List<List<Character>> buildMap(String input) {
        List<String> rows = StringUtil.splitOnLines(input);
        List<List<Character>> seatMap = new ArrayList<>();
        for (String row : rows) {
            List<Character> rowSeats = new ArrayList<>();
            for (char c : row.toCharArray()) {
                rowSeats.add(c);
            }
            seatMap.add(rowSeats);
        }
        return seatMap;
    }

    /**
     * Functional interface for how we should count occupied seats.
     */
    interface SeatCounter {
        int count(List<List<Character>> seatMap, int r, int c);
    }

}


