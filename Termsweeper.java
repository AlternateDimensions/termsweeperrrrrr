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
            sizeRow = 0; sizeCol = 0;

            try{
                // give player the option to make board size
                //sc.nextLine();
                System.out.print("\u001b[2J\u001b[1;2H\033[3mFrankBrian presents: \033[4mTermsweeper\n\033[0m\n[ACTION] Number of rows: ");
                sizeRow = sc.nextInt();
                System.out.print("[ACTION] Number of columns: ");
                sizeCol = sc.nextInt();
                if (sizeRow == 0 || sizeCol == 0){
                    throw new IllegalArgumentException();
                }
            } catch (Exception e){
                //if not integer, call them out
                sc.nextLine();
                System.out.println("\n\033[43mYour input(s) (and your opinion) was invalid! Defaulting to value (16,16)\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
                sizeRow = 16; sizeCol = 16;
            }

            Board board = new Board(sizeRow, sizeCol);
            //initiates the game
            play(board);
            //handles replay
            System.out.print("Replay? (if not \"Y\", game will close): ");
            sc.reset(); sc = new Scanner(System.in);
            
            String replayInput = sc.nextLine();
            if (!replayInput.toLowerCase().equals("y")){replay = false; sc.close();}
        }
    }

    public static void play(Board b){
        int inputRow = -5, inputCol = -5, inputAction = 0;
        int state = 1; // 0 is loss, 1 is solving, 2 is win

        // keep playing until loss/win
        while (state == 1){
            b.display();
            try{
                // get inputs
                //sc.nextLine();
                System.out.print("\n[LOCATION] Coordinate (row): ");
                inputRow = sc.nextInt();
                System.out.print("[LOCATION] Coordinate (column): ");
                inputCol = sc.nextInt();
                System.out.print("[ACTION] {1} Check Tile | {2} Flag/Unflag ---> ");
                inputAction = sc.nextInt();
            } catch (Exception e){
                // invalid input
                sc.nextLine();
                System.out.println("\n\033[43mYour input was invalid!\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
            }

            // Handle input
            if (inputRow > sizeRow || inputCol > sizeCol || inputAction > 2){ // exceeds board size
                System.out.println("\n\033[43mYour input(s) exceeds limits!\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
            } else if (inputRow > 0 && inputCol > 0 && inputAction == 1){ // is valid, wants to check
                state = b.checkSquare(inputRow, inputCol, false); // state changes when checked
            } else if (inputRow > 0 && inputCol > 0 && inputAction == 2){ // is valid, wants to flag
               state = b.flagSquare(inputRow, inputCol); 
            } else if (inputRow <= 0 || inputCol <= 0 || inputAction < 1){ // below board size
                System.out.println("\n\033[43mYour input(s) are invalid!\033[0m");
                try{Thread.sleep(3000);}catch(Exception ignore){}
            }
        }

        String finalTime = b.getFinalTime(); // get final time for display

        if (state == 0) { // handle win/loss status
            System.out.println("\n\033[41mYou lost!\033[0m");
        } else {
            System.out.println("\n\033[42mYou won!\033[0m");
        }
        System.out.println("Final time: "+finalTime+"\n\n\n\n");
    }
}