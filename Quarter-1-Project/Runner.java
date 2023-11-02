import javax.swing.JFrame;
import java.util.Scanner;

public class Runner{
  public static void main(String[] args){
    Scanner scan = new Scanner(System.in);
    System.out.print("Enter 1 for day and 2 for night: ");
    int backgroundChoice = scan.nextInt();
    System.out.print("Enter 1 for winter, 2 for spring, and 3 for fall: ");
    int foregroundChoice = scan.nextInt();

    JFrame fr = new JFrame("Scenery");
    Scenery sc = new Scenery(backgroundChoice, foregroundChoice);
    fr.add(sc);
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fr.pack();
    fr.setVisible(true);
  }
}