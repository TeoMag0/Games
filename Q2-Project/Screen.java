import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class Screen extends JPanel implements KeyListener {
  private Player player;
  Treasure[] treasures = new Treasure[10];
  Obstacle[] obstacles = new Obstacle[10];
  Enemy[] enemies = new Enemy[5];
  private BufferedImage background = null;
  private BufferedImage win = null;
  private BufferedImage lose = null;
  private boolean aP, wP, dP, sP;
  private double timer = 30;
  private boolean won = false, lost = false;
  private double respawn = 1.0;
  public boolean dead = false;
  private BufferedImage[] playerAnims = new BufferedImage[10];
  private double playerAnimTimer = 0.35;
  private BufferedImage playerCurrent;

  public Screen() {
    aP = false;
    wP = false;
    dP = false;
    sP = false;

    try {
      for (int i = 0; i < playerAnims.length; i++) {
        playerAnims[i] = ImageIO.read(new File(i + 1 + ".png"));
      }
    } catch (IOException e) {
    }
    playerCurrent = playerAnims[0];

    player = new Player(0, 270, this);
    for (int i = 0; i < enemies.length; i++) {
      enemies[i] = new Enemy();
    }
    for (int i = 0; i < treasures.length; i++) {
      treasures[i] = new Treasure();
    }
    for (int i = 0; i < obstacles.length; i++) {
      obstacles[i] = new Obstacle();
    }
    try {
      background = ImageIO.read(new File("background.png"));
      win = ImageIO.read(new File("win.png"));
      lose = ImageIO.read(new File("lose.png"));
    } catch (IOException e) {
    }

    addKeyListener(this);
    setFocusable(true);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (!won && !lost) {
      g.drawImage(background, 0, 0, 800, 600, null);

      for (int i = 0; i < obstacles.length; i++) {
        obstacles[i].draw(g);
      }

      int enemyX = 100;
      for (int i = 0; i < enemies.length; i++) {
        enemies[i].draw(g, enemyX);
        enemyX += 75;
      }
      for (int i = 0; i < treasures.length; i++) {
        if (treasures[i].interactable) {
          treasures[i].draw(g, obstacles);
        }
      }

      player.draw(g, enemies, treasures, obstacles);
      g.setFont(new Font(getFont() + "", Font.PLAIN, 40));
      g.drawString("" + (Math.round(100 * timer) / 100.0), 700, 45);
    } else if (won) {
      g.drawImage(win, 0, 0, 800, 600, null);
      g.setFont(new Font(getFont() + "", Font.PLAIN, 70));
      g.drawString("Press Y to Play Again", 65, 450);
    } else if (lost) {
      g.drawImage(lose, 0, 0, 800, 600, null);
      g.setFont(new Font(getFont() + "", Font.PLAIN, 70));
      g.drawString("Press Y to Play Again", 65, 450);
    }
  }

  public void win() {
    won = true;
  }

  public void lose() {
    lost = true;
  }

  public void animate() {
    while (true) {
      for (int i = 0; i < enemies.length; i++) {
        enemies[i].move();
      }

      if (aP || wP || dP || sP) {
        playerAnimTimer -= 0.01;
        if (playerAnimTimer <= 0) {
          if (aP) {
            if(playerCurrent == playerAnims[0]){
              playerCurrent = playerAnims[1];
            }
            else{
              playerCurrent = playerAnims[0];
            }
          }
          else if (wP) {
            if(playerCurrent == playerAnims[3]){
              playerCurrent = playerAnims[4];
            }
            else{
              playerCurrent = playerAnims[3];
            }
          }
          else if (dP) {
            if(playerCurrent == playerAnims[5]){
              playerCurrent = playerAnims[6];
            }
            else{
              playerCurrent = playerAnims[5];
            }
          }
          else if (sP) {
            if(playerCurrent == playerAnims[8]){
              playerCurrent = playerAnims[9];
            }
            else{
              playerCurrent = playerAnims[8];
            }
          }
          playerAnimTimer = 0.35;
        }
      }
      else{
        if(playerCurrent == playerAnims[1]){
          playerCurrent = playerAnims[0];
        }
        else if(playerCurrent == playerAnims[3] || playerCurrent == playerAnims[4]){
          playerCurrent = playerAnims[2];
        }
        else if(playerCurrent == playerAnims[6]){
          playerCurrent = playerAnims[5];
        }
        else if(playerCurrent == playerAnims[8] || playerCurrent == playerAnims[9]){
          playerCurrent = playerAnims[7];
        }
      }
      if (aP) {
        if(!wP && !dP && !sP){
          if(playerCurrent != playerAnims[0] && playerCurrent != playerAnims[1]){
          playerCurrent = playerAnims[0];
          }
        }
        player.move(1, playerCurrent);
      }
      if (wP) {
        if(!aP && !dP && !sP){
          if(playerCurrent != playerAnims[2] && playerCurrent != playerAnims[3] && playerCurrent != playerAnims[4]){
          playerCurrent = playerAnims[2];
          }
        }
        player.move(2, playerCurrent);
      }
      if (dP) {
        if(!aP && !wP && !sP){
          if(playerCurrent != playerAnims[5] && playerCurrent != playerAnims[6]){
          playerCurrent = playerAnims[5];
          }
        }
        player.move(3, playerCurrent);
      }
      if (sP) {
        if(!aP && !wP && !dP){
          if(playerCurrent != playerAnims[7] && playerCurrent != playerAnims[8] && playerCurrent != playerAnims[9]){
          playerCurrent = playerAnims[7];
          }
        }
        player.move(4, playerCurrent);
      }
      timer -= 0.01;
      if (timer <= 0) {
        lose();
      }
      if (dead) {
        respawn -= 0.01;
        if (respawn <= 0) {
          dead = false;
          respawn = 1.0;
          player.active = true;
        }
      }
      repaint();
      try {
        Thread.sleep(10);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
        ;
      }
    }
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 65 || e.getKeyCode() == 37) {
      aP = true;
    }
    if (e.getKeyCode() == 87 || e.getKeyCode() == 38) {
      wP = true;
    }
    if (e.getKeyCode() == 68 || e.getKeyCode() == 39) {
      dP = true;
    }
    if (e.getKeyCode() == 83 || e.getKeyCode() == 40) {
      sP = true;
    }
    if (e.getKeyCode() == 67) {
      win();
    }
    if (e.getKeyCode() == 89) {
      if (won || lost) {
        player = new Player(0, 270, this);
        for (int i = 0; i < enemies.length; i++) {
          enemies[i] = new Enemy();
        }
        for (int i = 0; i < treasures.length; i++) {
          treasures[i] = new Treasure();
        }
        for (int i = 0; i < obstacles.length; i++) {
          obstacles[i] = new Obstacle();
        }
        won = false;
        lost = false;
        timer = 30;
      }
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == 65 || e.getKeyCode() == 37) {
      aP = false;
    }
    if (e.getKeyCode() == 87 || e.getKeyCode() == 38) {
      wP = false;
    }
    if (e.getKeyCode() == 68 || e.getKeyCode() == 39) {
      dP = false;
    }
    if (e.getKeyCode() == 83 || e.getKeyCode() == 40) {
      sP = false;
    }
  }

  public void keyTyped(KeyEvent e) {

  }
}
