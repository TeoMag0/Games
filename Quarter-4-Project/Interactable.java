import java.awt.Graphics;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public abstract class Interactable extends Basic{
    private int x, y, bound, xDiff, yDiff;
    private boolean interact = false;
    private int playerW, playerH;
    private BufferedImage prompt;

    public Interactable(int x, int y, int b){
        super(y);
        this.x = x;
        this.y = y;
        bound = b;
        playerW = 30;
        playerH = 30;
        try{
            prompt = ImageIO.read(new File("e.png"));
        } catch(IOException e){}
    }

    public boolean checkCollision(){
        if( 400 + playerW/2 > x+xDiff-bound && 400-playerW/2 < x+xDiff + bound && 300-playerH/2 < y+yDiff + bound && 300 + playerH/2 > y+yDiff - bound){
            interact = true;
            return true;
        }
        interact = false;
        return false;
    }

    public void drawMe(Graphics g, int xD, int yD){
        xDiff = xD;
        yDiff = yD;
        g.setColor(Color.white);
        //interact bounds
        //g.fillRect(x + xDiff - bound, y + yDiff - bound, bound * 2, bound * 2);
        if(interact && activatePrompt()){
            //interation prompt
            g.drawImage(prompt, x+xDiff-9, y+yDiff-bound - 18, 18, 18, null);
        }
    }
    /*public void drawMe(Graphics g, int newX, int newY, int xSize, int ySize){
        this.x = newX;
        this.y = newY;
    }*/
    public int getX(){
        return x+xDiff;
    }
    public int getY(){
        return y+yDiff;
    }
    public boolean activatePrompt(){
        return true;
    }
    public abstract int interacted();
    public abstract boolean enabled();
    public abstract String type();
}