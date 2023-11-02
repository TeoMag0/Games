import java.awt.Graphics;

import java.awt.Color;

public class Character extends Interactable {

    private String[] lines;    private int curLine;
    private int stage = 0;
    private int length = 0;
    private int npcSize = 48;

    public Character(int x, int y, int b, String[] lines){
        super(x, y, b);
        this.lines = lines;
    }

    public int interacted(){
        if(curLine < lines.length && length != lines[curLine].length()){
            length = lines[curLine].length();
        }
        else{
            length = 0;
            curLine++;
        }
        if(curLine < lines.length){
            //quest not started yet
            return 1;
        }
        else{
            //quest started
            return 2; 
        }
    }

    public void drawMe(Graphics g, int xD, int yD){
        super.drawMe(g, xD, yD);
        if(curLine < lines.length){
            g.setColor(Color.white);
            int size = g.getFontMetrics().stringWidth(lines[curLine].substring(0, length));
            if(!lines[curLine].equals(""))
                g.fillRect(super.getX()-size/2-10, super.getY()-75, size+20, 20);
            g.setColor(Color.black);
            g.drawString(lines[curLine].substring(0,length), super.getX()-size/2, super.getY()-60);
        }
    }

    public void nextStage(String[] newLines){
        curLine = 0;
        lines = newLines;
        length = 0;
        stage++;
    }
    public void repeatLastLine(){
        if(curLine > lines.length){
            curLine = lines.length-1;
            length = 0;
        }
    }
    public boolean activatePrompt(){
        if(curLine == 0 || curLine >= lines.length){
            return true;
        }
        return false;
    }
    public int getStage(){
        return stage;
    }
    public boolean enabled(){
        return true;
    }
    public String type(){
        return "character";
    }
    public void incLength(){
        if(curLine < lines.length && length < lines[curLine].length()){
            length++;
        }
    }
    public int getYConst(){
        return super.getYConst()+npcSize/2;
    }
    public int getSize(){
        return npcSize;
    }
}
