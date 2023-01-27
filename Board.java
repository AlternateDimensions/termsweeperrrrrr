public class Board {
    private static Tile[][] board;
    private static String[][] displayBoard;

    public Board(){}

    public void display(){
        for (String[] i : displayBoard){
            for (String j : i){
                System.out.print(j);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public void generate(int rows, int columns){
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
        while (bombs < 16){
            bombs = generateBombs();
        }

        // Create bomb values
        generateValues();

        // Create display board
        generateDisplayBoard();
    }

    private int generateBombs(){
        return 16;
    }

    private void generateValues(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                // EVERY INDEX check for bombs count if not a bomb
                if (board[i][j].getTrueValue() != -1){
                    // Bomb checking
                    int tempBombCount = 0;
                    for (int checkRow = -1; checkRow <= 1; checkRow++){
                        try {
                            for (int checkColumn = -1; checkColumn <= 1; checkColumn++){
                                try{
                                    if (checkRow != 0 && checkColumn != 0){ // everything but that spot
                                        if (board[checkRow][checkColumn].getTrueValue() == -1){
                                            tempBombCount++;
                                        }
                                    }
                                } catch (Exception ignore){}
                            }
                        } catch (Exception ignore){}
                    }
                    // Set bomb
                    board[i][j].setTrueValue(tempBombCount);
                }
            }
        }        
    }

    private void generateDisplayBoard(){
        String[][] newDisplayBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                newDisplayBoard[i][j] = board[i][j].getDisplayValue();
            }
        }
        displayBoard = newDisplayBoard;
    }
}
