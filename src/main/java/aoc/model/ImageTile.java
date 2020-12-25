package aoc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square image tile. Tiles are a 2d array of characters where '#' denotes an "on" pixel and '.' denotes
 * off. Tiles can be transformed by rotating and flipping.
 * <p>
 * Used in day 20.
 */
public class ImageTile {

    public enum Side {LEFT, RIGHT, TOP, BOTTOM}

    private static final int SIZE = 10;

    private Long id;
    private Character[][] state;

    public ImageTile(List<String> lines) {
        state = new Character[SIZE][SIZE];
        int start = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Tile")) {
                id = Long.parseLong(lines.get(i).split(" ")[1].replace(":", ""));
                start = i + 1;
                break;
            }
        }
        for (int i = start; i < lines.size(); i++) {
            state[i - start] = new Character[SIZE];
            for (int j = 0; j < lines.get(i).length(); j++) {
                state[i - start][j] = lines.get(i).charAt(j);
            }
        }
    }

    public ImageTile(long id, Character[][] state) {
        this.id = id;
        this.state = state;
    }

    /**
     * Generates new ImageTile instances based on all the possible orientations (i.e. rotations and flips).
     *
     * @return
     */
    public List<ImageTile> generateVariants() {
        List<ImageTile> tiles = new ArrayList<>();

        tiles.add(this);
        ImageTile vFlip = new ImageTile(this.id, this.flipVert());
        tiles.add(vFlip);

        ImageTile prev = this;
        for (int i = 0; i < 3; i++) {
            prev = new ImageTile(this.id, prev.rot90());
            tiles.add(prev);
        }
        prev = vFlip;
        for (int i = 0; i < 3; i++) {
            prev = new ImageTile(this.id, prev.rot90());
            tiles.add(prev);
        }
        return tiles;
    }


    /**
     * Returns new 2d array after rotating state by 90 degrees clockwise.
     *
     * @return
     */
    private Character[][] rot90() {
        Character[][] rotatedMatrix = new Character[state.length][state.length];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                rotatedMatrix[j][(state.length - 1) - i] = state[i][j];
            }
        }
        return rotatedMatrix;
    }

    /**
     * Returns new 2d array after flipping state vertically.
     *
     * @return
     */
    private Character[][] flipVert() {
        Character[][] flipped = new Character[state.length][state.length];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                flipped[state.length - 1 - i][j] = state[i][j];
            }
        }
        return flipped;
    }

    /**
     * Flips this imageTile.
     */
    public void flip() {
        this.state = flipVert();
    }

    /**
     * Rotates this image tile 90 degrees clockwise.
     */
    public void rotate() {
        this.state = rot90();
    }


    /**
     * Gets the value of a specific pixel.
     *
     * @param i
     * @param j
     * @return
     */
    public char getChar(int i, int j) {
        return state[i][j];
    }

    /**
     * Returns the entire state 2d array.
     *
     * @return
     */
    public Character[][] getState() {
        return this.state;
    }


    /**
     * Returns true if the other tile matches this tile when the other tile is lined up with the side of this tile.
     *
     * @param other
     * @param side
     * @return
     */
    public boolean matches(ImageTile other, Side side) {
        for (int i = 0; i < state.length; i++) {
            switch (side) {
                case TOP:
                    if (other.state[state.length - 1][i] != state[0][i]) {
                        return false;
                    }
                    break;
                case BOTTOM:
                    if (other.state[0][i] != state[state.length - 1][i]) {
                        return false;
                    }
                    break;
                case LEFT:
                    if (other.state[i][state.length - 1] != state[i][0]) {
                        return false;
                    }
                    break;
                case RIGHT:
                    if (other.state[i][0] != state[i][state.length - 1]) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void print() {

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                System.out.print(state[i][j]);
            }
            System.out.print("\n");
        }
    }
}
