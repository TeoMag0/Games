import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {

    public static void playSound(Sound soumd){
        AudioInputStream audioInput = null;
        switch(soumd){
            case DRAWSOUND: 
                try{
                    audioInput = AudioSystem.getAudioInputStream(new File("drawCard.wav"));
                } catch(Exception e){}
                break;
            case GATHERSOUND:
                try{
                    audioInput = AudioSystem.getAudioInputStream(new File("gatherCards.wav"));
                } catch(Exception e){}
                break;
            case SHUFFLESOUND:
                try{
                    audioInput = AudioSystem.getAudioInputStream(new File("shuffleAnBridge.wav"));
                } catch(Exception e){}
                break;
        }
        try{
            Clip s = AudioSystem.getClip();
            s.open(audioInput);
            s.start(); 
        }catch(Exception e){}
    }
}
enum Sound{
    DRAWSOUND,
    GATHERSOUND,
    SHUFFLESOUND
}
