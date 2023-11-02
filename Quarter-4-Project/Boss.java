import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Boss extends Character {

    private BufferedImage sprite;
    private BufferedImage sprite2;
    private BufferedImage curSprite;

    public Boss(int x, int y, int b, String[] lines){
        super(x, y, b, lines);
        try{
            sprite = ImageIO.read(new File("nguyen.png"));
            sprite2 = ImageIO.read(new File("boss.png"));
        } catch(IOException e){
        }
        curSprite = sprite;
    }

    public void drawMe(Graphics g,int xD,int yD){
        super.drawMe(g, xD, yD);
        g.drawImage(curSprite, getX()-24, getY()-24, getSize(), getSize(), null);
    }
    public void upgrade(){
        curSprite = sprite2;
    }
    public String type(){
        return "boss";
    }
}
