import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Enemy{
  
  public int xPos;
  public int yPos;
  public int w;
  public int h;
  public int speed;
  private boolean goingDown;
  private int currentDir;
  private BufferedImage pic;

  public Enemy(){
    try {
          pic = ImageIO.read(new File("enemyfacingleft.png"));
        } catch (IOException e) {
        }
    w = 60;
    h = 60;
    yPos = (int)(Math.random()*(499-h)+50);
    xPos = (int)(Math.random()*(650-w)+100);
    speed = (int)(Math.random()*3+1);
    int random = (int)(Math.random()*2+1);
    if(random == 1){
      goingDown = true;
    }
    else{
      goingDown = false;
    }
  }

  public void move(){
    if(yPos >=550-h || yPos <= 50){
      goingDown = !goingDown;
    }
    if(goingDown){
      yPos += speed;
    }
    else{
      yPos -= speed;
    }
  }
  public void move(boolean sideways){
    int four = (int)(Math.random()*5+1);
    if(four == 1 && xPos<750-w || currentDir == 3 && four == 5 && xPos<750-w){
      xPos++;
      currentDir = 3;
    }
    else if(four == 2 && xPos<50 || currentDir == 1 && four == 5 && xPos<50){
      xPos--;
      currentDir = 1;
    }
    else if(four == 3 && yPos<550-h || currentDir == 4 && four == 5 && yPos<550-h){
      yPos++;
      currentDir = 4;
    }
    else if(four == 4 && yPos>50 || currentDir == 2 && four == 5 && yPos>50){
      yPos--;
      currentDir = 2;
    }
  }
  public void draw(Graphics g, int x){
    g.drawImage(pic, xPos, yPos, w, h, null);
  }
}