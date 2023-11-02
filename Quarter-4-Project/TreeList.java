import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;

public class TreeList {
  private ArrayList<Tree> allTrees = new ArrayList<Tree>();

  public TreeList(int xSize, int ySize) {
    try {

      FileReader reader = new FileReader("Trees.txt");
      BufferedReader lineReader = new BufferedReader(reader);

      // find all the trees, store them
      String line;
      while ((line = lineReader.readLine()) != null) {
        int x = Integer.parseInt(line.substring(0, line.indexOf(",")));
        int y = Integer.parseInt(line.substring(line.indexOf(",") + 1));
        allTrees.add(new Tree(x,y, xSize, ySize));
      }
      lineReader.close();
    } catch (IOException e) {
    }
  }

  public void addTree(int x, int y) {
    try {
      FileWriter writer = new FileWriter("Trees.txt", true);
      PrintWriter printer = new PrintWriter(writer);

      printer.printf("%s" + "%n", x + "," + y);
      printer.close();
    } catch (IOException e) {
    }
  }



  public ArrayList<Tree> getTrees(){
    return allTrees;
  }

}