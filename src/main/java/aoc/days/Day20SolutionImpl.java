package aoc.days;

import aoc.DaySolution;
import aoc.Part1RequiredException;
import aoc.model.ImageTile;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builds up an "image" from a set of image tiles. In part 1, tiles are rotated/flipped independently until they can all be
 * placed in a grid (lining up their borders). In part 2, the borders are removed and then we search the image for
 * "monsters" that can be in any orientation.
 */
public class Day20SolutionImpl implements DaySolution {

    private static final char[][] MONSTER = {
        "..................#.".toCharArray(),
        "#....##....##....###".toCharArray(),
        ".#..#..#..#..#..#".toCharArray()
    };
    private ImageTile[][] tileGrid;

    @Override
    public void part1(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        Map<Long, List<ImageTile>> tiles = new HashMap<>();
        for (int i = 0; i <= lines.size() - 11; i += 12) {
            ImageTile tile = new ImageTile(lines.subList(i, i + 11));
            tiles.put(tile.getId(), tile.generateVariants());
        }
        int gridSize = (int) Math.sqrt(tiles.size());
        tileGrid = new ImageTile[gridSize][gridSize];
        place(tiles, tileGrid, 0, 0, new ArrayList(tiles.keySet()));

        long product = tileGrid[0][0].getId() * tileGrid[0][gridSize - 1].getId() *
            tileGrid[gridSize - 1][0].getId() * tileGrid[gridSize - 1][gridSize - 1].getId();
        System.out.println("Product of corners are: " + product);
    }

    @Override
    public void part2(String input) {
        if (tileGrid == null) {
            throw new Part1RequiredException();
        }
        Character[][] trimmedGrid = new Character[tileGrid.length * 8][tileGrid.length * 8];
        for (int i = 0; i < tileGrid.length; i++) {
            for (int m = 1; m < 9; m++) {
                for (int j = 0; j < tileGrid.length; j++) {
                    for (int n = 1; n < 9; n++) {
                        trimmedGrid[i * 8 + m - 1][j * 8 + n - 1] = tileGrid[i][j].getChar(m, n);
                    }
                }
            }
        }
        ImageTile trimmedImage = new ImageTile(-1L, trimmedGrid);
        int monsterCount = 0;
        for (int i = 0; i < 8; i++) {
            monsterCount += findMonster(trimmedImage);
            if (i == 4) {
                trimmedImage.flip();
            } else {
                trimmedImage.rotate();
            }
        }
        System.out.println("Found " + monsterCount + " monsters");
        int totalNonBlank = 0;
        for (Character[] row : trimmedGrid) {
            totalNonBlank += Collections.frequency(Arrays.asList(row), '#');
        }

        System.out.println("Roughness " + (totalNonBlank - (15 * monsterCount)));

    }

    /**
     * Looks for monsters in the imageTile.
     *
     * @param tile
     * @return
     */
    private int findMonster(ImageTile tile) {
        int count = 0;
        // go from 1 to size-1 since we are going to search for the middle of the monster and then look above/below
        for (int i = 1; i < tile.getState().length - 1; i++) {
            for (int j = 0; j < tile.getState()[i].length - MONSTER[1].length; j++) {
                if (checkPos(tile.getState()[i], MONSTER[1], j) &&
                    checkPos(tile.getState()[i - 1], MONSTER[0], j) &&
                    checkPos(tile.getState()[i + 1], MONSTER[2], j)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if the toFind string is in the row starting at the position passed in. For this comparison, only positions
     * that are non-blanks in the toFind string are considered.
     *
     * @param row
     * @param toFind
     * @param startPos
     * @return
     */
    private boolean checkPos(Character[] row, char[] toFind, int startPos) {
        for (int i = 0; i < toFind.length; i++) {
            if (toFind[i] == '#' && row[i + startPos] != toFind[i]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Recursively searches for the right placement for the tiles.
     *
     * @param allTiles
     * @param positions
     * @param i
     * @param j
     * @param availIds
     * @return
     */
    public boolean place(Map<Long, List<ImageTile>> allTiles, ImageTile[][] positions, int i, int j, List<Long> availIds) {

        // if we have placed everything, we're done
        if (availIds.isEmpty()) {
            return true;
        }

        int nextI = i;
        int nextJ = j;
        if (j < positions.length - 1) {
            nextJ++;
        } else {
            nextI++;
            nextJ = 0;
        }

        for (ImageTile tile : getTilesForPosition(allTiles, positions, i, j, availIds)) {
            positions[i][j] = tile;
            if (place(allTiles, positions, nextI, nextJ, availIds.stream().filter(tid -> !tid.equals(tile.getId())).collect(Collectors.toList()))) {
                return true;
            }
            //if we're here, there was no solution for the placement of tile at i,j
            positions[i][j] = null;
        }

        return false;
    }


    /**
     * Returns the set of tiles that can be placed at position i,j. That means that the borders of the images returned
     * will line up with the images adjacent to it in the tileGrid.
     *
     * @param allTiles
     * @param tileGrid
     * @param i
     * @param j
     * @param avail
     * @return
     */
    private List<ImageTile> getTilesForPosition(Map<Long, List<ImageTile>> allTiles, ImageTile[][] tileGrid, int i, int j, List<Long> avail) {
        List<ImageTile> candidates = allTiles.entrySet().stream().filter(e -> avail.contains(e.getKey())).map(Map.Entry::getValue).flatMap(List::stream).collect(Collectors.toList());
        if (i == 0 && j == 0) {
            return candidates;
        }
        List<ImageTile> tiles = new ArrayList<>();
        if (i == 0 && j > 0) {
            // if it's the top row, we only need to match on the left
            tiles.addAll(candidates.stream().filter(c -> c.matches(tileGrid[i][j - 1], ImageTile.Side.LEFT)).collect(Collectors.toList()));
        } else if (i > 0 && j == 0) {
            // if it's the first column, we only care about the top
            tiles.addAll(candidates.stream().filter(c -> c.matches(tileGrid[i - 1][j], ImageTile.Side.TOP)).collect(Collectors.toList()));
        } else {
            //for everything else, need to match top and left
            tiles.addAll(candidates.stream().filter(c -> c.matches(tileGrid[i][j - 1], ImageTile.Side.LEFT)).
                filter(c -> c.matches(tileGrid[i - 1][j], ImageTile.Side.TOP))
                .collect(Collectors.toList()));
        }
        return tiles;
    }

}
