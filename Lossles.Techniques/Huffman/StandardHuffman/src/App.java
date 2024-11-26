import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scOption = new Scanner(System.in);
        Scanner scFilename = new Scanner(System.in);
        int option;

        do {
            System.out.println("[1] Compress File\t\t[2] Decompress File");
            System.out.print("Select Option: ");
            option = scOption.nextInt();

            if(option == 1) {
                String fileData = FileManager.readFile("TestCompression.txt");

                String compressedData = Huffman.Compress(fileData);

                FileManager.writeFile("OutputCompression.txt", compressedData);
                System.out.println("Compression Done Successfully");
                break;
            }
            else if(option == 2) {
                String fileData = FileManager.readFile("TestDecompression.txt");

                String decompressedData = Huffman.Decompress(fileData, Huffman.determineCodes(FileManager.readFile("TestCompression.txt")));

                FileManager.writeFile("OutputDecompression.txt", decompressedData);
                System.out.println("Decompression Done Successfully");
                break;
            }
            else {
                System.out.println("Invalid Option, Try Again");
            }
        } while(option != 1 && option != 2);

    }
}
