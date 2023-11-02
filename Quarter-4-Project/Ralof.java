import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Ralof extends Character {

    private BufferedImage sprite;

    public Ralof(int x, int y, int b, String[] lines){
        super(x, y, b, lines);
        try{
            sprite = ImageIO.read(new File("ralof.png"));
        } catch(IOException e){
        }
    }

    public void drawMe(Graphics g,int xD,int yD){
        super.drawMe(g, xD, yD);
        g.drawImage(sprite, getX()-24, getY()-24, getSize(), getSize(), null);
    }

    public String type(){
        return "ralof";
    }
}
