/* Imports */
import java.util.ArrayList;

/* Termsweeper Class */
public class Termsweeper {
    private static int[][] board;
    /* Main method - runs the game */
    public static void main(String args[]) {
        System.out.println("new loop iteration");
        
        board = new int[][]{
            /*    0  1  2 */
            /*0*/{1, 0, 1},
            /*1*/{0, 0, 0},
            /*2*/{0, 0, 0}
        };
        int currentRow = 1;
        int currentColumn = 0;
        String bombs = checkField(new int[]{currentRow, currentColumn}); // tile display

        System.out.println(bombs+" bombs");
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

}