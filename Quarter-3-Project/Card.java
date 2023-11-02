import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Card {

   private int value;
   private BufferedImage suitImage, back, current; 
   private int xSize;
   private int xOffset;
   private boolean expanding = false, onBack = true, faceUp = false;

   public Card(int value, String name, String suit){
		this.value = value;
		try{
				suitImage = ImageIO.read(new File(name+suit+".png"));
                back = ImageIO.read(new File("back.png"));
			} catch (IOException e) {
            }
        xSize = 124;
	 }

	 public int getValue(){
      return value;
   }

  public boolean faceUp(){
    return faceUp;
  }

  public void reset(){
    onBack = true;
    faceUp = false;
  }

	 public void drawMe(Graphics g, int x, int y){
        if(onBack)
            current = back;
        else
            current = suitImage;
      g.drawImage(current, x + xOffset, y, xSize, 176, null);
   }  

   public boolean flip(){
    if(expanding){
        xOffset -= 1;
        xSize += 2;
        if(xSize == 124){
            expanding = false;
            faceUp = !faceUp;
            return true;
        }
    }
    else{
        xOffset += 1;
        xSize -= 2;
        if(xSize <= 0){
            expanding = true;
            onBack = !onBack;
        }
    }
    return false;
   }
   
}
