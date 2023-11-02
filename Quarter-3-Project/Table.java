import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Font;


public class Table extends JPanel implements ActionListener {

  private ArrayList<Card> deck;
  private ArrayList<Card> player;
  private ArrayList<Card> dealer;
  private ArrayList<Card> toFlip;
  private JButton hit, stand, replay;
  private boolean dealerTurn = false, gameOver = false, updateScore = true, once = true, incDY = true;
  private int value = 0, dealerVal = 0,playerWins=0,dealerWins=0, iVal = 0;
  private double speedX=0, speedY=0, dX = 500, dY = 250;
  private Card drawing;

  public Table() {

    setLayout(null);
    deck = new ArrayList<Card>();
    toFlip = new ArrayList<Card>();

    String suit = "";
    for (int i = 0; i < 4; i++) {
      switch (i) {
        case 0:
          suit = "h";
          break;
        case 1:
          suit = "d";
          break;
        case 2:
          suit = "s";
          break;
        case 3:
          suit = "c";
          break;
      }
      
      for(int j=2;j<=10;j++){
        if(j==10){
          deck.add(new Card(10, "j", suit));
          deck.add(new Card(10, "q", suit));
          deck.add(new Card(10, "k", suit));
          deck.add(new Card(11, "a", suit));
        }
        else{
          deck.add(new Card(j, j+"", suit));
        }
      }
    }

    shuffle();

    player = new ArrayList<Card>();
    player.add(deck.get(0));
    deck.remove(0);
    player.add(deck.get(0));
    deck.remove(0);
    toFlip.add(player.get(0));
    toFlip.add(player.get(1));

    dealer = new ArrayList<Card>();
    dealer.add(deck.get(0));
    deck.remove(0);
    dealer.add(deck.get(0));
    deck.remove(0);
    toFlip.add(dealer.get(0));

    hit = new JButton("Hit");
    hit.setBounds(30, 20, 100, 40);
    hit.addActionListener(this);
    stand = new JButton("Stand");
    stand.setBounds(180, 20, 100, 40);
    stand.addActionListener(this);
    replay = new JButton("Play Again");
    replay.setBounds(200, 335, 130, 40);
    replay.addActionListener(this);

    add(hit);
    add(stand);
    add(replay);
    replay.setVisible(false);
    replay.setEnabled(true);
  }

  public Dimension getPreferredSize() {
    // Sets the size of the panel
    return new Dimension(800, 600);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setFont(new Font("Arial", Font.PLAIN, 20));
    g.setColor(new Color(0,150,0));
    g.fillRect(0, 0, 800, 600);
    g.setColor(Color.black);

    value = 0;
    dealerVal = 0;
    iVal = 0;
    drawHand(g, deck, 500,250);
    drawHand(g, player, 30, 400);
    drawHand(g, dealer, 30, 100);
    if(gameOver){
      replay.setVisible(true);
      if(updateScore){
        if (dealerVal > 21) {
          playerWins++;
        } 
        else if(value > 21){
          dealerWins++;
        }
        else if (dealerVal > value) {
          dealerWins++;
        } 
        else if (dealerVal < value) {
          playerWins++;
        }
        updateScore = false;
      }
      if(!dealerTurn){
        endGame(g, "Player Bust");
      }
      else{
        if (dealerVal > 21) {
          endGame(g, "Dealer Bust");
        } else if (dealerVal > value) {
          endGame(g, "Dealer Wins");
        } else if (dealerVal < value) {
          endGame(g, "Player Wins");
        } else if(dealerVal == value){
          endGame(g, "Tie");
        }
      }
    }
    else{
      replay.setVisible(false);
      if(dealerTurn){
        if (dealerVal < 17 && drawing == null && dealer.get(dealer.size()-1).faceUp()) {
          dealer.add(deck.get(0));
          toFlip.add(deck.get(0));
          drawing = deck.get(0);
          deck.remove(0);
        } else if(dealerVal >= 17) {
          gameOver = true;
        }
      }
    }
    g.drawString("Player Card Value: " + value, 300, 50);
    g.drawString("Dealer Card Value: " + dealerVal, 300, 100);
    g.drawString("Player: "+playerWins+"   Dealer: "+dealerWins,20,90);
  }

