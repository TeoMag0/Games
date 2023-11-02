import java.awt.Graphics;
import java.awt.Color;

public class CardLock {

    private Color fullColor = new Color(215, 160, 50);
    private Color curColor;
    private float speed;
    private boolean enabled;
    private float yPos;
    private float unlockedPos;
    private int thickness;
    private int sideBarLength;
    private Card card;
    private float time;

    public CardLock(Card c){
        curColor = new Color(fullColor.getRed(), fullColor.getGreen(), fullColor.getBlue(), 0);
        speed = 2;
        unlockedPos = 100;
        yPos = unlockedPos;
        thickness = 8;
        sideBarLength = 40;
        card = c;
    }
    
    public void update(float deltaTime){
        if(enabled && yPos > 0){
            yPos -= sineSpeed(true) * deltaTime;
            if(yPos < 0) yPos = 0;
            int alpha = (int)((100-yPos)*2.55);
            curColor = new Color(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), alpha);
            time += deltaTime;
        }
        else if(!enabled && yPos < unlockedPos){
            yPos += sineSpeed(false) * deltaTime;
            if(yPos > 100) yPos = 100;
            int alpha = (int)((100-yPos)*2.55);
            curColor = new Color(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), alpha);
            time += deltaTime;
        }
        else time=0;
    }
    public float sineSpeed(boolean locking){
        float amp = unlockedPos*2;
        float shift = locking ? 1/(speed*2) : 0;
        return (float)(amp*speed * (-Math.cos(2*Math.PI*speed * (time + shift+.005)) + 1));
    }

    public void lock(){
        enabled = !enabled;
    }
    public void unlock(){
        enabled = false;
    }
    public boolean enabled(){
        return enabled;
    }

    public void drawMe(Graphics g){
        g.setColor(curColor);
        Vector2 cardPos = card.getPos();
        Vector2 cardSize = card.getSize();

        //top
        g.fillRect(cardPos.intX()-thickness/2, (int)(cardPos.getY()-yPos+thickness/2), thickness, sideBarLength);
        g.fillRect(cardPos.intX()-thickness/2, (int)(cardPos.getY()-yPos-thickness/2), cardSize.intX()+thickness, thickness);
        g.fillRect(cardPos.intX()+cardSize.intX()-thickness/2, (int)(cardPos.getY()-yPos+thickness/2), thickness, sideBarLength);

        //bottom
        g.fillRect(cardPos.intX()-thickness/2, (int)(cardPos.getY()+cardSize.intY()+yPos-sideBarLength-thickness/2), thickness, sideBarLength);
        g.fillRect(cardPos.intX()-thickness/2, (int)(cardPos.getY()+cardSize.intY()+yPos-thickness/2), cardSize.intX()+thickness, thickness);
        g.fillRect(cardPos.intX()+cardSize.intX()-thickness/2, (int)(cardPos.getY()+cardSize.intY()+yPos-sideBarLength-thickness/2), thickness, sideBarLength);

        g.setColor(Color.black);
    }
}
