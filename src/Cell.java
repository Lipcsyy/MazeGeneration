class Cell {
    boolean north, south, east, west;
    boolean in = false;
    boolean frontier = false;
    int x, y;

    Cell setCoordinates( int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
}