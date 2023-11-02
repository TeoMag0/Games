public class QuestTrack{

    private String line;

    public QuestTrack(String line){
        this.line = line;
    }

    public void changeLine(String str){
        line = str;
    }
    public String toString(){
        return line;
    }
}