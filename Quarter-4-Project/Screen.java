import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.awt.MouseInfo;

public class Screen extends JPanel implements KeyListener, MouseListener {

  // objects/misc
  private Letter letter;
  private Player p1;
  private TreeList trees;
  private Diamond i1, i2, i3;
  private Card c1, c2, c3, c4;
  private Creeper creeper;
  private Ralof ralof;
  private Nguyen nguyen;
  private Boss boss;
  private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
  private int bossCooldown;
  private int timer;
  private int bossFightLength;
  private int bossFightTimer;
  private int gameEndAnimTimer;
  private int[] projectileSpeedRange = { 1, 3 };

  private int xDiff;
  private int yDiff;
  private int playX;
  private int playY;
  private int playSizeX;
  private int playSizeY;
  private int treeSize;
  private int menuUISize;

  private ArrayList<Item> itemList = new ArrayList<Item>();
  private ArrayList<Item> indoorItemList = new ArrayList<Item>();
  private ArrayList<Character> npcList = new ArrayList<Character>();

  // animation
  private int playerAnimTimer;
  private PlayerAnimations anims = new PlayerAnimations();
  private int faceYDiff;
  private double ts1VShift;
  private double ts2VShift;
  private double tsSpeed;

  // scenes
  private boolean mainMenu = true;
  private boolean inInv = false;
  private boolean gameEnd = false;
  private boolean inDoors = false;
  private int[] pizzeriaBounds = { -2170 + 350, -832 + 110, -2170 + 350 + 80, -832 + 110 + 50, -1790, -520, -1590,
      -490 };
  private ArrayList<Barrier> walkies = new ArrayList<Barrier>();
  private boolean bossFight;
  private Barrier mapBounds;
  private Barrier boatBounds;

  // sprites
  private BufferedImage map;
  private BufferedImage pizzeriaExt;
  private BufferedImage pizzeriaInt;
  private BufferedImage playButton;
  private BufferedImage boat1;
  private BufferedImage boat2;
  private BufferedImage curBoat;
  private BufferedImage ts1;
  private BufferedImage ts2;
  private BufferedImage wasdInstructions;
  private BufferedImage eInstructions;
  private BufferedImage cheatInstructions;
  private BufferedImage invInstructions;
  private BufferedImage mapInstructions;

  // sounds
  private int partyRadius;
  private float fadeTime;
  private float fadeVal;
  private float fadeValMice;
  private boolean endSong = false;
  private Clip revenge;
  private Clip revengeLoud;
  private Clip mega;
  private Clip backgroundMusic;
  private boolean startBackgroundMusic;
  private boolean fadeMice = false;

  // Quest tracking
  private boolean quest2Done = false;
  private boolean quest1Done = false;
  private boolean quest3Done = false;
  private boolean quest4Done = false;
  private boolean quest2HalfDone = false;
  private ArrayList<QuestTrack> questList = new ArrayList<QuestTrack>();
  private QuestTrack quest1Text;
  private QuestTrack quest2Text;
  private QuestTrack quest3Text;
  private QuestTrack quest4Text;
  private QuestTrack quest2HalfText;

  // lines
  private String[] creeperIdleLines = { "", "..." };
  private String[] creeperLines1 = { "", "thanks for the letter" };
  private String[] creeperLines2 = { "", "Go get some diamonds and then talk to me" };
  private String[] creeperLines3 = { "", "mmmmmmmm", "I love diamonds", "you want to get off the island?",
      "you could talk to my computer science teacher", "go back along the path and take the other split to find him" };

  private String[] ralofLines = { "", "Hey, you. You're finally awake.", "You were trying to cross the border, right?",
      "Well, anyways I have this important letter I need to give to a friend", "could you bring him the letter for me?",
      "He might know how to help you get off the island",
      "He's at a party in the pizzeria", "follow the path and keep to the left" };
  private String[] ralofLines2 = { "", "Thanks bro" };

  private String[] nguyenIdleLines = { "", "I used to be an adventurer like you", "then I took an arrow to the knee" };
  private String[] nguyenLines = { "", "I want to play blackjack but I misplaced my cards",
      "can you get me my 4 missing cards?" };
  private String[] nguyenLines2 = { "", "YESSSS, THANK YOU", "oh, you want to get off the island?",
      "if you follow the path on the left, there's a dock with a boat", "you can leave on the boat" };

  private String[] bossLines1 = { "", "your quarter 4 project isn't done yet.", "I can't let you leave." };
  private String[] bossLines2 = { "", "that's got to be the best project I've ever seen", "so it would seem.",
      "You can leave" };

