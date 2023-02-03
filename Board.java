import java.util.ArrayList;

public class Board {
    private static Tile[][] board;
    private static String[][] displayBoard;
    private static long startTime;
    private static int time;
    private static String timeSeconds;
    private static int bombs;
    private static int totalBombs;
    private static int flags;
    
    public Board(int rows, int columns){
        // Create actual board
        startTime = System.currentTimeMillis();
        Tile[][] newBoard = new Tile[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                newBoard[i][j] = new Tile(i, j);
            }
        }
        board = newBoard;
        
        // Create bombs
        bombs = 0;
        flags = 0;
        double threshold = 0.08;
        while (bombs < (int) (0.15*(rows*columns))){
            bombs = generateBombs(bombs, threshold);
        }
        totalBombs = bombs;

        // Create tile values
        generateValues();

        // Create display board
        updateDisplayBoard();
    }

    public void display(){ // prints out the display values of the board
        updateDisplayBoard(); // make sure board is updated
        time = (int) ((System.currentTimeMillis()-startTime)/60000);
        System.out.println("\u001b[2J\u001b[1;2H\033[3mFrankBrian presents: \033[4mTermsweeper\033[0m   |   Time: "+time+" min\n");
        int colref = 0;
        System.out.print("   ");
        for (int temp = 1; temp <= displayBoard.length; temp++){
            if (temp<10) {System.out.print(" "+temp+"  ");} else {System.out.print(""+temp+"  ");}

        }
        System.out.println("");
        for (String[] i : displayBoard){
            colref++;
            if (colref<10) {System.out.print(" "+colref+" ");} else {System.out.print(colref+" ");}
            for (String j : i){
                System.out.print(j);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    private int generateBombs(int b, double t){
        // uses value t as a chance for tile to be bomb
        int tempBombCount = b;
        for (Tile[] row : board){
            for (Tile tile : row){
                double bombGen = Math.random();
                if (bombGen <= t && tile.getTrueValue() != -1){
                    tile.setTrueValue(-1);
                    tempBombCount++;
                }
            }
        }
        return tempBombCount;
    }

    private void generateValues(){ // generates the true value for each tile
        for (Tile[] row : board){ // For each row
            for (Tile t : row){ // For each tile
                int tempBombCount = 0;
                int currentRow = t.getIndex()[0]; 
                int currentCol = t.getIndex()[1];

                if (t.getTrueValue() != -1){ // If current tile isn't a bomb
                    for (int checkRow = -1; checkRow <= 1; checkRow++){ // every row -1 to +1
                        for (int checkColumn = -1; checkColumn <= 1; checkColumn++){ // every tile -1 to +1
                            try { // if out of range, ignore
                                if (board[currentRow+checkRow][currentCol+checkColumn].getTrueValue() == -1){ // Check if it's a bomb. If checking current, it would not matter as it can't be a bomb
                                    tempBombCount++;

                                }
                            } catch (Exception ignore){}
                        }
                    }
                    t.setTrueValue(tempBombCount);
                } else {
                    t.setTrueValue(-1);
                }
            }
        }
    }

    private void updateDisplayBoard(){ // updates String 2d array with display values
        String[][] newDisplayBoard = new String[board.length][board[0].length];
        updateDisplayBoardValues();
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                newDisplayBoard[i][j] = board[i][j].getDisplayValue();
            }
        }
        displayBoard = newDisplayBoard;
    }

    private void updateDisplayBoardValues(){ // updates the values based on the status of the tiles. Color the numbers also
        for (Tile[] row : board){
            for (Tile t : row){
                if (t.getRevealed()){ // if revealed
                    if (t.getTrueValue() == -1){ // if bomb
                        t.setDisplayValue("[\033[31mX\033[0m]");
                    } else if (t.getTrueValue() == 0) {
                        t.setDisplayValue("[ ]");
                    } else if (t.getTrueValue() < 3){
                        t.setDisplayValue("[\033[32m"+t.getTrueValue()+"\033[0m]");
                    } else if (t.getTrueValue() < 5){
                        t.setDisplayValue("[\033[36m"+t.getTrueValue()+"\033[0m]");
                    } else if (t.getTrueValue() < 7){
                        t.setDisplayValue("[\033[35m"+t.getTrueValue()+"\033[0m]");
                    } else{
                        t.setDisplayValue("[\033[33m"+t.getTrueValue()+"\033[0m]");
                    }
                } else if (t.getFlagged()){ // else if flagged
                    t.setDisplayValue("[\033[34mF\033[0m]");
                } 
            }
        }
    }

    public int checkSquare(int r, int c, boolean ignoreCheck){ // handles checking the square for win/loss and then returning status of game
        // ignoreCheck if the check for bomb/not bomb is needed or not
        if (!ignoreCheck){
            board[r-1][c-1].setRevealed(true);
            if (board[r-1][c-1].getFlagged()){
                flags--;
            }
            if (board[r-1][c-1].getTrueValue() == -1){
                return 0;
            }else if (board[r-1][c-1].getTrueValue() == 0){
                recursiveSearch(r-1, c-1);
            }
        }
        
        // checks win condition
        if (bombs == 0 && flags == totalBombs){
            return 2;
        }

        return 1;
    }

    public int flagSquare(int r, int c){ // handles flagging and then also checks win/loss
        boolean isFlagged = board[r-1][c-1].getFlagged(); // flag status of tile
        if (isFlagged){ // is flagged, unflag
            if (board[r-1][c-1].unflag()) {
                flags--;
                if (board[r-1][c-1].getTrueValue() == -1){
                    bombs++;
                }
            }
        } else { // not flagged, flag
            if (board[r-1][c-1].flag()) {
                flags++;
                if (board[r-1][c-1].getTrueValue() == -1){
                    bombs--;
                }
            }
        }
        // checks the game status without checking current square
        int gameStatus = checkSquare(r, c, true);
        return gameStatus;
    }
    

    public String getFinalTime(){ // calculates how long the game has gone on for 
        time = (int) ((System.currentTimeMillis()-startTime)/60000);
        timeSeconds = ((int) ((System.currentTimeMillis()-startTime)/1000))%60 < 10 ? "0"+String.valueOf(((int) ((System.currentTimeMillis()-startTime)/1000))%60) : String.valueOf(((int) ((System.currentTimeMillis()-startTime)/1000))%60);
        return (time+":"+timeSeconds);
    }

    private void recursiveSearch(int r, int c){ // recursively searches the board for 0s. If found, add that tile to tiles to check.
        ArrayList<int[]> indicesToCheck = new ArrayList<int[]>();
        ArrayList<int[]> indicesChecked = new ArrayList<int[]>();

        indicesToCheck.add(board[r][c].getIndex());

        while (indicesToCheck.size() > 0){
            int[][] indices = new int[][]{
                {0,1},
                {0,-1},
                {1,0},
                {-1,0},
                {-1,-1},
                {-1,1},
                {1, -1},
                {1, 1}
            };

            for (int[] index : indices){
                try{
                    Tile tempTile = board[indicesToCheck.get(0)[0]+index[0]][indicesToCheck.get(0)[1]+index[1]];

                    if (indicesChecked.contains(tempTile.getIndex()) == false){
                        if (tempTile.getTrueValue() == 0){ // is an empty space, add to the check list and reveal
                            indicesToCheck.add(tempTile.getIndex());
                            board[indicesToCheck.get(0)[0]+index[0]][indicesToCheck.get(0)[1]+index[1]].setRevealed(true);
                        } else if (tempTile.getTrueValue() > 0){  // is a number, reveal
                            indicesChecked.add(tempTile.getIndex());
                            board[indicesToCheck.get(0)[0]+index[0]][indicesToCheck.get(0)[1]+index[1]].setRevealed(true);
                        } else { // else, add to the checked list
                            indicesChecked.add(tempTile.getIndex());
                        }
                    }
                } catch (Exception ignore){}
        }

        indicesChecked.add(indicesToCheck.remove(0));
        }
    }
}
