import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private final int width;
    private final int height;
    private Map<Cell, List<Cell>> adjacencyList = new HashMap<>();
    private final Random rand = new Random();

    private static final int N = 1, S = 2, E = 4, W = 8;
    private static final int[] DIRECTIONS = {N, S, E, W};

    public Graph(int width, int height) {
        this.width = width;
        this.height = height;
        // Initialize the graph with all cells having no neighbors
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell();
                cell.setCoordinates(x, y);
                adjacencyList.put(cell, new ArrayList<>());
            }
        }
    }

    public void generateMaze() {
        System.out.println("Generating maze...");
        // Start with a random cell
        Cell startCell = getRandomCell();
        startCell.in = true;
        addFrontier(startCell);

        while (!allCellsIn()) {

            System.out.println("Cells in: " + adjacencyList.keySet().stream().filter(c -> c.in).count() + " / " + adjacencyList.size());

            Cell frontierCell = removeRandomFrontierCell();
            List<Cell> inNeighbors = getInNeighbors(frontierCell);

            if (!inNeighbors.isEmpty()) {
                Cell neighbor = inNeighbors.get(rand.nextInt(inNeighbors.size()));
                connectCells(neighbor, frontierCell);
                addFrontier(frontierCell);
            }
        }

        System.out.println("Maze generated!");
    }

    private Cell getRandomCell() {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);
        return findCell(x, y);
    }

    private void addFrontier(Cell cell) {
        int x = cell.x;
        int y = cell.y;
        for (int direction : DIRECTIONS) {
            int nx = x, ny = y;
            switch (direction) {
                case N: ny--; break;
                case S: ny++; break;
                case E: nx++; break;
                case W: nx--; break;
            }
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                Cell neighbor = findCell(nx, ny);
                if (!neighbor.in && !adjacencyList.get(neighbor).contains(cell)) {
                    adjacencyList.get(neighbor).add(cell);
                }
            }
        }
    }

    private List<Cell> getInNeighbors(Cell cell) {
        return adjacencyList.get(cell).stream().filter(c -> c.in).collect(Collectors.toList());
    }

    private void connectCells(Cell a, Cell b) {
        adjacencyList.get(a).add(b);
        adjacencyList.get(b).add(a);
        a.in = true;
        b.in = true;
    }

    private boolean allCellsIn() {
        return adjacencyList.keySet().stream().allMatch(c -> c.in);
    }

    private Cell removeRandomFrontierCell() {
        List<Cell> frontierCells = new ArrayList<>(adjacencyList.keySet().stream().filter(c -> !c.in && !adjacencyList.get(c).isEmpty()).toList());
        System.out.println("Frontier cells: " + frontierCells.size());
        return frontierCells.remove(rand.nextInt(frontierCells.size()));
    }

    private Cell findCell(int x, int y) {
        for (Cell cell : adjacencyList.keySet()) {
            if (cell.x == x && cell.y == y) {
                return cell;
            }
        }
        return null; // Should never happen
    }

    public int getWidth() {
        return width;
    }

    public Map<Cell, List<Cell>> getAdjacencyList() {
        return adjacencyList;
    }

    public void displayMaze() {
        System.out.print(" ");
        for (int i = 0; i < width * 2 - 1; i++) {
            System.out.print("_");
        }
        System.out.println();

        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                Cell cell = findCell(x, y);
                int finalY = y;
                System.out.print(adjacencyList.get(cell).stream().anyMatch(c -> c.y == finalY + 1) ? " " : "_");
                if (x < width - 1) {
                    Cell rightCell = findCell(x + 1, y);
                    System.out.print(adjacencyList.get(cell).contains(rightCell) ? " " : "|");
                } else {
                    System.out.print("|");
                }
            }
            System.out.println();
        }
    }
}
