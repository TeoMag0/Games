import java.io.File;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Color;

public class Treasure{
    private BufferedImage pic = null;
    public int x,y,w,h;
    public boolean interactable = true;

    public Treasure(){
        try {
            pic = ImageIO.read(new File("whiteBag.png"));
        } catch (IOException e) {
        }
        w = 50;
        h = 56;
        x = (int)(Math.random()*(700-w+1)+50);
        y = (int)(Math.random()*(500-h+1)+50);
    }
    public void draw(Graphics g, Obstacle[] obstacles){
      for(int i=0;i<obstacles.length;i++){
        if(x+w>obstacles[i].x && x<obstacles[i].x+obstacles[i].w && y+h>obstacles[i].y && y<obstacles[i].y+obstacles[i].h){
          x = (int)(Math.random()*(700-w+1)+50);
          y = (int)(Math.random()*(500-h+1)+50);
        }
      }
      g.drawImage(pic, x, y,w, h, null);
    }
}