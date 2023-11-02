import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Tree extends Basic{

    private int x;
    private int y;
    private BufferedImage sprite;
    private int xSize;
    private int ySize;

    public Tree(int x,int y, int xS, int yS){
        super(y);
        this.x = x;
        this.y = y;
        xSize = xS;
        ySize = yS;

        try {
            sprite = ImageIO.read(new File("tree35x47.png"));
          } catch (IOException e) {
          }
    }

    public void drawMe(Graphics g, int xDiff, int yDiff){
        g.drawImage(sprite, x - xSize / 2 + xDiff, y - ySize + yDiff, xSize, ySize, null);
    }

    public int getX(){
        return x;
    }
    public int getYConst(){
        return y;
    }
    public boolean enabled(){
        return true;
    }
}
