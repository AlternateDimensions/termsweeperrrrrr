public class Board {
    private static Tile[][] board;
    private static String[][] displayBoard;

    public Board(int rows, int columns){
        // Create actual board
        Tile[][] newBoard = new Tile[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                newBoard[i][j] = new Tile(i, j);
            }
        }
        board = newBoard;
        
        // Create bombs
        int bombs = 0;
        double threshold = 0.08;
        while (bombs < 16){
            bombs = generateBombs(bombs, threshold);
        }

        // Create tile values
        generateValues();

        // Create display board
        updateDisplayBoard();
    }

    public void display(){
        System.out.print("\u001b[1J");
        for (String[] i : displayBoard){
            for (String j : i){
                System.out.print(j);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    private int generateBombs(int b, double t){
        int tempBombCount = b;
        for (Tile[] row : board){
            for (Tile tile : row){
                double bombGen = Math.random();
                if (bombGen <= t){
                    tile.setTrueValue(-1);
                    tempBombCount++;
                }
            }
        }
        return tempBombCount;
    }

    private void generateValues(){
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

    private void updateDisplayBoard(){
        String[][] newDisplayBoard = new String[board.length][board[0].length];
        updateDisplayBoardValues();
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                newDisplayBoard[i][j] = board[i][j].getDisplayValue();
            }
        }
        displayBoard = newDisplayBoard;
    }

    public void updateDisplayBoardValues(){
        for (Tile[] row : board){
            for (Tile t : row){
                t.setRevealed(true);
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
                } else if (t.getFlagged()){
                    t.setDisplayValue("[\033[34m⚑\033[0m]");
                } 
            }
        }
    }
}
