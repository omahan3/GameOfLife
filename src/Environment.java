import java.util.Arrays;

public class Environment {
    static final boolean ALIVE = true;
    static final boolean DEAD = false;
    private final Cell[][] population;
    private final int height;
    private final int width;

    public Environment(int height, int width) {
        population = new Cell[height][width];
        this.height = height;
        this.width = width;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                population[i][j] = new Cell(DEAD, this, i, j);
            }
        }
    }

    public Environment(Cell[][] c) {
        this.height = c.length;
        this.width = c[0].length;

        this.population = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                population[i][j] = c[i][j].getCellCopy(this);
            }
        }
    }

    public Cell getCell(int vertical, int horizontal) {
        return population[vertical][horizontal];
    }

    public boolean isWithinBoundsV(int vert) {
        return (vert >= 0) && (vert < height);
    }

    public boolean isWithinBoundsH(int hor) {
        return (hor >= 0) && (hor < width);
    }

    private void updateCellStatus(int vertical, int horizontal, Cell[][] cells) {
        int numLiveCells = cells[vertical][horizontal].getNumLiveNeighbors();

        if (numLiveCells < 2 || numLiveCells > 3) {
            population[vertical][horizontal].setStatus(DEAD);
        } else if (numLiveCells == 3) {
            population[vertical][horizontal].setStatus(ALIVE);
        }
    }

    public void updatePopulation() {
        Environment snapshot = new Environment(population);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                updateCellStatus(i, j, snapshot.population);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder state = new StringBuilder();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                state.append(population[i][j].toString());
            }
            state.append("\n");
        }
        return state.toString();

    }
}
