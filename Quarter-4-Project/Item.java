import java.awt.Graphics;

public class Item extends Interactable{
    private boolean enabled = true;
    private int ySize;

    public Item(int x ,int y, int b, int ySize){
        super(x, y, b);
        this.ySize = ySize;
    }

    public int interacted(){
        enabled = false;
        return 0;
    }

    public void drawMe(Graphics g, int xD, int yD){
        super.drawMe(g, xD, yD);
    }
    public void drawMe(Graphics g, int newX, int newY, int xSize, int ySize){
    }
    public boolean enabled(){
        return enabled;
    }
    public String type(){
        return "item";
    }
    public int getYConst(){
        return super.getYConst()+ySize/2;
    }
    public String getSuit(){
        return "";
    }
}