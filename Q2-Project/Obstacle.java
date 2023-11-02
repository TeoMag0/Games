import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Color;

public class Obstacle{
    private BufferedImage pic = null;
    public int x,y,w,h;

    public Obstacle(){
        try {
            pic = ImageIO.read(new File("fire.png"));
        } catch (IOException e) {
        }
        w = 40;
        h = 43;
        x = (int)(Math.random()*(650-w+1)+100);
        y = (int)(Math.random()*(500-h+1)+50);
    }
    public void draw(Graphics g){
        g.drawImage(pic, x, y,w, h, null);
    }
}