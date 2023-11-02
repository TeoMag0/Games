import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class PlayerAnimations{

    private BufferedImage forward;
    private BufferedImage left;
    private BufferedImage right;
    private BufferedImage current;

    public PlayerAnimations(){
        try{
            forward = ImageIO.read(new File("face2.png"));
            left = ImageIO.read(new File("face1.png"));
            right = ImageIO.read(new File("face3.png"));
        } catch(IOException e){}
    }

    public BufferedImage getAnimation(boolean aP, boolean wP, boolean sP, boolean dP){
        if(aP){
            current = left;
            return left;
        }
        else if(dP){
            current = right;
            return right;
        }
        else if(wP){
            current = null;
            return null;
        }
        else if(sP){
            current = forward;
            return forward;
        }
        return current;
    }
}