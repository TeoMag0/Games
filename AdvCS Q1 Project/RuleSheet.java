import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Font;

public class RuleSheet {
    private JTextArea textArea;
    private float pos;
    private int openLoc;
    private int closedLoc;
    private float moveTo;
    private float moveSpeed;
    private int margin;
    private int xPos;
    private int buttonHeight;
    private Color backgroundColor;
    private Font textFont;

    public RuleSheet(Screen sc){

        openLoc = 325;
        moveSpeed = 2;
        xPos = 525;
        margin = 50;
        buttonHeight = 50;
        pos = 600-buttonHeight;
        closedLoc = 600-buttonHeight;
        moveTo = closedLoc;
        backgroundColor = new Color(215, 160, 50);
        textFont = new Font("Arial", Font.PLAIN, 20);


        textArea = new JTextArea();
        textArea.setBounds(xPos, closedLoc + buttonHeight, 800-xPos, 600-openLoc);
        sc.add(textArea);
        textArea.setFocusable(false);
        textArea.setBackground(new Color(backgroundColor.getRed()+35, backgroundColor.getGreen()+40, backgroundColor.getBlue()+40));
        textArea.setMargin(new Insets(5, 10, 0, 10));
        textArea.setFont(textFont);
        textArea.setText(
            "Royal Flush - 250 pts\n"+
            "Straight Flush - 50 pts\n"+
            "Four of a Kind - 25 pts\n"+
            "Full House - 9 pts\n"+
            "Flush - 6 pts\n"+
            "Straight - 4 pts\n"+
            "Three of a Kind - 3 pts\n"+
            "Two Pairs - 2 pts\n"+
            "Pair of Jacks or Higher - 1 pt"
        );

    }

    public void drawMe(Graphics g){
        g.setColor(backgroundColor);
        int buttonWidth = 800-xPos-2*margin;
        g.fillRoundRect(xPos+margin, (int)pos, buttonWidth, buttonHeight, 5, 5);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Rules", xPos+buttonWidth/2+margin-g.getFontMetrics().stringWidth("Rules")/2, (int)pos+buttonHeight/2+6);
    }

    float time = 0;
    public void update(float deltaTime){
        if(pos != moveTo){
            int dir = moveTo-pos < 0 ? -1 : 1;
            pos += dir * sineMove() * deltaTime;
            if(sineMove() < .1 && time > deltaTime) pos = moveTo;
            textArea.setLocation(textArea.getX(), (int)pos+buttonHeight);
            time += deltaTime;
        }
        else time = 0;
    }
    private float sineMove(){
        float amp = closedLoc - openLoc;
        return (float)(amp*moveSpeed * (-Math.cos(2*Math.PI*moveSpeed * time) + 1));
    }

    public void click(){
        moveTo = moveTo == openLoc ? closedLoc : openLoc;
    }
    public void close(){
        moveTo = closedLoc;
    }

    public boolean isClicked(Vector2 mouse){
        if(xPos+margin <= mouse.getX() && 800-margin >= mouse.getX() &&
           pos <= mouse.getY() && pos + buttonHeight >= mouse.getY())
           return true;
        return false;
    }
}
