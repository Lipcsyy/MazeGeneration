import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graph {
    private final int width;
    private final int height;
    private final Cell[][] grid;
    private final List<Cell> frontier = new ArrayList<>();
    private final Random rand = new Random();

    private static final int N = 1, S = 2, E = 4, W = 8;
    private static final int[] DIRECTIONS = {N, S, E, W};

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell();
            }
        }
    }

    public void generateMaze() {
        // Start with a random cell
        int startY = rand.nextInt(height);
        int startX = rand.nextInt(width);

        //this is where the maze begins
        grid[startY][startX].in = true;

        //we need to add it's negihbors to the frontier
        addFrontier(startX, startY);

        //We continue to add cells to the maze until the frontier is empty
        while (!frontier.isEmpty()) {

            //We remove a random cell from the frontier randomly
            Cell frontierCell = frontier.remove(rand.nextInt(frontier.size()));

            //We find the neighbors of the frontier cell that are in the maze
            List<Cell> inNeighbors = getInNeighbors(frontierCell.x, frontierCell.y);

            //If there are neighbors in the maze we connect the frontier cell to one of them
            if (!inNeighbors.isEmpty()) {
                Cell neighbor = inNeighbors.get(rand.nextInt(inNeighbors.size()));
                connectCells(neighbor, frontierCell);
            }

            frontierCell.in = true; //We mark the frontier cell as in the maze
            addFrontier(frontierCell.x, frontierCell.y); //Start again
        }
    }

    private void addFrontier(int x, int y) {
        for (int direction : DIRECTIONS) {

            int nx = x, ny = y;

            switch (direction) {
                case N: ny--; break;
                case S: ny++; break;
                case E: nx++; break;
                case W: nx--; break;
            }
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && !grid[ny][nx].in && !grid[ny][nx].frontier) {
                grid[ny][nx].frontier = true;
                frontier.add(grid[ny][nx].setCoordinates(nx, ny));
            }
        }
    }

    private List<Cell> getInNeighbors(int x, int y) {

        //We check the neighbors of a cell that are in the maze in all directions

        List<Cell> neighbors = new ArrayList<>();

        for (int direction : DIRECTIONS) {
            int nx = x, ny = y;

            switch (direction) {
                case N: ny--; break;
                case S: ny++; break;
                case E: nx++; break;
                case W: nx--; break;
            }

            if (nx >= 0 && nx < width && ny >= 0 && ny < height && grid[ny][nx].in) {
                neighbors.add(grid[ny][nx]);
            }
        }
        return neighbors;
    }

    private void connectCells(Cell a, Cell b) {
        if (a.x == b.x) {
            if (a.y < b.y) {
                a.south = true;
                b.north = true;
            } else {
                a.north = true;
                b.south = true;
            }
        } else if (a.y == b.y) {
            if (a.x < b.x) {
                a.east = true;
                b.west = true;
            } else {
                a.west = true;
                b.east = true;
            }
        }
    }

    public void displayMaze() {
        // Top border
        for (int i = 0; i < width; i++) {
            System.out.print("+-");
        }
        System.out.println("+");

        // Each row
        for (int y = 0; y < height; y++) {
            // West border
            System.out.print("|");

            for (int x = 0; x < width; x++) {
                // South wall
                System.out.print(grid[y][x].south ? " " : "_");

                // East wall: print a space if there's no wall, or a '|' if there is a wall
                if (grid[y][x].east) {
                    System.out.print((grid[y][x].south || (y + 1 < height && grid[y + 1][x].east)) ? " " : "_");
                } else {
                    System.out.print("|");
                }
            }

            System.out.println();
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void verifyMaze() {
        boolean[][] visited = new boolean[height][width];
        dfs(0, 0, visited); // Starting from the top-left cell

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!visited[y][x]) {
                    System.out.println("Unreachable cell found at: " + x + ", " + y);
                    return;
                }
            }
        }
        System.out.println("All cells are reachable.");
    }

    private void dfs(int x, int y, boolean[][] visited) {

        if (x < 0 || x >= width || y < 0 || y >= height || visited[y][x]) return;

        visited[y][x] = true;

        if (grid[y][x].north) dfs(x, y - 1, visited);
        if (grid[y][x].south) dfs(x, y + 1, visited);
        if (grid[y][x].east) dfs(x + 1, y, visited);
        if (grid[y][x].west) dfs(x - 1, y, visited);
    }

}
