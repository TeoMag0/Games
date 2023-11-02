import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Diamond extends Item {

    private BufferedImage sprite;
    
    public Diamond(int x, int y, int b){
        super(x, y, b, 26);
        try {
            sprite = ImageIO.read(new File("diamond.png"));
          } catch (IOException e) {
          }
    }

    public void drawMe(Graphics g, int x, int y){
        super.drawMe(g, x, y);
        g.drawImage(sprite, getX()-12, getY()-13, 24, 26, null);
    }
    public void drawMe(Graphics g, int x, int y, int xs, int ys){
        super.drawMe(g, x, y);
        g.drawImage(sprite, x-xs/2, y-ys/2, (int)(xs*.923), ys, null);
    }

    public String type(){
        return "diamond";
    }
}