  public void shuffle() {
    for (int i = 0; i < deck.size(); i++) {
      int rand = (int) (Math.random() * deck.size());
      Card temp = deck.get(i);
      deck.set(i, deck.get(rand));
      deck.set(rand, temp);
    }
  }

  private void drawHand(Graphics g, ArrayList<Card> array, int x, int y) {
    for (int i = 0; i < array.size(); i++) {
       if (array.get(i) == drawing){
        if(once){
          speedX = (x-dX)/50.0;
          speedY = (y-dY)/50.0;
          if(speedY<0){
            incDY = false;
          }
          else{
            incDY = true;
          } 
          once = false;
        }
        array.get(i).drawMe(g, (int)dX, (int)dY);
        if(incDY && dY >= y || !incDY && dY <= y){
          dY = y;
          dX = x;
          if(dX <= x){
            drawing = null;
            once = true;
            dX = 500;
            dY = 250;
          }
        }
      }
      else{
        array.get(i).drawMe(g, x, y);
      } 
      if (array == player) {
        iVal += array.get(i).getValue();
        if(player.get(i).faceUp()){
          value += array.get(i).getValue();
        }
        if (value > 21) {
          gameOver = true;
        }
        else if(value == 21){
          hit.setEnabled(false);
        }
        x += 80;
      }
      else if(array == deck){
        x += 1;
        y -= 1;
        if(i > 18){
          return;
        }
      } else if (array == dealer) {
        if (dealerTurn) {
          if(dealer.get(i).faceUp()){
            dealerVal += array.get(i).getValue();
          }
        } 
        else{
          dealerVal = array.get(0).getValue();
        }
        if (dealerVal > 21) {
          gameOver = true;
        }
        x += 80;
      }
    }
  }

  private void endGame(Graphics g, String str) {
    g.drawString(str, 20, 350);
    hit.setEnabled(false);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == hit) {
      if(iVal <= 21){
        boolean allPFaceUp = true;
        for(Card each : player){
          if(!each.faceUp()){
            allPFaceUp = false;
          }
        }
        if(allPFaceUp){
          player.add(deck.get(0));
          toFlip.add(deck.get(0));
          drawing = deck.get(0);
          deck.remove(0);
        }
      }
    } else if (e.getSource() == stand) {
      if (!dealerTurn && !gameOver){
        toFlip.add(dealer.get(1));
        dealerTurn = true;
      }
    } else if (e.getSource() == replay) {

      dealerTurn = false;
      gameOver = false;
      updateScore = true;
      hit.setEnabled(true);
      

      for(int i=player.size()-1;i>=0;i--){
        deck.add(player.get(i));
        player.get(i).reset();
        player.remove(i);
      }
      for(int i=dealer.size()-1;i>=0;i--){
        deck.add(dealer.get(i));
        dealer.get(i).reset();
        dealer.remove(i);
      }

    shuffle();

      player.add(deck.get(0));
      deck.remove(0);
      player.add(deck.get(0));
      deck.remove(0);
      toFlip.add(player.get(0));
      toFlip.add(player.get(1));
      
      dealer.add(deck.get(0));
      deck.remove(0);
      dealer.add(deck.get(0));
      deck.remove(0);
      toFlip.add(dealer.get(0));

    }
    repaint();
  }

  public void animate() {
    while (true) {

      if (toFlip.size() >= 1) {
        for (int i = 0; i < toFlip.size(); i++) {
          if(toFlip.get(i) != drawing){
            if (toFlip.get(i).flip() == true) {
              toFlip.remove(i);
            }
          }
        }
      }
      if(drawing != null){
        dX+= speedX;
        dY+= speedY;
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

}
