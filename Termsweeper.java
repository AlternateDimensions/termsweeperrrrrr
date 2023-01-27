/* Termsweeper Class */
public class Termsweeper {
    /* Main method - runs the game */
    public static void main(String args[]) {    
        Board board = new Board();    
        board.generate(16,16);
        board.display();
    }
}