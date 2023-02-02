public class Tile {
    private String displayValue; // Store in object, use in main
    private boolean revealed; // Store in object, use in object
    private int trueValue; // Store in object, use in main+object
    private boolean flagged; // Store in object, use in main+object
    private int[] index; // store in object, use in main


    public Tile(int row, int col){
        displayValue = "[-]";
        revealed = false;
        trueValue = 0;
        flagged = false;
        index = new int[]{row, col};
    }

    public String getDisplayValue(){
        return displayValue;
    }

    public void setDisplayValue(String dv){
        displayValue = dv;
    }

    public int getTrueValue(){
        return trueValue;
    }

    public void setTrueValue(int tv){
        trueValue = tv;
    }

    public boolean getRevealed(){
        return revealed;
    }

    public void setRevealed(boolean r){
        revealed = r;
        if (revealed){
            flagged = false;
        }
    }

    public boolean getFlagged(){
        return flagged;
    }
    
    public int[] getIndex(){
        return index;
    }

    public boolean flag(){
        if (!revealed){flagged = true; return true;} return false; // Flag only if not revealed
    }

    public boolean unflag(){
        if (flagged) {flagged = false; return true;} return false; // Unflag ONLY if flagged
    }

}
