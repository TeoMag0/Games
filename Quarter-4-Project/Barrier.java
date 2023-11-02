public class Barrier {
    private int xm, ym, xma, yma;
    private boolean inside;
    public Barrier(int xMin, int yMin, int xMax, int yMax, boolean inide){
        xm = xMin;
        xma = xMax;
        ym = yMin;
        yma = yMax;
        this.inside = inide;
    }

    public int xMin(){
        return xm;
    }
    public int xMax(){
        return xma;
    }
    public int yMin(){
        return ym;
    }
    public int yMax(){
        return yma;
    }
    public boolean inside(){
        return inside;
    }

}
