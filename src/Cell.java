class Cell {
    int x, y;
    boolean in = false; // In the maze
    // Directions (N, S, E, W) are managed in adjacencyList in the Graph class

    public Cell setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}