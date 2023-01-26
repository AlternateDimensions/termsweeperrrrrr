public class Tile {
    private String displayValue; // Store in object, use in main
    private boolean revealed; // Store in object, use in object
    private int trueValue; // Store in object, use in main+object
    private boolean flagged; // Store in object, use in main+object
    private int[] index; // store in object, use in main


    public Tile(int v, int col, int row){
        displayValue = "";
        revealed = false;
        trueValue = v;
        flagged = false;
        index = new int[]{row, col};
    }

    public String getDisplayValue(){
        return displayValue;
    }

    public int getTrueValue(){
        return trueValue;
    }

    public int[] getIndex(){
        return index;
    }

    public void flag(){
        flagged = true;
        if (!displayValue.equals("U")){displayValue = "⚑";} // Flag only if unknown
    }

    public void unflag(){
        if (flagged) { // Unflag ONLY if flagged
            flagged = false; 
            if (!revealed){displayValue = "U";} // Set unknown if it was not revealed
            else {displayValue = String.valueOf(trueValue);} // Set to value if known
        }
    }

}