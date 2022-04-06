// 2048 program

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Board extends JPanel{
    // global variables
    static final int SCREEN_WIDTH = 450;
    static final int SCREEN_HEIGHT = 500;
    Random random = new Random();
    boolean running;
    char direction;
    Tile[][] tileBoard = new Tile[4][4];
    int tileCounter = 0;

    // creates a game window
    Board(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        makeBoard();
        newTile();
        running = true;
    }

    // initializes the board
    public void makeBoard(){
        for(int i = 0; i<4; i++){
            for(int j=0; j<4; j++){
                tileBoard[i][j] = null;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.DARK_GRAY);
        g.drawRect(25, 75, 400, 400);
        g.drawLine(125, 75, 125, 475);
        g.drawLine(225, 75, 225, 475);
        g.drawLine(325, 75, 325, 475);
        g.drawLine(25, 175, 425, 175);
        g.drawLine(25, 275, 425, 275);
        g.drawLine(25, 375, 425, 375);

        draw(g);
    }
     
    public void draw (Graphics g){
        // something to set the color of tile to match the number it has
        for(int i = 0; i < 4; i++){
            for(int j = 0; j< 4; j++){
                if(tileBoard[i][j] != null){
                    int val = tileBoard[i][j].getValue();
                    if(val == 2){
                        g.setColor(new Color(225, 242, 255));
                    }else if(val == 4){
                        g.setColor(new Color(199, 206, 211));
                    }else if(val == 8){
                        g.setColor(new Color(197, 229, 255));
                    }else if(val == 16){
                        g.setColor(new Color(183, 201, 214));
                    }else if(val == 32){
                        g.setColor(new Color(174, 218, 255));
                    }else if(val == 64){
                        g.setColor(new Color(168, 196, 216));
                    }else if(val == 128){
                        g.setColor(new Color(151, 209, 255));
                    }else if(val == 256){
                        g.setColor(new Color(143, 184, 214));
                    }else if(val == 512){
                        g.setColor(new Color(115, 192, 255));
                    }else if(val == 1028){
                        g.setColor(new Color(118, 172, 211));
                    }else {
                        g.setColor(new Color(82, 178, 255));
                    }

                    // fills the tile with the color and puts the value of the tile in the center
                    g.fillRect(tileBoard[i][j].getX() + 2, tileBoard[i][j].getY() + 2, 96, 96);
                    g.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
                    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setColor(Color.black);
                    FontMetrics metrics = getFontMetrics(g.getFont());
                    metrics.getAscent();
                    String value = Integer.toString(tileBoard[i][j].getValue());
                    g.drawString(value, tileBoard[i][j].getX() + 45, tileBoard[i][j].getY() +50);
                }
                // displays the score at the top of the panel
                g.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Color.black);
                FontMetrics metrics = getFontMetrics(g.getFont());
                metrics.getAscent();
                String score = Integer.toString(getScore());
                g.drawString("score: " + score, 190, 30);
            }
        }
    }

    // creates a new tile in an empty space on the board
    public void newTile(){
        int[] xValues = {25, 125, 225, 325};
        int[] yValues = {75, 175, 275, 375};

        int x = 25;
        int y = 75;
        int randomx = 0;
        int randomy = 0;
        int value = 2;

        boolean spaceEmpty = false;
        do{
            // generates a randome index to choose a random x,y coordinate
            randomx = random.nextInt(4);
            randomy = random.nextInt(4);
            x = xValues[randomx];
            y = yValues[randomy];
            // assigns a random value of 2 or 4 to the new tile
            value = (random.nextInt(2) + 1) * 2;

            spaceEmpty = checkSpace(x, y);
        }while(spaceEmpty == false);


        Tile tile = new Tile(value, x, y, false);
        tileBoard[randomy][randomx] = tile;
    }

    public boolean checkSpace(int x, int y){
        x = changeXIndex(x);
        y = changeYIndex(y);

        // if that spot is not empty, return false
        if(tileBoard[y][x] != null){
            return false;
        }
        
        // if there is no tile in that spot, return true
        return true;
    }

    public void move(){
        switch (direction){
            case 'D':
                for(int a = 0; a < 3; a++){
                    // goes through the matrix from bottom to top
                    for(int i = 3; i >= 0; i--){
                        for(int j = 0; j < 4; j++){
                            // if there is a tile in that spot
                            if (tileBoard[i][j] != null ){
                                int x = tileBoard[i][j].getX();
                                int y = tileBoard[i][j].getY();
    
                                // variable for the new coordinates of the tile after it moves
                                int moveToY = y + 100;
                                int index;
    
                                // makes sure the tile isn't in the last position
                                if( moveToY > 400)
                                {
                                    break;
                                }
                                //checks if the next space down is empty
                                boolean emptySpaceD = checkSpace(x, moveToY);

                                if(emptySpaceD){
                                    // if the space down is empty, move the tile down one spot
                                    int xIndex = changeXIndex(x);
                                    int yIndex = changeYIndex(y);
                                    
                                    // obtain all values of the old tile and make a new one in the new spot
                                    if(yIndex<3)
                                    {
                                        Tile tile = tileBoard[yIndex][xIndex];
                                        tile.changeYValue(moveToY);
                                        tileBoard[yIndex + 1][xIndex] = tile;
                                        tileBoard[yIndex][xIndex] = null;
                                    }
                                    index = i + 1;
                                }
                                else{
                                    moveToY = y;
                                    index = i;
                                }
                                
                                if(i < 3 && !tileBoard[index][j].getCombo()){
                                    // checks if the tile and the tile above it are the same
                                    boolean sameTile = checkSameTile(x, moveToY, direction);
    
                                    if(sameTile){
                                        // move the current tile down one and combine the two tiles
                                        int yIndex = changeYIndex(moveToY +100);
                                        int xIndex = changeXIndex(x);
                                        Tile ntile = new Tile(tileBoard[yIndex][xIndex].changeValue(), x, moveToY + 100, true);
                                        tileBoard[yIndex][xIndex] = ntile;
                                        // remove the tile from the previous spot
                                        tileBoard[yIndex - 1][xIndex] = null;
                                    }
                                }
                            }
                        }
                    }
                }
                
                resetCombo();
                break;
            case 'U':
                for(int a = 0; a < 3; a++){
                    for(int i = 0; i < 4; i++){
                        for(int j = 3; j >= 0; j--){
                            if (tileBoard[i][j] != null ){
                                int x = tileBoard[i][j].getX();
                                int y = tileBoard[i][j].getY();

                                // variable for the new coordinates of the tile after it moves
                                int moveToY = y - 100;
                                int index;

                                // sees how far the tile can be moved up before colliding
                                if( moveToY < 0 )
                                {
                                    break;
                                }
                                boolean emptySpaceD = checkSpace(x, moveToY);
                                if(emptySpaceD){
                                    // if the space up is empty, move the tile up one spot
                                    int xIndex = changeXIndex(x);
                                    int yIndex = changeYIndex(y);
                                    if(yIndex>0)
                                    {
                                        Tile tile = tileBoard[yIndex][xIndex];
                                        tile.changeYValue(moveToY);
                                        tileBoard[yIndex][xIndex] = null;
                                        tileBoard[yIndex - 1][xIndex] = tile;
                                    }
                                    index = i - 1;
                                }
                                else{
                                    moveToY = y;
                                    index = i;
                                }
                                
                                if(i > 0 && !tileBoard[index][j].getCombo()){
                                    // checks if the tile and the tile above it are the same
                                    boolean sameTile = checkSameTile(x, moveToY, direction);

                                    if(sameTile){
                                        // move the current tile up one and combine the two tiles
                                        int yIndex = changeYIndex(moveToY -100);
                                        int xIndex = changeXIndex(x);
                                        Tile ntile = new Tile(tileBoard[yIndex][xIndex].getValue()*2, x, moveToY - 100, true);
                                        tileBoard[yIndex][xIndex] = ntile;
                                        // remove the tile from the previous spot
                                        tileBoard[yIndex + 1][xIndex] = null;
                                    }
                                }
                            }
                        }
                    }
                }
                
                resetCombo();
                break;
            case 'R':
                for(int a = 0; a< 3; a++){
                    for(int j = 3; j >= 0; j--){
                        for(int i = 0; i < 4; i++){
                            if (tileBoard[i][j] != null ){
                                int x = tileBoard[i][j].getX();
                                int y = tileBoard[i][j].getY();
    
                                // variable for the new coordinates of the tile after it moves
                                int moveToX = x + 100;
                                int index;
    
                                // sees how far the tile can be moved right before colliding
                                if( moveToX > 400)
                                {
                                    break;
                                }
                                boolean emptySpaceD = checkSpace(moveToX, y);
                                if(emptySpaceD){
                                    // if the space to the right is empty, move the tile right one spot
                                    int xIndex = changeXIndex(x);
                                    int yIndex = changeYIndex(y);

                                    if(xIndex<3)
                                    {
                                        Tile tile = tileBoard[yIndex][xIndex];
                                        tile.changeXValue(moveToX);
                                        tileBoard[yIndex][xIndex + 1] = tile;
                                        tileBoard[yIndex][xIndex] = null;
                                    }
                                    index = j + 1;
                                }
                                else{
                                    moveToX = x;
                                    index = j;
                                }
                                
                                if(j < 3 && !tileBoard[i][index].getCombo()){
                                    // checks if the tile and the tile above it are the same
                                    boolean sameTile = checkSameTile(moveToX, y, direction);
    
                                    if(sameTile){
                                        // move the current tile right one and combine the two tiles
                                        int yIndex = changeYIndex(y);
                                        int xIndex = changeXIndex(moveToX + 100);
                                        Tile ntile = new Tile(tileBoard[yIndex][xIndex].getValue()*2, moveToX + 100, y, true);
                                        tileBoard[yIndex][xIndex] = ntile;
                                        // remove the tile from the previous spot
                                        tileBoard[yIndex][xIndex - 1] = null;
                                    }
                                }
                            }
                        }
                    }
                }
                resetCombo();
                break;
            case 'L':
            for(int a = 0; a< 3; a++){
                for(int j = 0; j < 4 ; j++){
                    for(int i = 0; i < 4; i++){
                        if (tileBoard[i][j] != null ){
                            int x = tileBoard[i][j].getX();
                            int y = tileBoard[i][j].getY();

                            // variable for the new coordinates of the tile after it moves
                            int moveToX = x - 100;
                            int index;

                            // sees how far the tile can be moved left before colliding
                            if( moveToX < 0)
                            {
                                break;
                            }
                            boolean emptySpaceD = checkSpace(moveToX, y);
                            if(emptySpaceD){
                                // if the space to the left is empty, move the tile left one spot
                                int xIndex = changeXIndex(x);
                                int yIndex = changeYIndex(y);

                                if(xIndex > 0)
                                {
                                    Tile tile = tileBoard[yIndex][xIndex];
                                    tile.changeXValue(moveToX);
                                    tileBoard[yIndex][xIndex - 1] = tile;
                                    tileBoard[yIndex][xIndex] = null;
                                }
                                index = j - 1;
                            }
                            else{
                                moveToX = x;
                                index = j;
                            }
                            
                            if(j > 0 && !tileBoard[i][index].getCombo()){
                                // checks if the tile and the tile to the left are the same
                                boolean sameTile = checkSameTile(moveToX, y, direction);

                                if(sameTile){
                                    // move the current tile left one and combine the two tiles
                                    int yIndex = changeYIndex(y);
                                    int xIndex = changeXIndex(moveToX - 100);
                                    Tile ntile = new Tile(tileBoard[yIndex][xIndex].getValue()*2, moveToX - 100, y, true);
                                    tileBoard[yIndex][xIndex] = ntile;
                                    // remove the tile from the previous spot
                                    tileBoard[yIndex][xIndex + 1] = null;
                                }
                            }
                        }
                    }
                }
            }
            resetCombo();
            break;
        }

    }
    
    // changes an x coordinate to the corresponding x index
    public int changeXIndex (int x){
        int xIndex = 0;
        if(x == 25){xIndex = 0;}
        else if(x == 125){xIndex = 1;}
        else if(x == 225){xIndex = 2;}
        else{xIndex = 3;}

        return xIndex;
    }
    // changes a y coordinate to the corresponding y index
    public int changeYIndex (int y){
        int yIndex = 0;
        if(y == 75){yIndex = 0;}
        else if(y == 175){yIndex = 1;}
        else if(y == 275){yIndex = 2;}
        else{yIndex = 3;}
        
        
        return yIndex;
    }
    
    // resets the tiles so that none of them have been combined before
    public void resetCombo(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(tileBoard[i][j] != null)
                tileBoard[i][j].changeCombined(false);
            }
        }
    }

    // checks if the current tile is the same tile as the one in the next spot
    public boolean checkSameTile(int x, int y, char direction){
        boolean sameTile = false;

        int xIndex = changeXIndex(x);
        int yIndex = changeYIndex(y);

        int currentValue;
        int nextValue;

        if(direction == 'U' && yIndex != 0){
            // if there's a tile in current spot & its value is the same as the tile up, return true
            if(tileBoard[yIndex - 1][xIndex] != null){
                currentValue = tileBoard[yIndex][xIndex].getValue();
                nextValue = tileBoard[yIndex-1][xIndex].getValue();
                if(currentValue == nextValue){
                    sameTile = true;
                }
            }
        }
        else if(direction == 'D' && yIndex != 3){
            // if there's a tile in current spot & its value is the same as the tile down, return true
            if(tileBoard[yIndex + 1][xIndex] != null){
                currentValue = tileBoard[yIndex][xIndex].getValue();
                nextValue = tileBoard[yIndex+1][xIndex].getValue();
                if(currentValue == nextValue){
                    sameTile = true;
                }
            }
        }
        else if(direction == 'R' && xIndex != 3){
            // if there's a tile in current spot & its value is the same as the tile right, return true
            if(tileBoard[yIndex][xIndex+1] != null){
                currentValue = tileBoard[yIndex][xIndex].getValue();
                nextValue = tileBoard[yIndex][xIndex+1].getValue();
                if(currentValue == nextValue){
                    sameTile = true;
                }
            }
        }
        else if(direction == 'L' && xIndex != 0){
            // if there's a tile in current spot & its value is the same as the tile left, return true
            if(tileBoard[yIndex][xIndex-1] != null){
                currentValue = tileBoard[yIndex][xIndex].getValue();
                nextValue = tileBoard[yIndex][xIndex - 1].getValue();
                if(currentValue == nextValue){
                    sameTile = true;
                }
            }
        }
        return sameTile;
    }

    public int getScore(){
        int score= 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j< 4; j++){
                if(tileBoard[i][j] != null){
                    score += tileBoard[i][j].getValue();
                }
            }
        }
        return score;
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        // takes the pressed key and assigns it as the direction
        public void keyPressed (KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 'D';
                    break;
            }
            move();
            newTile();
            repaint();
        }
      }

}
