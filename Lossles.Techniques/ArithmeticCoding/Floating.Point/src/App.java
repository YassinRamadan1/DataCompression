import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scOption = new Scanner(System.in);
        int option;

        do {
            System.out.println("[1] Compress File\t\t[2] Decompress File\t\t[3] Exit");
            System.out.print("Select Option: ");
            option = scOption.nextInt();

            // Compression Logic
            if (option == 1) {
                String fileData = FileManager.readFromTextFile("TestCompression.txt");
                float compressedCode =  ArithmeticCoder.compress(fileData);

                FileManager.writeToBinaryFile("OutputCompression.bin", compressedCode);
                System.out.println("Compression Done Successfully");
                FileManager.writeToTextFile("OutputCompression.txt", compressedCode);
            }
            // Decompression Logic
            else if (option == 2) {
                ArithmeticCoder.decompress();
            }
            else if (option == 3) {
                break;
            }
            else
                System.out.println("Invalid Option");
        } while (true);
        scOption.close();
    }
}