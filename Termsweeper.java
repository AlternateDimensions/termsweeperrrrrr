/* Imports */
import java.util.ArrayList;

/* Termsweeper Class */
public class Termsweeper {
    private static Tile[][] board;

    /* Main method - runs the game */
    public static void main(String args[]) {        
        generateBoard(16,16);
        int bombs = 0;
        while (bombs < 16){
            bombs = generateBombs();
        }


    }

    private static void generateBoard(int rows, int columns){
        Tile[][] newBoard = new Tile[rows][columns];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = new Tile(i, j);
            }
        }
        board = newBoard;
    }

    private static void generateBombs(){
        for
    }

    private static String checkField(int[] selectedBox){
        int tempBombCount = 0;

        for (int checkRow = -1; checkRow <= 1; checkRow++){
            System.out.print("\nRow "+(checkRow+selectedBox[0]));
            try {
                for (int checkColumn = -1; checkColumn <= 1; checkColumn++){
                    try{
                        System.out.print("\nColumn "+(selectedBox[1]+checkColumn));
                        if (board[selectedBox[1] + checkRow][selectedBox[1] + checkColumn] == 1){
                            // is a bomb
                            tempBombCount++;
                        }
                    } catch (Exception ignore){System.out.print(" INVALID");}
                }
            } catch (Exception ignore){System.out.print(" INVALID");}
        }

        if (tempBombCount == 0){
            return " ";
        } else {
            return String.valueOf(tempBombCount);
        }
    }
    */
}