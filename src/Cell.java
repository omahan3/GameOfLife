
/*
A Cell has a status (alive or dead, i.e. true or false), a home (of type Environment) and integer
coordinates within its home.

The Cell class has one constructor and a bunch of instance methods.
• The Cell constructor initializes all the attributes of this Cell with the values of its four parameters:
a boolean status, an Environment, a vertical coordinate, a horizontal coordinate. The coordinates
are integers.
*/

public class Cell {

    static final boolean ALIVE = true;
    static final boolean DEAD = false;

    private boolean status; // true if the cell is alive
    private final Environment home;
    private final int vertical; // vertical coordinate
    private final int horizontal; // horizontal coordinate

    public Cell (boolean status, Environment e, int vert, int hor) {
        this.status = status;
        home = e;
        vertical = vert;
        horizontal = hor;
    }

    public boolean isAlive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNumLiveNeighbors() {
        int numLiveNeighbors = 0;
        if (home.isWithinBoundsH(horizontal - 1)) {
            if (home.getCell(vertical, horizontal - 1).isAlive()) {
                numLiveNeighbors++;
            }
            if (home.isWithinBoundsV(vertical - 1) &&
                    home.getCell(vertical - 1, horizontal - 1).isAlive()) {
                numLiveNeighbors++;
            }
            if (home.isWithinBoundsV(vertical + 1) &&
                    home.getCell(vertical + 1, horizontal - 1).isAlive()) {
                numLiveNeighbors++;
            }
        }
        if (home.isWithinBoundsH(horizontal + 1)) {
            if (home.getCell(vertical, horizontal + 1).isAlive()) {
                numLiveNeighbors++;
            }
            if (home.isWithinBoundsV(vertical - 1) &&
                    home.getCell(vertical - 1, horizontal + 1).isAlive()) {
                numLiveNeighbors++;
            }
            if (home.isWithinBoundsV(vertical + 1) &&
                    home.getCell(vertical + 1, horizontal + 1).isAlive()) {
                numLiveNeighbors++;
            }
        }
        if (home.isWithinBoundsV(vertical - 1) &&
                home.getCell(vertical - 1, horizontal).isAlive()) {
            numLiveNeighbors++;
        }
        if (home.isWithinBoundsV(vertical + 1) &&
                home.getCell(vertical + 1, horizontal).isAlive()) {
            numLiveNeighbors++;
        }

        return numLiveNeighbors;
    }

    public Cell getCellCopy(Environment e) {
        return new Cell(this.status, e, this.vertical, this.horizontal);
    }

    public String toString() {
        return status ? "o" : ".";
    }
}
