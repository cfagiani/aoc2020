package aoc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a set of ConwayCubes in either 3 or 4 dimensions. It is initialized with a single 2-d slice (regardless
 * of dimensionality). Every cycle, all cubes will be considered and toggled based on the following rules:
 * if it is active and either 2 or 3 of its neighbors are active, it will remain active, otherwise it will become inactive.
 * If it is inactive and 3 of its neighbors are active, it will become active.
 * <p>
 * Used in Day 17
 */
public class PocketDimension {

    private Map<Integer, Map<Integer, List<ConwayCube>>> hypercubes;
    private boolean useFourthDimension;

    /**
     * Initializes this dimension using either 3 or 4 dimensions. The list of cube strings is the list of cells
     * in a 2-d slice at z-index 0. Each line is a string consisting of either # or .
     * <p>
     * # denotes active whereas . denotes inactive.
     *
     * @param cubeStrings
     * @param useFourDimensions
     */
    public PocketDimension(List<String> cubeStrings, boolean useFourDimensions) {
        Map<Integer, List<ConwayCube>> cubes = new HashMap<>();
        hypercubes = new HashMap<>();
        useFourthDimension = useFourDimensions;
        List<ConwayCube> cubeList = new ArrayList<>();
        int z = 0;
        int w = 0;
        for (int y = 0; y < cubeStrings.size(); y++) {
            for (int x = 0; x < cubeStrings.get(y).length(); x++) {
                if (cubeStrings.get(y).charAt(x) == '#') {
                    int[] pos = new int[] {x, y, z, w};
                    cubeList.add(new ConwayCube(pos, cubeStrings.get(y).charAt(x)));
                }
            }
        }
        cubes.put(z, cubeList);
        hypercubes.put(w, cubes);

    }

    /**
     * Advances time by 1 unit. All cells are updated simultaneously.
     */
    public void advanceCycle() {
        // we only keep track of active nodes. Build a set of all the neighbors of all active nodes
        Map<Integer, Map<Integer, List<ConwayCube>>> nextCubes = new HashMap<>();
        Set<ConwayCube> cubesToConsider = new HashSet<>();
        for (Map<Integer, List<ConwayCube>> layerEntry : hypercubes.values()) {
            for (List<ConwayCube> layer : layerEntry.values()) {
                for (ConwayCube cube : layer) {
                    cubesToConsider.addAll(getNeigbors(cube));
                }
            }
        }
        // for each node that could possible change (the current active set and all their neighbors), evaluate them
        // using the rules
        for (ConwayCube cube : cubesToConsider) {
            List<ConwayCube> activeNeighbors = getNeigbors(cube).stream()
                .filter(c -> c.getValue() == '#').collect(Collectors.toList());
            if (cube.getValue() == '#' && (activeNeighbors.size() == 2 || activeNeighbors.size() == 3)) {
                // if it's active
                nextCubes.computeIfAbsent(cube.getDimPos(3), (k) -> new HashMap<>()).computeIfAbsent(cube.getDimPos(2), (k) -> new ArrayList<>()).add(new ConwayCube(cube.getPosition(), '#'));
            } else if (cube.getValue() == '.' && activeNeighbors.size() == 3) {
                // if it's inactive
                nextCubes.computeIfAbsent(cube.getDimPos(3), (k) -> new HashMap<>()).computeIfAbsent(cube.getDimPos(2), (k) -> new ArrayList<>()).add(new ConwayCube(cube.getPosition(), '#'));
            }
        }
        hypercubes = nextCubes;
    }

    /**
     * Returns a list of all the neighbors of the cube passed in (not including the cube itself).
     *
     * @param cube
     * @return
     */
    private List<ConwayCube> getNeigbors(ConwayCube cube) {
        List<ConwayCube> neighbors = new ArrayList<>();
        int wMin = useFourthDimension ? cube.getDimPos(3) - 1 : 0;
        int wMax = useFourthDimension ? cube.getDimPos(3) + 2 : 1;

        for (int x = cube.getDimPos(0) - 1; x < cube.getDimPos(0) + 2; x++) {
            for (int y = cube.getDimPos(1) - 1; y < cube.getDimPos(1) + 2; y++) {
                for (int z = cube.getDimPos(2) - 1; z < cube.getDimPos(2) + 2; z++) {
                    for (int w = wMin; w < wMax; w++) {
                        if (cube.getDimPos(0) == x && cube.getDimPos(1) == y &&
                            cube.getDimPos(2) == z && cube.getDimPos(3) == w) {
                            // don't include the cube itself
                            continue;
                        }
                        List<ConwayCube> layer = null;
                        Map<Integer, List<ConwayCube>> cubes = hypercubes.get(w);
                        if (cubes != null) {
                            layer = cubes.get(z);
                        }
                        if (layer == null) {
                            neighbors.add(new ConwayCube(new int[] {x, y, z, w}, '.'));
                        } else {
                            neighbors.add(findOrCreateCube(layer, x, y, z, w));
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Counts all active cubes in this pocketDimension.
     *
     * @return
     */
    public List<ConwayCube> getActiveCubes() {
        List<ConwayCube> active = new ArrayList<>();
        for (Map<Integer, List<ConwayCube>> cubes : hypercubes.values()) {
            cubes.values().stream().forEach(v -> active.addAll(v));
        }

        return active;
    }

    /**
     * Returns a cube at the position indicated or, if no cube exists, return a new inactive cube.
     *
     * @param cubeList
     * @param x
     * @param y
     * @param z
     * @param w
     * @return
     */
    private ConwayCube findOrCreateCube(List<ConwayCube> cubeList, int x, int y, int z, int w) {
        for (ConwayCube c : cubeList) {
            if (c.getDimPos(0) == x && c.getDimPos(1) == y && c.getDimPos(2) == z && c.getDimPos(3) == w) {
                return c;
            }
        }
        return new ConwayCube(new int[] {x, y, z, w}, '.');
    }


}
