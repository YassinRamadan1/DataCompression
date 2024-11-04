import java.util.ArrayList;

public class App {

    

    public static void main(String[] args) {
        
        String test = "AAAAA";
        //CABRACADABRARRARRAD
        //AAAA
        
        ArrayList<Tag> tags = LZ77.Compress(test, 2, 2);
        
        for(Tag tag : tags) {
            System.out.println("<"+ tag.position + "," + tag.length + "," + tag.nextSymbol + ">");
        }
        System.out.println(LZ77.deCompress(tags));
        
    }
}