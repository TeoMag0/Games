import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.IOException;

public class Card{
    private int num;
    private Suit suit;
    private Vector2 size;
    private BufferedImage pic;
    private BufferedImage back;
    private BufferedImage curSide;
    private Vector2 pos;
    private Vector2 prevPos;
    private Vector2 moveTo = pos;
    private boolean hovered = false;
    private float moveSpeed;
    private float flipSpeed;
    private float time = 0;
    private boolean doneMoving = true;
    private boolean doneFlipping = true;
    private CardLock lock;
    private boolean soundPlayed;

    //flipping
    private boolean compress;
    private boolean extend;
    private float flipTime = 0;
    private int defaultXSize = (int)(63*2);
    private int defaultYSize = (int)(88*2);

    public Card(int n, Suit s){
        num = n;
        suit = s;
        size = new Vector2(defaultXSize, defaultYSize);
        moveSpeed = 2f;
        flipSpeed = 2f;
        pos = Vector2.zero();
        prevPos = pos.clone();
        lock = new CardLock(this);

        try{
            if(num > 10){
                String face = "";
                switch(num){
                    case 11: face = "j"; break;
                    case 12: face = "q"; break;
                    case 13: face = "k"; break;
                    case 14: face = "a"; break;
                }
                pic = ImageIO.read(new File(face+suit.val+".png"));
            }
            else
                pic = ImageIO.read(new File(num+suit.val+".png"));
            back = ImageIO.read(new File("back.png"));
            curSide = back;
        }catch(IOException e){System.out.println(e);}
    }
    public Card(int x, int y){
        size = new Vector2(defaultXSize, defaultYSize);
        pos = new Vector2(x, y);
        try{
            curSide = ImageIO.read(new File("back.png"));
        }catch(IOException e){System.out.println(e);}
    }

    public void update(float deltaTime){
        //position
        if(!pos.equals(moveTo)){
            pos.add(Vector2.multiply(Vector2.difference(moveTo, prevPos).normalized(), sineSpeed() * deltaTime));
            if(sineSpeed() < .01 && time > 0.01 || Vector2.difference(moveTo, prevPos).magnitude() < 3){
                pos = moveTo;
                prevPos.set(pos.getX(), pos.getY());
                doneMoving = true;
            }
            time += deltaTime;
        }
        else{
            time = 0;
            doneMoving = true;
        } 

        //flipping
        if(extend){
            size.set(size.getX() + sineFlip()*deltaTime, size.getY());
            pos.set(pos.getX() - sineFlip()*deltaTime/2, pos.getY());
            moveTo.set(moveTo.getX()-sineFlip()*deltaTime/2, moveTo.getY());
            prevPos.set(prevPos.getX()-sineFlip()*deltaTime/2, prevPos.getY());
            if(sineFlip() < 0.1 && flipTime > 0.01) {
                extend = false; 
                size.set(defaultXSize, size.getY());
                doneFlipping = true;
            }
            flipTime += deltaTime;
        }
        else if(compress){
            size.set(size.getX() - sineFlip()*deltaTime, size.getY());
            pos.set(pos.getX() + sineFlip()*deltaTime/2, pos.getY());
            moveTo.set(moveTo.getX()+sineFlip()*deltaTime/2, moveTo.getY());
            prevPos.set(prevPos.getX()+sineFlip()*deltaTime/2, prevPos.getY());
            if(size.getX() <= 0) { 
                extend = true; 
                compress = false;
                curSide = curSide == pic ? back : pic;
                size.set(-size.getX(), size.getY());
            }
            flipTime += deltaTime;
        }
        else flipTime = 0;
    }
    private float sineSpeed(){
        float amp = (float)Math.sqrt(Math.pow(moveTo.getX()-prevPos.getX(), 2) + Math.pow(moveTo.getY()-prevPos.getY(),2));
        return (float)(amp*moveSpeed * (-Math.cos(2*Math.PI*moveSpeed * time) + 1));
    }
    private float sineFlip(){
        float amp = 2.0f*defaultXSize;
        return (float)(amp*flipSpeed * (-Math.cos(2*Math.PI*flipSpeed * flipTime) + 1));
    }
    public void moveTo(Vector2 newPos){
        soundPlayed = false;
        moveTo = newPos;
        prevPos.set(pos.getX(), pos.getY());
        time = 0;
        //cards that are already there don't need to take time
        doneMoving = pos.intY() == moveTo.intY() ? true : false;
    }

    public void flip(){
        compress = true;
        doneFlipping = false;
    }
    public boolean doneFlipping(){
        return doneFlipping;
    }

    public void drawMe(Graphics g){
        g.drawImage(curSide, pos.intX(), pos.intY(), size.intX(), size.intY(), null);
    }
    public void setPos(int x, int y){
        pos.set(x, y);
    }
    public int number(){
        return num;
    }
    public boolean soundPlayed(){
        return soundPlayed;
    }
    public float animProgress(float thresh){
        if(time*moveSpeed >= thresh) soundPlayed = true;
        return time*moveSpeed;
    }
    public Suit suit(){
        return suit;
    }
    public void hovered(boolean h){
        if(hovered && !h){
            Vector2 newSize = Vector2.multiply(size, 10/11f);
            pos.set(pos.getX() - (newSize.getX() - size.getX())/2, 
                    pos.getY() - (newSize.getY() - size.getY())/2);
            size = newSize;
            hovered = false;
        }
        else if(!hovered && h){
            Vector2 newSize = Vector2.multiply(size, 1.1f);
            pos.set(pos.getX() - (newSize.getX() - size.getX())/2, 
                    pos.getY() - (newSize.getY() - size.getY())/2);
            size = newSize;
            hovered = true;
        }
    }
    public boolean isHovered(){
        return hovered;
    }
    public boolean mouseOver(Vector2 mouse){
        if(pos.getX() <= mouse.getX() && pos.getX() + size.getX() >= mouse.getX() &&
           pos.getY() <= mouse.getY() && pos.getY() + size.getY() >= mouse.getY())
           return true;
        return false;
    }

    public CardLock getLock(){
        return lock;
    }

    public boolean doneMoving(){
        return doneMoving;
    }

    public Vector2 getPos(){
        return pos.clone();
    }
    public Vector2 getSize(){
        return size.clone();
    }
    public Vector2 prevPos(){
        return prevPos;
    }

    @Override
    public String toString(){
        return num+ " of "+suit;
    }
}

enum Suit{
    HEARTS("h"),
    DIAMONDS("d"),
    CLUBS("c"),
    SPADES("s");

    String val;
    Suit(String v){
        val = v;
    }
}
