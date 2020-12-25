package aoc.days;

import aoc.DaySolution;
import aoc.util.StringUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Uses a set of direction string to set the initial state of hexagonal floor tiles. Each line in the input is a path
 * to a tile. Tiles are flipped each time they're encountered. In part 1, we simply count the initial black tiles.
 * In part 2, we run 100 generations of an algorithm that flips tiles based on the number of adjacent black tiles and
 * the current color of the tile being considered.
 */
public class Day24SolutionImpl implements DaySolution {
    @Override
    public void part1(String input) {
        Set<String> blackGrid = buildInitialTileList(input);

        System.out.println("There are " + blackGrid.size() + " black tiles");
    }

    @Override
    public void part2(String input) {
        Set<String> blackGrid = buildInitialTileList(input);
        for (int i = 0; i < 100; i++) {
            Set<String> nextGrid = new HashSet<>();
            //first consider all the tiles we need to consider. We should look at all the tiles adjacent to the
            //tiles in the map
            Set<String> adj = getAllTiles(blackGrid);
            for (String loc : adj) {
                if (shouldBeBlack(loc, blackGrid)) {
                    nextGrid.add(loc);
                }
            }

            blackGrid = nextGrid;
        }
        System.out.println("There are " + blackGrid.size() + " black tiles after 100 days.");
    }

    /**
     * Returns true if the location passed in should be black. The rules are:
     * if loc is black, then it should be flipped to white if it has either 0 or more than 2 black neighbors. If loc
     * is white, then it should be flipped to black if it has exactly 2 black neighbors.
     *
     * @param loc
     * @param grid
     * @return
     */
    private boolean shouldBeBlack(String loc, Set<String> grid) {
        long neighbors = getAdjacentLocs(loc).stream().filter(grid::contains).count();
        if (grid.contains(loc)) {
            return !(neighbors > 2 || neighbors == 0);
        } else {
            return neighbors == 2;
        }
    }

    /**
     * Returns the set of all the locations in the grid along with all the adjacent locations to those tiles.
     *
     * @param grid
     * @return
     */
    private Set<String> getAllTiles(Set<String> grid) {
        Set<String> adj = new HashSet<>();
        for (String loc : grid) {
            adj.addAll(getAdjacentLocs(loc));
            //add the loc itself too
            adj.add(loc);
        }
        return adj;
    }

    /**
     * Returns a set of tile locations that are immediately adjacent to the location passed in using a hexagonal grid.
     * This set will contain exactly 6 elements and will NOT include the location itself.
     *
     * @param loc
     * @return
     */
    private Set<String> getAdjacentLocs(String loc) {
        Set<String> adj = new HashSet<>();
        String[] parts = loc.split(",");
        double x = Double.parseDouble(parts[0]); ///-2
        double y = Double.parseDouble(parts[1]); ///-1
        for (double dy = -1.0; dy <= 1; dy++) {
            for (double dx = -1; dx <= 1; dx += .5) {
                if ((dx == 0 && dy == 0) || (dy == 0 && Math.abs(dx) == .5) || (dy != 0 && Math.abs(dx) != .5)) {
                    continue;
                }
                adj.add((x + dx) + "," + (y + dy));
            }
        }
        return adj;
    }

    /**
     * Parses the input to build the initial list of black tiles. Each line in the input is a series of directions that
     * can be e, w, ne, nw, se, sw. The string does not contain delimiters.
     *
     * @param input
     * @return
     */
    private Set<String> buildInitialTileList(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        Set<String> blackGrid = new HashSet<>();
        for (String line : lines) {
            double curHoriz = 0.0;
            double curVert = 0.0;
            for (int i = 0; i < line.length(); i++) {
                char dir = line.charAt(i);
                boolean readNextChar = false;
                switch (dir) {
                    case 'n':
                        curVert--;
                        readNextChar = true;
                        break;
                    case 's':
                        curVert++;
                        readNextChar = true;
                        break;
                    case 'e':
                        curHoriz++;
                        break;
                    case 'w':
                        curHoriz--;
                        break;
                }
                if (readNextChar) {
                    i++;
                    char dir2 = line.charAt(i);
                    switch (dir2) {
                        case 'e':
                            curHoriz += 0.5;
                            break;
                        case 'w':
                            curHoriz -= 0.5;
                            break;
                    }
                }
            }
            String key = curHoriz + "," + curVert;
            if (blackGrid.contains(key)) {
                blackGrid.remove(key);
            } else {
                blackGrid.add(key);
            }
        }
        return blackGrid;
    }
}
