import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;

public class Screen extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

    public static DLList<Card> deck = new DLList<Card>();
    private DLList<Card> hand = Hand.hand;
    private DLList<Card> dummyList = new DLList<Card>();
    private int handValue = 0;
    private int winnings = 0;
    private int points;
    private JButton startGame;
    private Vector2 deckPos;
    private Card deckCard;
    private boolean[] keepCards;
    private RuleSheet rules;
    private boolean drawEnabled;
    private boolean gameNotInProgress = true;
    private boolean firstGame = true;
    private int cardSpacing, handXPos;
    private int consecutiveLosses = 0;

    public Screen() {
        this.setLayout(null);

        SoundManager.playSound(Sound.DRAWSOUND);
        deckPos = new Vector2(75, 100);
        deckCard = new Card(deckPos.intX(), deckPos.intY());
        keepCards = new boolean[5];
        rules = new RuleSheet(this);
        cardSpacing = 90;
        handXPos = 15;

        int dx = 1;
        int dy = 1;
        for(int i=0;i<10;i++){
            dummyList.add(new Card(deckPos.intX()-dx+10, deckPos.intY()-dy+10));
            dx++; dy++;
        }

        Suit curSuit = Suit.HEARTS;
        for(int i=0;i<4;i++){
            switch(i){
                case 1:
                    curSuit = Suit.DIAMONDS;
                    break;
                case 2:
                    curSuit = Suit.CLUBS;
                    break;
                case 3:
                    curSuit = Suit.SPADES;
                    break;
            }

            for (int j = 2; j < 15; j++) {
                deck.add(new Card(j, curSuit));
            }
        }

        points = 50;
        startGame = setUpJButton("Start Game", 300, 300, 200, 50, true);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(10, 100, 10));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.black);

        if(cardsToRemove != null){
            for(Card each : cardsToRemove){
                each.drawMe(g);
            }
        }

        for(int i=0;i<dummyList.size();i++){
            dummyList.get(i).drawMe(g);
        }
        deckCard.drawMe(g);

        for(int i=0;i<hand.size();i++){
            Card c = hand.get(i);
            c.drawMe(g);
            c.getLock().drawMe(g);
            if(!c.doneMoving()) break;
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        g.drawString("E-Bucks: "+points, 400, 200);
        g.drawString("Hand value: "+handValue, 400, 260);
        g.setColor(Color.black);


        rules.drawMe(g);

        if(gameNotInProgress){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0,0,800,600);
            if(!firstGame){
                String s = "";
                switch(winnings){
                    case 0: s = consecutiveLosses < 3 ? "You lose!" : "THE HOUSE ALWAYS WINS."; break;
                    case 1: s = "You win 1 point!"; break;
                    default: s = "You win "+winnings+" points!"; break;
                }
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Arial", Font.BOLD, consecutiveLosses < 3 ? 36 : 55));
                g.drawString(s, 400-g.getFontMetrics().stringWidth(s)/2, 250);
            }
            
            g.setColor(Color.black);
        }

    }

    private void getWinnings(){
        points += handValue;
        winnings = handValue;
        consecutiveLosses = winnings == 0 ? consecutiveLosses+1 : 0;
        startGame.setVisible(true);
    }

    private float pacingTimer = 0;
    private boolean waitToGetWinnings;
    private boolean waitForHandToMove;
    private boolean waitForShuffle;
    private boolean shuffleSoundPlayed;
    private Card[] cardsToRemove = null;
    public void animate(){
        while(true){

            rules.update(.01f);
            for(int i=0;i<hand.size();i++){
                hand.get(i).getLock().update(0.01f);
            }

            if(cardsToRemove != null){
                for(int i=0;i<cardsToRemove.length;i++){
                    cardsToRemove[i].update(.01f);
                    if(i == cardsToRemove.length-1 && cardsToRemove[i].doneMoving()){
                        cardsToRemove = null;
                        break;
                    }
                }
            }
            else if(waitForShuffle){
                if(!shuffleSoundPlayed && pacingTimer >= 0.5){
                    SoundManager.playSound(Sound.SHUFFLESOUND);
                    shuffleSoundPlayed = true;
                }
                if(pacingTimer >= 2){
                    waitForShuffle = false;
                    pacingTimer = 0;
                }
                else pacingTimer += 0.01f;
            }
            else if(waitForHandToMove){
                for(int i=0;i<hand.size();i++){
                    hand.get(i).update(.01f);
                    if(!hand.get(i).soundPlayed() && hand.get(i).animProgress(0.75f) >= 0.75 && !hand.get(i).doneMoving()){
                        SoundManager.playSound(Sound.DRAWSOUND);
                    }
                    hand.get(i).getLock().unlock();
                    if(!hand.get(i).doneMoving())
                        break;
                    if(i == hand.size()-1 && hand.get(i).doneFlipping())
                        waitForHandToMove = false;
                        handValue = Hand.value();
                }
            }
            else if(waitToGetWinnings){
                if(pacingTimer >= 1){
                    waitToGetWinnings = false;
                    getWinnings();
                    gameNotInProgress = true;
                    pacingTimer = 0;
                }
                else pacingTimer += 0.01;
            }
        


            repaint();
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){Thread.currentThread().interrupt();}
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == startGame){
            gameNotInProgress = false;
            //resetting
            keepCards = new boolean[5];
            if(hand.size() != 0) cardsToRemove = new Card[hand.size()];
            for(int i=0;i<hand.size();i++){
                cardsToRemove[i] = hand.get(i);
                cardsToRemove[i].moveTo(deckPos.clone());
                cardsToRemove[i].flip();
                cardsToRemove[i].hovered(false);
            }

            //setup
            points--;
            shuffleDeck();
            Hand.replaceHand(new boolean[5]);
            int x=handXPos;
            for(int i=0;i<hand.size();i++){
                hand.get(i).setPos(deckPos.intX(), deckPos.intY());
                hand.get(i).moveTo(new Vector2(x+i*cardSpacing, 400));
                hand.get(i).flip();
            }
            startGame.setVisible(false);
            drawEnabled = true;
            waitForHandToMove = true;
            waitForShuffle = true;
            if(!firstGame)
                SoundManager.playSound(Sound.GATHERSOUND);
            shuffleSoundPlayed = false;
            firstGame = false;
        }

        repaint();
    }
    public void mouseMoved(MouseEvent e){
        boolean selectable = true;
        if(cardsToRemove != null || waitForHandToMove) return;
        for(int i=hand.size()-1;i>=0;i--){
            if(selectable && hand.get(i).mouseOver(new Vector2(e.getX(), e.getY()))){
                hand.get(i).hovered(true);
                selectable = false;
            }
            else{
                hand.get(i).hovered(false);
            }
        }
        if(drawEnabled && selectable && deckCard.mouseOver(new Vector2(e.getX(), e.getY()))){
            deckCard.hovered(true);
            selectable = false;
        } else deckCard.hovered(false);
        for(int i=0;i<dummyList.size();i++){
            dummyList.get(i).hovered(deckCard.isHovered());
        }
        repaint();
    }
    public void mouseEntered(MouseEvent e){
        
    }
    public void mouseExited(MouseEvent e){
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){
        if(drawEnabled){
            if(deckCard.isHovered()){
                drawCards();
                deckCard.hovered(false);
                for(int i=0;i<dummyList.size();i++){
                    dummyList.get(i).hovered(false);
                }       
            }
            for(int i=0;i<hand.size();i++){
                if(hand.get(i).isHovered()){
                    keepCards[i] = !keepCards[i];
                    hand.get(i).getLock().lock();
                }
            }
        }

        if(rules.isClicked(new Vector2(e.getX(), e.getY()))){
            rules.click();
        }
        else{
            rules.close();
        }

        repaint();
    }
    public void mouseClicked(MouseEvent e){

    }
    public void mouseDragged(MouseEvent e){

    }

    private void drawCards(){
        drawEnabled = false;
        Card[][] changingCards = Hand.replaceHand(keepCards);
        cardsToRemove = changingCards[0];
        Card[] newCards = changingCards[1];

        
        for(int i=0;i<cardsToRemove.length;i++){
            Vector2 bottomDeckPos = deckPos.clone();
            bottomDeckPos.add(new Vector2(10, 10));
            cardsToRemove[i].moveTo(bottomDeckPos);
            cardsToRemove[i].flip();
        }


        for(int i=0;i<newCards.length;i++){
            newCards[i].setPos(deckPos.intX(), deckPos.intY());
            newCards[i].flip();
        }
        int x = handXPos;
        for(int i=0;i<hand.size();i++){
            hand.get(i).moveTo(new Vector2(x+i*cardSpacing, 400));
        }
        waitForHandToMove = true;
        waitToGetWinnings = true;
        if(cardsToRemove.length > 1)
            SoundManager.playSound(Sound.GATHERSOUND);
        else if(cardsToRemove.length == 1)
            SoundManager.playSound(Sound.DRAWSOUND);
        else if(cardsToRemove.length == 0)
            cardsToRemove = null;
    }



    private void shuffleDeck(){
        for(int i=0;i<deck.size();i++){
            int randex = (int)(Math.random()*deck.size());
            swap(deck, i, randex);
        }
    }

    

    public static void sortList(DLList<Card> list){
        for(int i=0;i<list.size()-1;i++){
            for(int j=0;j<list.size()-i-1;j++){
                if(list.get(j).number() > list.get(j+1).number()){
                    swap(list, j, j+1);
                }
            }
        }
    }

    private static void swap(DLList<Card> list, int ind1, int ind2){
        Card temp = list.get(ind1);
        list.set(ind1, list.get(ind2));
        list.set(ind2, temp);
    }

    private JButton setUpJButton(String text, int x, int y, int width, int height, boolean startingVisibility){
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.addActionListener(this);
        add(button);
        button.setEnabled(startingVisibility);
        return button;
    }
}