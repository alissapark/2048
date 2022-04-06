public class Tile {
    private int value;
    private int tileX;
    private int tileY;
    private boolean hasBeenCombined;

    public Tile(int val, int x, int y, boolean combo){
        value = val;
        tileX = x;
        tileY = y;
        hasBeenCombined = combo;
    }

    public int changeValue(){
        return value*2;
    }

    public void changeXValue(int x){
        tileX = x;
    }

    public void changeYValue(int y){
        tileY = y;
    }

    public void changeCombined(boolean combo){
        hasBeenCombined = combo;
    }

    public int getValue(){
        return value;
    }

    public int getX(){
        return tileX;
    }

    public int getY(){
        return tileY;
    }

    public boolean getCombo(){
        return hasBeenCombined;
    }

}
