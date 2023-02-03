/* Termsweeper Class */
import java.util.Scanner;

public class Termsweeper {
    private static int sizeRow, sizeCol;
    private static Scanner sc;
    /* Main method - runs the game */

    public static void main(String args[]) {    
        boolean replay = true;

        while (replay){
            sc = new Scanner(System.in);

            // Create the board
            sizeRow = 16; sizeCol = 16;
            Board board = new Board(sizeRow, sizeCol);

            play(board);
            System.out.print("Replay? (if not \"Y\", game will close): ");
            sc.reset(); sc = new Scanner(System.in);
            
            String replayInput = sc.nextLine();
            if (!replayInput.toLowerCase().equals("y")){replay = false; sc.close();}
        }
    }

    public static void play(Board b){
        int inputRow = -5, inputCol = -5, inputAction = 0;
        int state = 1; // 0 is loss, 1 is solving, 2 is win
        while (state == 1){
            b.display();
            try{
                //sc.nextLine();
                System.out.print("\n[LOCATION] Coordinate (row): ");
                inputRow = sc.nextInt();
                System.out.print("[LOCATION] Coordinate (column): ");
                inputCol = sc.nextInt();
                System.out.print("[ACTION] {1} Check Tile | {2} Flag/Unflag ---> ");
                inputAction = sc.nextInt();
            } catch (Exception e){
                sc.nextLine();
                System.out.println("\n\033[43mYour input was invalid!\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
            }

            if (inputRow > sizeRow || inputCol > sizeCol || inputAction > 2){ // exceeds board size
                System.out.println("\n\033[43mYour input(s) exceeds limits!\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
            } else if (inputRow > 0 && inputCol > 0 && inputAction == 1){ // is valid, wants to check
                state = b.checkSquare(inputRow, inputCol, false);
            } else if (inputRow > 0 && inputCol > 0 && inputAction == 2){ // is valid, wants to flag
               b.flagSquare(inputRow, inputCol); 
            } else if (inputRow == 0 || inputCol == 0 || inputAction < 1){ // below board size
                System.out.println("\n\033[43mYour input(s) are invalid!\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
            }
        }

        String finalTime = b.getFinalTime();

        if (state == 0) {
            System.out.println("\n\033[41mYou lost!\033[0m");
        } else {
            System.out.println("\n\033[42mYou won!\033[0m");
        }
        System.out.println("Final time: "+finalTime+"\n\n\n\n");
    }
}