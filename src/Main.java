// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main( String[] args ) {

        Graph startingGraph = new Graph(2,1);
        startingGraph.generateMaze();
        startingGraph.verifyMaze();
        startingGraph.displayMaze();
    }
}