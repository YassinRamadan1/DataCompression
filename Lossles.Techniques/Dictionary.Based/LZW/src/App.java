import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        

        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        scanner.close();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        String stream = new String();
        for (String line : lines)
            stream = stream + line;

        ArrayList<Integer> tags = LZW.Compress(stream);

        for(int i = 0; i < tags.size(); i++)
            System.out.print("< " + tags.get(i) + " >   ");
        
    }
}
