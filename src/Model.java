import java.awt.*;
import java.util.*;

public class Model {
    public final static int DIM_MIN = 3;
    public final static int DIM_MAX = 10;
    private final static float LEVEL = 100;

    private int dim;
    private int[][] grid;

    public Model() {
        dim = 4;
        init();
    }

    public int getDim() {
        return dim;
    }

    public void addDim() {
        if (dim < DIM_MAX) {
            dim++;
            init();
        }
    }

    public void removeDim() {
        if (dim > DIM_MIN) {
            dim--;
            init();
        }
    }

    public int[][] getGrid() {
        return grid;
    }

    public void init() {
        int x, y;

        grid = new int[dim][dim];
        for (y = 0; y < dim; y++)
            for (x = 0; x < dim; x++)
                grid[x][y] = y * dim + x;
    }

    public int move(Point coord) {
        int direction, x, y, emptyCell;

        x = coord.x;
        y = coord.y;
        direction = -1;
        emptyCell = dim * dim - 1;
        if (isMoveValid(x, y - 1))
            if (grid[x][y - 1] == emptyCell) {
                direction = 0;
                grid[x][y - 1] = grid[x][y];
            }
        if (isMoveValid(x + 1, y))
            if (grid[x + 1][y] == emptyCell) {
                direction = 1;
                grid[x + 1][y] = grid[x][y];
            }
        if (isMoveValid(x, y + 1))
            if (grid[x][y + 1] == emptyCell) {
                direction = 2;
                grid[x][y + 1] = grid[x][y];
            }
        if (isMoveValid(x - 1, y))
            if (grid[x - 1][y] == emptyCell) {
                direction = 3;
                grid[x - 1][y] = grid[x][y];
            }
        if (direction != -1)
            grid[x][y] = emptyCell;

        return direction;
    }

    public boolean isMoveValid(int x, int y) {
        return (x >= 0 && x < dim && y >= 0 && y < dim);
    }

    public boolean isGridCompleted() {
        boolean completed;
        int i;

        completed = true;
        for (i = 0; i < dim * dim && completed; i++)
            completed = grid[i % dim][i / dim] == i % (dim * dim);

        return completed;
    }

    public void shuffle() {
        int x, y, currx, curry;
        float step;
        //true -> x, false -> y
        boolean direction;
        int move;
        Random rand;

        rand = new Random();
        x = 0;
        y = 0;
        for (curry = 0; curry < dim; curry++)
            for (currx = 0; currx < dim; currx++)
                if (grid[currx][curry] == 15) {
                    x = currx;
                    y = curry;
                }
        for (step = 0; step < LEVEL; step += 0.01f) {
            direction = rand.nextBoolean();
            move = (rand.nextBoolean() ? 1 : -1);
            currx = x;
            curry = y;
            if (direction) //x move
                x += isMoveValid(currx + move, curry) ? move : -move;
            else //y move
                y += isMoveValid(currx, curry + move) ? move : -move;
            invertCells(currx, curry, x, y);
        }
    }

    public void invertCells(int x1, int y1, int x2, int y2) {
        int temp;

        temp = grid[x1][y1];
        grid[x1][y1] = grid[x2][y2];
        grid[x2][y2] = temp;
    }
}