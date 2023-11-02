import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Player extends Basic{
  private int x;
  private int y;
  private int worldY;
  private int width;
  private int height;
  private BufferedImage sprite;
  private BufferedImage sprite1;
  private BufferedImage sprite2;

  public Player(int xSize, int ySize) {
    super(0);
    x = 400 - xSize / 2;
    y = 300 - ySize / 2;

    width = xSize; // total width of the player
    height = ySize; // total height of the player

    try {
      sprite1 = ImageIO.read(new File("slime1.png"));
      sprite2 = ImageIO.read(new File("slime2.png"));
    } catch (IOException e) {
    }
    sprite = sprite1;

  }

  public void drawMe(Graphics g) {

    g.drawImage(sprite, x, y, width, height, null);

    /*
     * g.setColor(Color.BLUE);
     * g.fillOval(x, y, width, width);
     * g.fillRect(x + 15, y, 10, height);
     */
  }

  public void switchSprite(){
    if(sprite == sprite1){
      sprite = sprite2;
    }
    else if(sprite == sprite2){
      sprite = sprite1;
    }
  }

  public int getYConst(){
    return worldY+height/8;
  }
  public void setY(int y){
    worldY = y;
  }
  public boolean enabled(){
    return true;
  }
  public void drawMe(Graphics g, int e, int a){}

}