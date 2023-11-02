import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Projectile {

    private BufferedImage sprite;
    private int timer;

    private int x;
    private int y;
    private int xSize;
    private int ySize;
    private int speed;
    private int yAdj = 0;
    private int yMaxAdj = 0;

    public Projectile(int minX, int maxX, int startY, int size, int minSpeed, int maxSpeed) {
        timer = 0;
        int rand = (int) (Math.random() * 2);
        try {
            switch (rand) {
                case 0:
                    sprite = ImageIO.read(new File("bracket.png"));
                    xSize = 19*size;
                    ySize = 5*size;
                    break;
                case 1:
                    sprite = ImageIO.read(new File("curlies.png"));
                    xSize = 19*size;
                    ySize = 8*size;
                    yAdj = 4*size;
                    yMaxAdj = 1*size;
                    break;
            }
        } catch (IOException e) {
        }

        x = (int)((Math.random()*(maxX-xSize-minX)+1) + minX);
        y = startY;
        speed = (int)((Math.random()*(maxSpeed-minSpeed+1))+minSpeed);
    }
    public Projectile(int xCoord, int startY, int size, int minSpeed, int maxSpeed, boolean mrRightSide) {
        timer = 0;
        int rand = (int) (Math.random() * 2);
        try {
            switch (rand) {
                case 0:
                    sprite = ImageIO.read(new File("bracket.png"));
                    xSize = 19*size;
                    ySize = 5*size;
                    break;
                case 1:
                    sprite = ImageIO.read(new File("curlies.png"));
                    xSize = 19*size;
                    ySize = 8*size;
                    yAdj = 4*size;
                    yMaxAdj = 1*size;
                    break;
            }
        } catch (IOException e) {
        }

        if(mrRightSide)
            x = xCoord - xSize;
        else
            x = xCoord;
        y = startY;
        speed = (int)((Math.random()*(maxSpeed-minSpeed+1))+minSpeed);
    }

    public Projectile(int mS, int startY, int size) {
        try {
            switch (mS) {
                case 1:
                    sprite = ImageIO.read(new File("milestone1.png"));
                    break;
                case 2:
                    sprite = ImageIO.read(new File("milestone2.png"));
                    break;
            }
        } catch (IOException e) {
        }
        xSize = 9*size;
        ySize = 10*size;

        x = -680 - xSize/2;
        speed = 2;
        y = startY;
    }
    public void move(){
        timer += 10;
        if(timer >= 1000)
            y+= speed;
    }

    public void drawMe(Graphics g, int xDiff, int yDiff) {
        g.drawImage(sprite, x+xDiff, y+yDiff, xSize, ySize, null);
    }

    public int getY(){
        return y;
    }

    public boolean hit(int xDiff, int yDiff, int playerX, int playerY, int playerXMax, int playerYMax){
        if(x+xSize+xDiff > playerX && x+xDiff < playerXMax && y+ySize+yDiff-yMaxAdj > playerY && y+yDiff+yAdj < playerYMax){
            return true;
        }
        return false;
    }
}
