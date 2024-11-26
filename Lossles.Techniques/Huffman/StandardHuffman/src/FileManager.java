import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileManager {
    public static String readFile(String fileName) {
        StringBuilder fileContent = new StringBuilder();
        try(Scanner scanner = new Scanner(new File(fileName))) {
            while(scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine());
            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

    public static void writeFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        }
        catch (Exception e) {
            System.out.println("An error occurs" + e.getMessage());
        }
    }
}
