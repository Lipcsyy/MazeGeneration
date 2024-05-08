import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MazeDisplay extends JPanel {
    private final Graph mazeGenerator;

    public MazeDisplay(Graph mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
        setPreferredSize(new Dimension(800, 800)); // Set the window size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
    }

    private void drawMaze(Graphics g) {
        Map<Cell, List<Cell>> adjacencyList = mazeGenerator.getAdjacencyList();
        int cellSize = 800 / mazeGenerator.getWidth(); // Calculate cell size based on maze width and panel size

        for (Cell cell : adjacencyList.keySet()) {
            int cellX = cell.x * cellSize;
            int cellY = cell.y * cellSize;

            // Determine if there are walls based on connectivity in the adjacency list
            boolean hasNorth = adjacencyList.get(cell).stream().anyMatch(c -> c.y == cell.y - 1);
            boolean hasSouth = adjacencyList.get(cell).stream().anyMatch(c -> c.y == cell.y + 1);
            boolean hasEast = adjacencyList.get(cell).stream().anyMatch(c -> c.x == cell.x + 1);
            boolean hasWest = adjacencyList.get(cell).stream().anyMatch(c -> c.x == cell.x - 1);

            if (!hasNorth) {
                g.drawLine(cellX, cellY, cellX + cellSize, cellY);
            }
            if (!hasSouth) {
                g.drawLine(cellX, cellY + cellSize, cellX + cellSize, cellY + cellSize);
            }
            if (!hasEast) {
                g.drawLine(cellX + cellSize, cellY, cellX + cellSize, cellY + cellSize);
            }
            if (!hasWest) {
                g.drawLine(cellX, cellY, cellX, cellY + cellSize);
            }
        }
    }

    public static void createAndShowGUI(Graph mazeGenerator) {
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MazeDisplay(mazeGenerator));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Graph maze = new Graph(20, 20); // Adjust size as needed
        maze.generateMaze();

        SwingUtilities.invokeLater(() -> createAndShowGUI(maze));
    }
}