  // inv
  private Item[][] inventory = new Item[4][3];
  private Item[] hotBar = new Item[4];
  private Item held = null;
  private int heldDiamonds = 0;
  private int heldCards = 0;

  private boolean aP, wP, dP, sP;

  // boundaries
  private int arenaMin;
  private int arenaMax;
  private int arenaY;
  private int projSize;

  private int playerX;
  private int playerY;
  private int playerXMin, playerYMin, playerXMax, playerYMax;

  //map
  private boolean mapOpen = false;

  public Screen() {
    // instances
    arenaMin = -875;
    arenaMax = -485;
    arenaY = -3400;
    bossCooldown = 2000;
    timer = 0;
    projSize = 7;
    playerX = 48;
    playerY = 48;
    playerXMin = 400 - playerX / 2;
    playerXMax = 400 + playerX / 2;
    playerYMin = 300 - playerY / 2;
    playerYMax = 300 + playerY / 2;
    playX = 20;
    playY = 200;
    playSizeX = 67;
    playSizeY = 24;
    faceYDiff = -1;
    bossFightLength = 30;
    bossFightTimer = 0;
    gameEndAnimTimer = 0;
    partyRadius = 1300;
    treeSize = 3;
    fadeTime = 2;
    fadeVal = 1;
    fadeValMice = 1;
    ts1VShift = -600;
    ts2VShift = 0;
    tsSpeed = 0.3;
    menuUISize = 6;
    startBackgroundMusic = true;

    // set up sounds
    try {
      AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("revenge.wav"));
      revenge = AudioSystem.getClip();
      revenge.open(audioInput);
      audioInput = AudioSystem.getAudioInputStream(new File("revengeloud.wav"));
      revengeLoud = AudioSystem.getClip();
      revengeLoud.open(audioInput);
      audioInput = AudioSystem.getAudioInputStream(new File("mice.wav"));
      backgroundMusic = AudioSystem.getClip();
      backgroundMusic.open(audioInput);
      audioInput = AudioSystem.getAudioInputStream(new File("bossmusic.wav"));
      mega = AudioSystem.getClip();
      mega.open(audioInput);
    } catch (Exception e) {
    }

    // quests
    quest1Text = new QuestTrack("Deliver Ralof's Letter");
    quest2Text = new QuestTrack("Collect 3 Diamonds (0/3)");
    quest3Text = new QuestTrack("Find the CS Teacher's 4 Cards (0/4)");
    quest4Text = new QuestTrack("Leave on the Boat");
    quest2HalfText = new QuestTrack("Find the CS Teacher");

    // boundaries
    walkies.add(new Barrier(-2180, -1432, -1200, -512, true));
    mapBounds = new Barrier(-2900, -2460, 2820, 740, false);
    walkies.add(mapBounds);
    walkies.add(new Barrier(arenaMin, arenaY, arenaMax, -2460, false));
    boatBounds = new Barrier(arenaMin - 120, arenaY + 40, arenaMin, arenaY + 260, false);

    // sprites
    try {
      map = ImageIO.read(new File("map.png"));
      pizzeriaExt = ImageIO.read(new File("pizzeriaExterior.png"));
      pizzeriaInt = ImageIO.read(new File("pizzeriaInterior.png"));
      playButton = ImageIO.read(new File("play.png"));
      boat1 = ImageIO.read(new File("boat1.png"));
      boat2 = ImageIO.read(new File("boat2.png"));
      ts1 = ImageIO.read(new File("ts1.png"));
      ts2 = ImageIO.read(new File("ts2.png"));
      wasdInstructions = ImageIO.read(new File("move44x15.png"));
      eInstructions = ImageIO.read(new File("e46x7.png"));
      cheatInstructions = ImageIO.read(new File("cheat47x7.png"));
      invInstructions = ImageIO.read(new File("inv58x7.png"));
      mapInstructions = ImageIO.read(new File("map26x9.png"));
    } catch (IOException e) {
    }
    curBoat = boat1;

    p1 = new Player(48, 48);

    // items
    letter = new Letter(200, 300, 50);
    itemList.add(letter);
    i1 = new Diamond(-2141, -432, 50);
    itemList.add(i1);
    i2 = new Diamond(-1238, -810, 50);
    itemList.add(i2);
    i3 = new Diamond(-1679, -204, 50);
    itemList.add(i3);
    c1 = new Card(1400, -1200, 50, "heart");
    itemList.add(c1);
    c2 = new Card(2121, -1300, 50, "club");
    itemList.add(c2);
    c3 = new Card(1767, -1554, 50, "spade");
    itemList.add(c3);
    c4 = new Card(1320, -1390, 50, "diamondCard");
    itemList.add(c4);

