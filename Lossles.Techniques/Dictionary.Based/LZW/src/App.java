import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scOption = new Scanner(System.in);
        Scanner scFilename = new Scanner(System.in);
        int option;

        System.out.println(
                "==================================================================================\n" +
                "============================= Welcome to LZW Program =============================\n" +
                "==================================================================================");
        do {
            System.out.println("[1] Compress File\t\t[2] Decompress File");
            System.out.print("Select Option: ");
            option = scOption.nextInt();

            if(option == 1) {
                System.out.printf("Enter File Name: ");
                String fileName = scFilename.nextLine();
                LZW.Compress(fileName);
                System.out.println("Compression Done Successfully");
                break;
            }
            else if(option == 2) {
                System.out.printf("Enter File Name: ");
                String fileName = scFilename.nextLine();
                LZW.Decompress(fileName);
                System.out.println("Decompression Done Successfully");
                break;
            }
            else {
                System.out.println("Invalid Option, Try Again");
            }
        } while(option != 1 && option != 2);

    }
}