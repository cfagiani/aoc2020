package aoc.model;

/**
 * Models movement of the ferry both as absolute movements and waypoint based movements.
 * Used in Day 12.
 */
public class Ship {

    private static final int[][] HEADINGS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};//N E S W

    private int headingIndex;
    private long nDist;
    private long eDist;
    private long waypointN;
    private long waypointE;

    public Ship() {
        this.headingIndex = 1;
        this.nDist = 0;
        this.eDist = 0;
        this.waypointE = 10;
        this.waypointN = 1;
    }

    public void move(String instruction) {
        char ins = instruction.charAt(0);
        int unit = Integer.parseInt(instruction.substring(1));
        switch (ins) {
            case 'F':
                nDist += unit * HEADINGS[headingIndex][0];
                eDist += unit * HEADINGS[headingIndex][1];
                break;
            case 'N':
                nDist += unit;
                break;
            case 'S':
                nDist -= unit;
                break;
            case 'E':
                eDist += unit;
                break;
            case 'W':
                eDist -= unit;
                break;
            case 'R':
                int stepsR = unit / 90;
                headingIndex = (headingIndex + stepsR) % 4;
                break;
            case 'L':
                int stepsL = unit / 90;
                headingIndex = headingIndex - stepsL;
                if (headingIndex < 0) {
                    headingIndex = HEADINGS.length + headingIndex;
                }
                break;
        }
    }

    public void moveWithWaypoints(String instruction) {
        char ins = instruction.charAt(0);
        int unit = Integer.parseInt(instruction.substring(1));
        switch (ins) {
            case 'F':
                nDist += unit * waypointN;
                eDist += unit * waypointE;
                break;
            case 'N':
                waypointN += unit;
                break;
            case 'S':
                waypointN -= unit;
                break;
            case 'E':
                waypointE += unit;
                break;
            case 'W':
                waypointE -= unit;
                break;
            case 'R':
                //3,1  90 -> 1,-3
                int stepsR = unit / 90;
                switch (stepsR) {
                    case 1:
                        long temp = 0 - waypointE;
                        waypointE = waypointN;
                        waypointN = temp;
                        break;
                    case 2:
                        waypointE = 0 - waypointE;
                        waypointN = 0 - waypointN;
                        break;
                    case 3:
                        long temp2 = 0 - waypointN;
                        waypointN = waypointE;
                        waypointE = temp2;
                        break;
                }
                break;
            case 'L':
                int stepsL = unit / 90;
                switch (stepsL) {
                    case 1:
                        long temp2 = 0 - waypointN;
                        waypointN = waypointE;
                        waypointE = temp2;
                        break;

                    case 2:
                        waypointE = 0 - waypointE;
                        waypointN = 0 - waypointN;
                        break;
                    case 3:
                        long temp = 0 - waypointE;
                        waypointE = waypointN;
                        waypointN = temp;
                        break;
                }
                break;
        }
    }

    /**
     * Calculates Manhattan distance from starting position.
     *
     * @return
     */
    public int getDistance() {
        return (int) (Math.abs(nDist) + Math.abs(eDist));
    }
}