    // characters
    creeper = new Creeper(-1630, -1150, 50, creeperIdleLines);
    ralof = new Ralof(300, 300, 50, ralofLines);
    npcList.add(ralof);
    nguyen = new Nguyen(1671, -1270, 50, nguyenIdleLines);
    npcList.add(nguyen);
    boss = new Boss((arenaMax + arenaMin) / 2, arenaY + 100, 50, bossLines1);

    xDiff = 0;
    yDiff = 0;

    // trees
    trees = new TreeList(35 * treeSize, 47 * treeSize);

    // sets keylistener
    setFocusable(true);
    addKeyListener(this);
    addMouseListener(this);
    setFocusTraversalKeysEnabled(false);
  }

  public Dimension getPreferredSize() {
    // Sets the size of the panel
    return new Dimension(800, 600);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (startBackgroundMusic) {
      playSound(backgroundMusic, 38000000);
      FloatControl vol = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
      vol.setValue(20 * (float) Math.log10(1));
      startBackgroundMusic = false;
    }
    if (mainMenu) {
      // play button
      g.drawImage(ts1, 0, (int) ts1VShift, 800, 600, null);
      g.drawImage(ts2, 0, (int) ts2VShift, 800, 600, null);
      g.drawImage(wasdInstructions, 500, 100, 44 * menuUISize, 15 * menuUISize, null);
      g.drawImage(mapInstructions, 500, 220, 26*menuUISize, 9*menuUISize, null);
      g.drawImage(eInstructions, 500, 310, 46 * menuUISize, 7 * menuUISize, null);
      g.drawImage(cheatInstructions, 500, 400, 47 * menuUISize, 7 * menuUISize, null);
      g.drawImage(invInstructions, 450, 500, 58 * menuUISize, 7 * menuUISize, null);
      g.setColor(Color.black);

      // under interact
      g.drawString("NPCs have multiple lines of dialogue; make", 500, 350);
      g.drawString("sure to interact with them until they don't", 500, 365);
      g.drawString("have anything new to say.", 500, 380);
      // under inventory
      g.drawString("while in the inventory, click on items in your inventory", 450, 550);
      g.drawString("or hotbar to move them around, or click outside of your", 450, 565);
      g.drawString("inventory while holding an item to drop the item.", 450, 580);
      // g.fillRect(300, 200, 200, 67);
      g.drawImage(playButton, playX, playY, playSizeX * 3, playSizeY * 3, null);
    } else if (gameEnd) {
      g.setColor(Color.blue);
      g.fillRect(0, 0, 800, 600);
      g.drawImage(curBoat, 190 + xDiff, 190 + yDiff, 19 * 20, 19 * 20, null);
      p1.drawMe(g);
      g.drawImage(anims.getAnimation(aP, wP, sP, dP), playerXMin, playerYMin - faceYDiff, 48, 48, null);
      g.setFont(new Font("Arial", Font.BOLD, 64));
      g.setColor(Color.white);
      g.drawString("YOU WIN!", 400 - g.getFontMetrics().stringWidth("YOU WIN!") / 2 + xDiff, 100 + yDiff);
    } else {

      if (inDoors) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 800, 600);
        g.drawImage(pizzeriaInt, -2180 + xDiff, -1432 + yDiff, 49 * 20, 46 * 20, null);
        ArrayList<Basic> drawOrder = new ArrayList<Basic>();
        p1.setY(300 - yDiff);
        drawOrder.add(creeper);
        drawOrder.addAll(indoorItemList);
        drawOrder.add(p1);

        //sort for inside
        for (int i = 0; i < drawOrder.size() - 1; i++) {
          for (int j = 0; j < drawOrder.size() - i - 1; j++) {
            if (drawOrder.get(j).getYConst() > drawOrder.get(j + 1).getYConst()) {
              Basic temp = drawOrder.get(j);
              drawOrder.set(j, drawOrder.get(j + 1));
              drawOrder.set(j + 1, temp);
            }
          }
        }
        //draw it now
        for (Basic each : drawOrder) {
          if (each == p1) {
            p1.drawMe(g);
            g.drawImage(anims.getAnimation(aP, wP, sP, dP), playerXMin, playerYMin - faceYDiff, 48, 48, null);
          } else if (each.enabled()) {
            each.drawMe(g, xDiff, yDiff);
          }
        }

        g.setColor(Color.black);
        g.fillRect(-1790 + xDiff, -532 + yDiff, 200, 20);
      } else {
        // collect everything to be ordered
        p1.setY(300 - yDiff);
        ArrayList<Basic> drawOrder = new ArrayList<Basic>();
        drawOrder.addAll(itemList);
        drawOrder.addAll(npcList);
        drawOrder.addAll(trees.getTrees());
        drawOrder.add(p1);

        // change draw order based on height (bubble sort)
        for (int i = 0; i < drawOrder.size() - 1; i++) {
          for (int j = 0; j < drawOrder.size() - i - 1; j++) {
            if (drawOrder.get(j).getYConst() > drawOrder.get(j + 1).getYConst()) {
              Basic temp = drawOrder.get(j);
              drawOrder.set(j, drawOrder.get(j + 1));
              drawOrder.set(j + 1, temp);
            }
          }
        }

        // draws background
        int mapSize = 20;
        g.setColor(Color.blue);
        g.fillRect(0, 0, 800, 600);
        g.drawImage(map, -150 * mapSize + 400 + xDiff, -185 * mapSize + 300 + yDiff, 256 * mapSize, 192 * mapSize,
            null);

        // Draw the game objects
        g.drawImage(pizzeriaExt, -2170 + xDiff, -900 + yDiff, 77 * 10, 24 * 10, null);
        // g.setColor(new Color(1, 1, 1, .3f));
        // g.fillRect(-2170 + 350 + xDiff, -832 + 110 + yDiff, 80, 50);

        if (quest3Done) {
          boss.drawMe(g, xDiff, yDiff);
        }

        // draw ordered things
        for (Basic each : drawOrder) {
          if (each == p1) {
            p1.drawMe(g);
            g.drawImage(anims.getAnimation(aP, wP, sP, dP), playerXMin, playerYMin - faceYDiff, 48, 48, null);
          } else if (each.enabled()) {
            each.drawMe(g, xDiff, yDiff);
          }
        }

        if (bossFight) {
          for (Projectile each : projectiles) {
            each.drawMe(g, xDiff, yDiff);
          }
        }
      }

      // hotbar
      for (int i = 0; i < hotBar.length; i++) {
        g.setColor(Color.lightGray);
        g.fillRect(285 + 60 * i, 540, 50, 50);
        if (hotBar[i] != null) {
          hotBar[i].drawMe(g, 310 + 60 * i, 565, 34, 34);
          hotBar[i].checkCollision();
        }
      }
    }

    if (inInv && !mainMenu) {
      g.setColor(Color.white);
      g.fillRect(200, 75, 400, 450);
      // inventory
      int x = 225;
      for (int i = 0; i < inventory.length; i++) {
        int y = 85;
        for (int j = 0; j < inventory[0].length; j++) {
          g.setColor(Color.lightGray);
          g.fillRect(x + j * 125, y + i * 110, 100, 100);
          if (inventory[i][j] != null) {
            inventory[i][j].checkCollision();
            inventory[i][j].drawMe(g, x + j * 125 + 50, y + i * 110 + 50, 80, 80);
          }
        }
      }

      if (held != null) {
        held.drawMe(g, (int) MouseInfo.getPointerInfo().getLocation().getX() - 5,
            (int) MouseInfo.getPointerInfo().getLocation().getY() - 30, 34, 34);
      }
    }

    if (!mainMenu && !gameEnd) {
      g.setColor(Color.white);
      g.drawString("Coordinates: " + -xDiff + ", " + -yDiff, 10, 10);

      g.setFont(new Font("Arial", Font.BOLD, 30));
      g.drawString("Quests:", 10, 30);
      g.setFont(new Font("Arial", Font.PLAIN, 15));
      for (int i = 0; i < questList.size(); i++) {
        g.drawString(questList.get(i).toString(), 15, 57 + 20 * i);
      }
    }

    if(mapOpen){
      g.setColor(new Color(0,0,0,0.5f));
      g.drawRect(0,0,800,600);
      int mapSize = 3;
      g.drawImage(map, (800-256*mapSize)/2, (600-192*mapSize)/2, 256 * mapSize, 192 * mapSize,
            null);
      //player mini
      g.setColor(Color.red);
      g.fillOval((int)((768.0/8120.0)*-xDiff+16+2304000.0/8120),(int)((576.0/3840.0)*-yDiff+567), 20, 20);
    }
  }

  public void animate() {
    while (true) {

      // make sure you can transition smoothly
      if (!mainMenu) {
        ArrayList<Barrier> inside = new ArrayList<Barrier>();
        for (Barrier each : walkies) {
          if (inDoors == each.inside()) {
            if (each.xMin() + xDiff <= playerXMax && each.yMin() + yDiff <= playerYMax
                && each.xMax() + xDiff >= playerXMin
                && each.yMax() + yDiff >= playerYMin) {
              inside.add(each);
            }
          }
        }
        if (aP) {
          boolean canLeft = false;
          for (Barrier chaque : inside) {
            if (chaque.xMin() + xDiff <= playerXMin && inDoors == chaque.inside())
              canLeft = true;
          }
          if (canLeft)
            xDiff += 3;
        }
        if (wP) {
          boolean canUp = false;
          for (Barrier chaque : inside) {
            if (chaque.yMin() + yDiff <= playerYMin && inDoors == chaque.inside())
              canUp = true;
          }
          if (canUp)
            yDiff += 3;
        }
        if (dP) {
          boolean canRight = false;
          for (Barrier chaque : inside) {
            if (chaque.xMax() + xDiff >= playerXMax && inDoors == chaque.inside())
              canRight = true;
          }
          if (canRight)
            xDiff -= 3;
        }
        if (sP) {
          boolean canDown = false;
          for (Barrier chaque : inside) {
            if (chaque.yMax() + yDiff >= playerYMax && inDoors == chaque.inside())
              canDown = true;
          }
          if (canDown)
            yDiff -= 3;
        }
        // animation
        if (sP || aP || wP || dP) {
          playerAnimTimer += 10;
          if (playerAnimTimer >= 200) {
            p1.switchSprite();
            playerAnimTimer = 0;
            switch (faceYDiff) {
              case -4:
                faceYDiff = -1;
                break;
              case -1:
                faceYDiff = -4;
                break;
            }
          }
        }

        // radius for music: 530
        if (!inDoors) {
          double dist = Math.sqrt(Math.pow(-1790 + xDiff - 400, 2) + Math.pow(-520 + yDiff - 300, 2));
          if (dist < partyRadius) {
            double distRange = -1.0 / partyRadius * dist + 1;
            FloatControl vol = (FloatControl) revenge.getControl(FloatControl.Type.MASTER_GAIN);
            if (distRange <= 1 && distRange > 0.0001)
              vol.setValue(20 * (float) Math.log10(distRange));
            vol = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            fadeMice = true;
          } else if (fadeValMice <= 1 && !bossFight) {
            FloatControl vol = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
            fadeValMice += 0.01;
            vol.setValue(20 * (float) Math.log10(fadeValMice));
          }
        }

        if (!inInv) {
          // collision prompt
          for (Item each : itemList) {
            each.checkCollision();
          }
          for(Item each : indoorItemList){
            each.checkCollision();
          }
          for (Character each : npcList) {
            each.checkCollision();
          }
          if (quest3Done) {
            boss.checkCollision();
          }
        }
        ralof.incLength();
        creeper.incLength();
        nguyen.incLength();
        boss.incLength();

        if (inDoors) {
          creeper.checkCollision();
          if (pizzeriaBounds[4] + xDiff < playerXMax && pizzeriaBounds[6] + xDiff > playerXMin
              && pizzeriaBounds[5] + yDiff < playerYMax
              && pizzeriaBounds[7] + yDiff > playerYMin) {
            inDoors = false;
            long pos = revengeLoud.getMicrosecondPosition();
            playSound(revenge, pos);
            revengeLoud.stop();
          }
        } else {
          if (pizzeriaBounds[0] + xDiff < playerXMax && pizzeriaBounds[2] + xDiff > playerXMin
              && pizzeriaBounds[1] + yDiff < playerYMax
              && pizzeriaBounds[3] + yDiff > playerYMin) {
            inDoors = true;
            long pos = revenge.getMicrosecondPosition();
            playSound(revengeLoud, pos);
            revenge.stop();
          }
        }
      }
      // boss fight
      if (quest3Done) {
        setUpBoss();
        updateProjectiles();
        if (bossFight) {
          if (bossFightTimer == bossFightLength / 3 * 1000) {
            projectiles.add(new Projectile(1, arenaY, projSize));
            timer = 0;
            projectileSpeedRange[0] = 4;
            projectileSpeedRange[1] = 6;
            bossCooldown = 1000;
          } else if (bossFightTimer == bossFightLength * 2 / 3 * 1000) {
            projectiles.add(new Projectile(2, arenaY, projSize));
            timer = 0;
            projectileSpeedRange[0] = 7;
            projectileSpeedRange[1] = 9;
          } else if (bossFightTimer == bossFightLength * 1000) {
            bossFight = false;
            quest4Done = true;
            quest4Text = new QuestTrack("Leave on the Boat");
            boss.nextStage(bossLines2);
            endSong = true;
            walkies.add(boatBounds);
          } else if (timer >= bossCooldown) {
            if (arenaMax + xDiff <= playerXMax) {
              projectiles.add(
                  new Projectile(arenaMax, arenaY, projSize, projectileSpeedRange[0], projectileSpeedRange[1], true));
            } else if (arenaMin + xDiff > playerXMin) {
              projectiles.add(
                  new Projectile(arenaMin, arenaY, projSize, projectileSpeedRange[0], projectileSpeedRange[1], false));
            } else {
              projectiles.add(
                  new Projectile(arenaMin, arenaMax, arenaY, projSize, projectileSpeedRange[0],
                      projectileSpeedRange[1]));
            }
            timer = 0;
          }
          timer += 10;
          bossFightTimer += 10;
        }
      }
      if (quest4Done && endSong) {
        if (fadeVal > 0.0001) {
          FloatControl vol = (FloatControl) mega.getControl(FloatControl.Type.MASTER_GAIN);
          fadeVal -= 1 / (fadeTime * 100);
          if (fadeVal > 0.0001) {
            vol.setValue(20 * (float) Math.log10(fadeVal));
          } else {
            endSong = false;
          }
        }
        if (fadeValMice <= 1) {
          FloatControl vol = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
          fadeValMice += 0.01;
          vol.setValue(20 * (float) Math.log10(fadeValMice));
        }
      }

      if (gameEnd) {
        gameEndAnimTimer += 10;
        if (gameEndAnimTimer >= 500) {
          if (curBoat == boat1)
            curBoat = boat2;
          else if (curBoat == boat2)
            curBoat = boat1;
          gameEndAnimTimer = 0;
        }
      }

      if (mainMenu) {
        if (ts2VShift >= 600) {
          ts2VShift = -600;
        } else if (ts1VShift >= 600) {
          ts1VShift = -600;
        }
        ts1VShift += tsSpeed;
        ts2VShift += tsSpeed;
      }
      if (fadeMice) {
        if (fadeValMice > 0.0001) {
          FloatControl vol = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
          vol.setValue(20 * (float) Math.log10(fadeValMice));
          fadeValMice -= 0.01;
        } else {
          fadeMice = false;
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

  // implement methods of the KeyListener
  public void keyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
      aP = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
      wP = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
      dP = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
      sP = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_E) {
      if (!mainMenu && !inInv && !gameEnd) {
        // check items
        ArrayList<Item> tempList;
        if(inDoors){
          tempList = indoorItemList;
        }
        else{
          tempList = itemList;
        }
        for (int i = 0; i < tempList.size(); i++) {
          if (tempList.get(i).checkCollision()) {
            ArrayList<Integer> openA = findOpenSlot();
            Item object = tempList.get(i);
            if (openA.get(0) != -1) {
              if (openA.size() == 1) {
                hotBar[openA.get(0)] = tempList.remove(i);
              } else {
                inventory[openA.get(0)][openA.get(1)] = tempList.remove(i);
              }
              // quests
              if (object.type().equals("diamond") && questList.contains(quest2Text)) {
                heldDiamonds++;
                quest2Text.changeLine(quest2Text.toString().substring(0, quest2Text.toString().indexOf('(')) + "("
                    + heldDiamonds + "/3)");
                if (heldDiamonds >= 3) {
                  quest2Text.changeLine("Talk to Ralof's Friend");
                }
              } else if (object.type().equals("card") && questList.contains(quest3Text)) {
                heldCards++;
                quest3Text.changeLine(
                    quest3Text.toString().substring(0, quest3Text.toString().indexOf('(')) + "(" + heldCards + "/4)");
                if (heldCards >= 4) {
                  quest3Text.changeLine("Talk to the CS Teacher");
                }
              }
            }
          }
        }
        // check npcs
        for (int i = 0; i < npcList.size(); i++) {
          if (npcList.get(i).checkCollision()) {
            // if character && quest started
            Character current = npcList.get(i);

            if (current.interacted() == 2) {
              // ralof
              if (current.type().equals("ralof")) {
                current.repeatLastLine();
                if (current.getStage() == 0 && !questList.contains(quest1Text)) {
                  questList.add(quest1Text);
                  creeper.nextStage(creeperLines1);
                }
              }

              else if (current.type().equals("nguyen")) {
                if (!quest2HalfDone && questList.contains(quest2HalfText)) {
                  questList.remove(quest2HalfText);
                  quest2HalfDone = true;
                }
                if (!quest3Done) {
                  // remove 4 cards, finishes 3rd quest
                  ArrayList<Integer> toRemove = findItemsOfType("card");
                  if (toRemove.size() >= 8) {
                    for (int j = 0; j < toRemove.size(); j += 2) {
                      if (toRemove.get(j + 1) == -1) {
                        hotBar[toRemove.get(j)] = null;
                      } else {
                        inventory[toRemove.get(j)][toRemove.get(j + 1)] = null;
                      }
                    }
                    nguyen.nextStage(nguyenLines2);
                    quest3Done = true;
                    questList.remove(quest3Text);
                  }
                }
                if (current.getStage() == 1 && !questList.contains(quest3Text)) {
                  questList.add(quest3Text);
                } else if (current.getStage() == 2 && !questList.contains(quest4Text)) {
                  questList.add(quest4Text);
                }
                nguyen.repeatLastLine();
              }
            }
          }
        }
        if (creeper.checkCollision() && inDoors && creeper.interacted() == 2) {
          if (!quest1Done && creeper.getStage() == 1) {
            // finish 1st quest
            ArrayList<Integer> toRemove = findItemsOfType("letter");
            if (toRemove.size() >= 2) {
              for (int j = 0; j < toRemove.size(); j += 2) {
                if (toRemove.get(j + 1) == -1) {
                  hotBar[toRemove.get(j)] = null;
                } else {
                  inventory[toRemove.get(j)][toRemove.get(j + 1)] = null;
                }
              }
              creeper.nextStage(creeperLines2);
              ralof.nextStage(ralofLines2);
              quest1Done = true;
              questList.remove(quest1Text);

            }
          } else if (!quest2Done) {
            // remove 3 diamonds, finishes 2nd quest
            ArrayList<Integer> toRemove = findItemsOfType("diamond");
            if (toRemove.size() >= 6) {
              for (int j = 0; j < toRemove.size(); j += 2) {
                if (toRemove.get(j + 1) == -1) {
                  hotBar[toRemove.get(j)] = null;
                } else {
                  inventory[toRemove.get(j)][toRemove.get(j + 1)] = null;
                }
              }
              creeper.nextStage(creeperLines3);
              nguyen.nextStage(nguyenLines);
              quest2Done = true;
              questList.remove(quest2Text);

            }
            if (creeper.getStage() == 2 && !questList.contains(quest2Text)) {
              questList.add(quest2Text);
            }
          } else if (quest2Done) {
            if (!questList.contains(quest2HalfText) && !quest2HalfDone) {
              questList.add(quest2HalfText);
            }
          }
          creeper.repeatLastLine();
        }
        if (boss.checkCollision() && boss.interacted() == 2 && !quest4Done && !bossFight) {
          bossFight = true;
          playSound(mega, 0);
          quest4Text = new QuestTrack("Survive");
          fadeMice = true;
          timer = 1000;
        }
      }
    }
    // tab
    if (e.getKeyCode() == KeyEvent.VK_TAB) {
      if(!mainMenu && !gameEnd){
        if(inInv && held != null){
          if(inDoors){
            dropHeld(indoorItemList);
          }
          else{
            dropHeld(itemList);
          }
        }
        inInv = !inInv;
      }
    }
    // cheat
    if (e.getKeyCode() == KeyEvent.VK_O) {
      if (!quest1Done) {
        quest1Done = true;
        ralof.nextStage(ralofLines2);
        creeper.nextStage(creeperLines2);
      } else if (!quest2Done) {
        quest2Done = true;
        creeper.nextStage(creeperLines3);
        nguyen.nextStage(nguyenLines);
      } else if (!quest3Done) {
        quest3Done = true;
        nguyen.nextStage(nguyenLines2);
      } else if (!quest4Done) {
        quest4Done = true;
        bossFight = false;
        gameEnd = true;
        walkies.clear();
        walkies.add(new Barrier(350, 190, 450, 370, false));
        xDiff = 0;
        yDiff = 0;
      }
    }

    // build trees
    if (e.getKeyCode() == KeyEvent.VK_0) {
      trees.addTree(400 - xDiff, 300 - yDiff);
    }

    //map
    if(e.getKeyCode() == KeyEvent.VK_M){
      if(!gameEnd && !mainMenu && !inInv){
        mapOpen = !mapOpen;
      }
    }

    repaint();

  }

  private ArrayList<Integer> findOpenSlot() {
    ArrayList<Integer> array = new ArrayList<Integer>();
    for (int i = 0; i < hotBar.length; i++) {
      if (hotBar[i] == null) {
        array.add(i);
        return array;
      }
    }
    for (int i = 0; i < inventory.length; i++) {
      for (int j = 0; j < inventory[0].length; j++) {
        if (inventory[i][j] == null) {
          array.add(i);
          array.add(j);
          return array;
        }
      }
    }
    array.add(-1);
    return array;
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
      aP = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
      wP = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
      dP = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
      sP = false;
    }

    repaint();
  }

  public void keyTyped(KeyEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
    if (mainMenu) {
      if (e.getX() > playX && e.getX() < playX + playSizeX * 3 && e.getY() > playY
          && e.getY() < playY + playSizeY * 3) {
        mainMenu = false;
        try {
          playSound(revenge, 0);
          FloatControl vol = (FloatControl) revenge.getControl(FloatControl.Type.MASTER_GAIN);
          vol.setValue(20 * (float) Math.log10(0));

        } catch (Exception a) {
        }
      }
    } else if (findSlot(e) != null && inInv) {
      int[] loc = findSlot(e);
      if (loc.length == 1) {
        Item temp = hotBar[loc[0]];
        hotBar[loc[0]] = held;
        held = temp;
      } else {
        Item temp = inventory[loc[0]][loc[1]];
        inventory[loc[0]][loc[1]] = held;
        held = temp;
      }
    }
    repaint();
  }

  public void mousePressed(MouseEvent e) {
    if (inInv && held != null) {
      if (e.getX() < 200 || e.getX() > 600) {
        if(inDoors){
          dropHeld(indoorItemList);
        }
        else{
          dropHeld(itemList);
        }
      }
    }
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {

  }
  private void dropHeld(ArrayList<Item> list){
    switch (held.type()) {
      case "diamond":
        list.add(new Diamond(400 - xDiff, 300 - yDiff, 50));
        break;
      case "letter":
        list.add(new Letter(400 - xDiff, 300 - yDiff, 50));
        break;
      case "card":
        list.add(new Card(400 - xDiff, 300 - yDiff, 50, held.getSuit()));
        break;
    }
    held = null;
  }

  private ArrayList<Integer> findItemsOfType(String type) {
    ArrayList<Integer> list = new ArrayList<Integer>();
    for (int j = 0; j < hotBar.length; j++) {
      if (hotBar[j] != null && hotBar[j].type().equals(type)) {
        list.add(j);
        list.add(-1);
      }
    }
    for (int r = 0; r < inventory.length; r++) {
      for (int c = 0; c < inventory[0].length; c++) {
        if (inventory[r][c] != null && inventory[r][c].type().equals(type)) {
          list.add(r);
          list.add(c);
        }
      }
    }
    return list;
  }

  private int[] findSlot(MouseEvent e) {
    for (int i = 0; i < hotBar.length; i++) {
      if (e.getX() > 285 + 60 * i && e.getX() < 335 + 60 * i && e.getY() > 540 && e.getY() < 590) {
        int[] a = { i };
        return a;
      }
    }
    for (int i = 0; i < inventory.length; i++) {
      int y = 85;
      for (int j = 0; j < inventory[0].length; j++) {
        if (e.getX() > 225 + j * 125 && e.getX() < 325 + j * 125 && e.getY() > y + i * 110
            && e.getY() < y + 100 + i * 110) {
          int[] a = { i, j };
          return a;
        }
      }
    }
    return null;
  }

  private void setUpBoss() {
    if (walkies.size() == 3 && walkies.get(2).xMin() + xDiff <= playerXMin
        && walkies.get(2).yMin() + yDiff <= playerYMin
        && walkies.get(2).xMax() + xDiff >= playerXMax
        && walkies.get(2).yMax() + yDiff >= playerYMax) {
      walkies.remove(1);
      if (walkies.get(1) == boatBounds) {
        gameEnd = true;
        walkies.clear();
        xDiff = 0;
        yDiff = 0;
        walkies.add(new Barrier(350, 190, 450, 370, false));
      }
    }
  }

  private void playSound(Clip clip, long pos) {
    try {
      clip.setMicrosecondPosition(pos);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
    }
  }

  private void die() {
    quest4Text = new QuestTrack("Leave on the Boat");
    bossFight = false;
    walkies.add(1, mapBounds);
    if (mega.isActive()) {
      mega.stop();
    }
    boss.nextStage(bossLines1);
    projectiles.clear();
    xDiff = 1093;
    yDiff = 2543;
    bossFightTimer = 0;
    timer = 0;
    projectileSpeedRange[0] = 1;
    projectileSpeedRange[1] = 3;
    fadeValMice = 1;
  }

  private void updateProjectiles() {
    for (Projectile each : projectiles) {
      // chack for collisison
      if (each.hit(xDiff, yDiff, playerXMin, playerYMin, playerXMax, (int) (playerYMax - 6.0 * playerY / 16))
          && !quest4Done) {
        die();
        return;
      }
      each.move();
    }
  }

}