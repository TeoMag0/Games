import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Player{

  private int x,y,w,h,lives,score;
  private Screen screen;
  private BufferedImage pic;
  public boolean active = true;
  
  public Player(int x,int y, Screen screen){
    try {
          pic = ImageIO.read(new File("6.png"));
        } catch (IOException e) {
        }
    this.x = x;
    this.y = y;
    w = 85;
    h = 63;
    lives = 3;
    score = 0;
    this.screen = screen;
  }

  public void move(int dir, BufferedImage sprite){
    if(active){
      pic = sprite;
      if(dir == 1 && x>=0){
        x-=2;
      }
      else if(dir == 2 && y>=50){
        y-=2;
      }
      else if(dir == 3 && x<=800-w){
        x+=2;
      }
      else if(dir == 4 && y<=550-h){
        y+=2;
      }
    }
  }
  public void draw(Graphics g, Enemy[] enemies, Treasure[] treasures, Obstacle[] obstacles){
    if(active){
      g.drawImage(pic, x, y, w, h, null);
    }
    for(int i=0;i<enemies.length;i++){
        if(x+w>enemies[i].xPos && x<enemies[i].xPos+enemies[i].w && y+h>enemies[i].yPos && y<enemies[i].yPos+enemies[i].h){
          x = 0;
          y = 270;
          lives--;
          active = false;
          screen.dead = true;
          try {
          pic = ImageIO.read(new File("6.png"));
        } catch (IOException e) {
        }
        }
     }
     for(int i=0;i<treasures.length;i++){
        if(x+w>treasures[i].x && x<treasures[i].x+treasures[i].w && y+h>treasures[i].y && y<treasures[i].y+treasures[i].h && treasures[i].interactable){
          treasures[i].interactable = false;;
          score++;
          if(score == treasures.length){
            screen.win();
          }
        }
     }
     for(int i=0;i<obstacles.length;i++){
        if(x+w>obstacles[i].x && x<obstacles[i].x+obstacles[i].w && y+h>obstacles[i].y && y<obstacles[i].y+obstacles[i].h){
            x = 0;
            y = 270;
          lives--;
          active = false;
          screen.dead = true;
          try {
          pic = ImageIO.read(new File("6.png"));
        } catch (IOException e) {
        }
        }
     }
    paintUI(g);
  }
  public void paintUI(Graphics g){
    g.setColor(Color.red);
    if(lives == 3){
    g.fillOval(10,10,40,40);
    g.fillOval(70,10,40,40);
    g.fillOval(130,10,40,40);
    }
    else if(lives == 2){
    g.fillOval(10,10,40,40);
    g.fillOval(70,10,40,40);
    g.setColor(Color.gray);
    g.fillOval(130,10,40,40);
    }
    else if(lives == 1){
    g.fillOval(10,10,40,40);
    g.setColor(Color.gray);
    g.fillOval(70,10,40,40);
    g.fillOval(130,10,40,40);
    }
    else if(lives <= 0){
      //end loss screen
      screen.lose();
    }
    g.setColor(Color.black);
    g.setFont(new Font(g.getFont()+"", Font.PLAIN,40));
    g.drawString("Treasures Collected: " + score,200,45);
  }
}