import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class Scenery extends JPanel{
  private int timeOfDay;
  private int ground;

  private Color lightBlue = new Color(135,206,235);
  private Color darkBlue = new Color(20,10,60);//as;gnaowegn

  private Color snow = new Color(235,230,230);
  private Color grass = new Color(126,200,80);
  private Color leaves = new Color(176,101,75);

  private Color pink = new Color(255,192,203);
  private Color yellow = new Color(255,255,0);
  private Color white = new Color(255,255,255);
  private Color red = new Color(255,0,0);
  private Color black = new Color(0,0,0);

  private Color darkGreen = new Color(3,70,52);
  private Color gold = new Color(200, 130, 40);
  private Color brown = new Color(100,42,42);

  public Scenery(int dayInput, int groundInput){
    timeOfDay = dayInput;
    ground = groundInput;
  }
  @Override
  public Dimension getPreferredSize(){
    return new Dimension(800, 600);
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    paintSky(g);
    paintGround(g);
    paintPig(g, 550, 300);
    paintChicken(g,200,400);
    paintBarn(g,350,200);
    paintTree(g, 20, 200);
    paintTree(g, 200, 150);
    paintTree(g, 650, 250);
    paintFlower(g, 500, 200, white);
    paintFlower(g, 350, 350, pink);
    paintFlower(g, 450, 400, red);
  }

  private void paintSky(Graphics g){
    if(timeOfDay == 1){
      g.setColor(lightBlue);
    }
    else if(timeOfDay == 2){
      g.setColor(darkBlue);
    }
    g.fillRect(0,0,800,600);
  }
  private void paintGround(Graphics g){
    if(ground == 1){
      g.setColor(snow);
    }
    else if(ground == 2){
      g.setColor(grass);
    }
    else if(ground == 3){
      g.setColor(leaves);
    }
    g.fillRect(0,240,800,460);
  }
  private void paintPig(Graphics g, int x, int y){
    g.setColor(pink);
    //snout
    g.fillRect(x,y+10, 20, 10);
    //head
    g.fillOval(x+4,y,20,20);
    //body
    g.fillRect(x+18,y+13, 40, 20);
    //legs
    g.fillRect(x+18,y+33,10,15);
    g.fillRect(x+48,y+33,10,15);
    //eye
    g.setColor(black);
    g.fillRect(x+7,y+7,3,3);
  }
  private void paintChicken(Graphics g, int x, int y){
    g.setColor(yellow);
    //beak
    int[] xPoints = {x,x+6,x+6};
    int[] yPoints = {y+15,y+10,y+20};
    g.fillPolygon(xPoints ,yPoints, 3);
    //leg
    g.fillRect(x+24,y+35,6,10);
    //red thing
    g.setColor(red);
    g.fillRect(x+9,y,12,10);
    //head
    g.setColor(white);
    g.fillOval(x+5,y+5,20,20);
    //body
    g.fillOval(x+15,y+15,23,20);
    //eye
    g.setColor(black);
    g.fillRect(x+8,y+10,3,3);
  }
  private void paintBarn(Graphics g, int x, int y){
    g.setColor(red);
    int[] xPoints = {x,x+15,x+50,x+85,x+100};
    int[] yPoints = {y+50,y+15,y,y+15,y+50};
    g.fillPolygon(xPoints, yPoints, 5);
    g.fillRect(x+0,y+50,100,50);
    g.setColor(black);
    int[] xPoints2 = {x,x+15,x+50,x+85,x+100};
    int[] yPoints2 = {y+50,y+15,y,y+15,y+50};
    g.drawPolygon(xPoints2, yPoints2, 5);
    g.fillRect(x+30,y+65,40,35);
    g.setColor(lightBlue);
    g.fillRect(x+40,y+20,20,20);
  }
  private void paintFlower(Graphics g, int x, int y, Color petals){
    g.setColor(darkGreen);
    g.fillRect(x+10,y+20,10,40);
    g.setColor(petals);
    g.fillOval(x,y,30,30);
    g.setColor(yellow);
    g.fillOval(x+5,y+5,20,20);
  }
  private void paintTree(Graphics g, int x, int y){
    if(ground == 1){
      g.setColor(brown);
      double rand = Math.random();
      if(rand <= 0.33){
        int[] xPoints = {x,x+20,x+35,x+35};
        int[] yPoints = {y+90,y+80,y+95,y+115};
        g.fillPolygon(xPoints, yPoints, 4);
        int[] xPoints2 = {x+90,x+70,x+55,x+55};
        int[] yPoints2 = {y+120,y+110,y+125,y+145};
        g.fillPolygon(xPoints2, yPoints2, 4);
      }
      else if(rand <= 0.66){
        int[] xPoints = {x,x+20,x+35,x+35};
        int[] yPoints = {y+90,y+80,y+95,y+115};
        g.fillPolygon(xPoints, yPoints, 4);
      }
      else if(rand <= 1){
        int[] xPoints2 = {x+90,x+70,x+55,x+55};
        int[] yPoints2 = {y+120,y+110,y+125,y+145};
        g.fillPolygon(xPoints2, yPoints2, 4);
      }
      g.fillRect(x+30,y+80,30,150);
    }
    else if(ground == 2){
      g.setColor(brown);
      g.fillRect(x+30,y+80,30,150);
      g.setColor(darkGreen);
      g.fillOval(x,y,90,180);
    }
    else if(ground == 3){
      g.setColor(brown);
      g.fillRect(x+30,y+80,30,150);
      g.setColor(gold);
      g.fillOval(x,y,90,180);
    }
  }
}