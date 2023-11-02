import java.awt.Graphics;

public abstract class Basic {
    private int y;
    public Basic(int y){
        this.y = y;
    }
    public abstract void drawMe(Graphics g, int xD, int yD);
    public abstract boolean enabled();
    public int getYConst(){
        return y;
    }
}
