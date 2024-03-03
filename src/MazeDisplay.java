import javax.swing.*;
import java.awt.*;

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
        Cell[][] grid = mazeGenerator.getGrid();
        int cellSize = 800 / mazeGenerator.getWidth(); // Calculate cell size based on maze width and panel size
        for (int y = 0; y < mazeGenerator.getHeight(); y++) {
            for (int x = 0; x < mazeGenerator.getWidth(); x++) {
                int cellX = x * cellSize;
                int cellY = y * cellSize;
                if (!grid[y][x].north) {
                    g.drawLine(cellX, cellY, cellX + cellSize, cellY);
                }
                if (!grid[y][x].south) {
                    g.drawLine(cellX, cellY + cellSize, cellX + cellSize, cellY + cellSize);
                }
                if (!grid[y][x].east) {
                    g.drawLine(cellX + cellSize, cellY, cellX + cellSize, cellY + cellSize);
                }
                if (!grid[y][x].west) {
                    g.drawLine(cellX, cellY, cellX, cellY + cellSize);
                }
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
        Graph maze = new Graph(16, 9); // Adjust size as needed
        maze.generateMaze();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(maze);
            }
        });
    }
}

