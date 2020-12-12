package aoc.model;

/**
 * Airline seat. Used in Day 5.
 */
public class Seat {
    private static final int MAX_ROWS = 128;

    private int row;
    private int col;
    private int id;

    public Seat(String specifier) {
        this.row = processBinaryPartitioning(specifier.substring(0, 7));
        this.col = processBinaryPartitioning(specifier.substring(7));
    }

    public Seat(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getId() {
        return this.row * 8 + this.col;
    }


    private int processBinaryPartitioning(String specifier) {
        int curMin = 0;
        int curMax = (int) Math.pow(2, specifier.length())-1;

        for (int i = 0; i < specifier.length(); i++) {
            int split = (curMax - curMin+1) / 2;
            if (specifier.charAt(i) == 'F' || specifier.charAt(i) == 'L') {
                curMax -= split;
            } else {
                curMin += split;
            }
        }
        if (specifier.charAt(specifier.length() - 1) == 'F' || specifier.charAt(specifier.length() - 1) == 'L') {
            return curMin;
        } else {
            return curMax;
        }
    }
}
