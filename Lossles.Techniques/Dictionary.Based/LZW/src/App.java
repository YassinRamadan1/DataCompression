import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        // path : C:\\dev\\datacompression\\Lossles.Techniques\\Dictionary.Based\\LZW\\src\\Stream.txt
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        scanner.close();
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        String stream = new String();
        for (String line : lines)
            stream = stream + line;

        ArrayList<Integer> tags = LZW.Compress(stream);

        FileWriter writer = new FileWriter("output.txt");
        for(int i = 0; i < tags.size(); i++)
            writer.write("< " + tags.get(i) + " >   ");
        writer.close();
    }
}
