import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Card extends Item {

  private BufferedImage sprite;
  private String suit;

  public Card(int x, int y, int b, String s) {
    super(x, y, b, 24);
    try {
      sprite = ImageIO.read(new File(s + ".png"));
    } catch (IOException e) {
    }
    suit = s;
  }

  public void drawMe(Graphics g, int x, int y) {
    super.drawMe(g, x, y);
    g.drawImage(sprite, getX() - 12, getY() - 12, 24, 24, null);
  }

  public void drawMe(Graphics g, int x, int y, int xs, int ys) {
    super.drawMe(g, x, y);
    g.drawImage(sprite, x - xs / 2, y - ys / 2, xs, ys, null);
  }

  public String type() {
    return "card";
  }

  public String getSuit() {
    return suit;
  }
}
