import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Letter extends Item {

    private BufferedImage sprite;
    
    public Letter(int x, int y, int b){
        super(x, y, b, 30);
        try {
            sprite = ImageIO.read(new File("letter.png"));
          } catch (IOException e) {
          }
    }

    public void drawMe(Graphics g, int x, int y){
        super.drawMe(g, x, y);
        g.drawImage(sprite, getX()-24, getY()-15, 48, 30, null);
    }
    public void drawMe(Graphics g, int x, int y, int xs, int ys){
        super.drawMe(g, x, y);
        g.drawImage(sprite, x-xs/2, y-ys/2, xs, (int)(ys*.625), null);
    }

    public String type(){
        return "letter";
    }
}
