import java.io.*;
import java.util.Scanner;

public class FileManager {
    public static String readFromTextFile(String fileName) {
        StringBuilder fileContent = new StringBuilder();
        try(Scanner scanner = new Scanner(new File(fileName))) {
            while(scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine());
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return fileContent.toString();
    }
    public static void writeToBinaryFile(String fileName, float value) {
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            int intBits = Float.floatToIntBits(value);
            byte[] bytes = new byte[] {
                    (byte) (intBits >> 24),
                    (byte) (intBits >> 16),
                    (byte) (intBits >> 8),
                    (byte) (intBits)
            };
            fos.write(bytes);
            System.out.println("Float value written to " + fileName + " in binary format.");
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }
    public static void writeToTextFile(String fileName, float value) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(String.valueOf((value)));
            writer.close();
        }
        catch (Exception e) {
            System.out.println("An error occurs" + e.getMessage());
        }
    }
    public static void writeToTextFile(String fileName, String content) {
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
