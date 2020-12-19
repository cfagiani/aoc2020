package aoc.model;

import java.util.Arrays;

/**
 * Represents a cell in n-dimension space. A cell can have a single character value.
 * Used in Day 17.
 */
public class ConwayCube {
    private int[] position;
    private char value;

    public ConwayCube(int[] pos, char val) {
        position = pos;
        this.value = val;
    }

    public int getDimPos(int dim) {
        return position[dim];
    }

    public int[] getPosition() {
        return position;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConwayCube that = (ConwayCube) o;
        if (this.position.length != that.position.length) {
            return false;
        }
        for (int i = 0; i < this.position.length; i++) {
            if (this.position[i] != that.position[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(position);
    }
}
