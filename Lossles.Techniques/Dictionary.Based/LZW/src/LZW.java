import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class LZW {
    public static void Compress(String fileName) {
        StringBuilder stream = new StringBuilder();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            if (scanner.hasNextLine()) {
                for (String token : scanner.nextLine().split("")) {
                    stream.append(token);
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.printf("File not found: " + e.getMessage());
        }

        ArrayList<Integer> tags = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        String lookFor;
        int nextIndex = 128;
        int tag = -1;
        int matchSize = 1;
        for(int left = 0; left < stream.length(); left += matchSize){
            tag = -1;
            matchSize = 1;
            for(int lookForSize = 1; lookForSize < stream.length() - left + 1; lookForSize++) {
                if(lookForSize == 1){
                    tag = (int) stream.charAt(left);
                    continue;
                }
                lookFor = stream.substring(left, left + lookForSize);
                if(map.containsKey(lookFor)) {
                    tag = map.get(lookFor);
                    matchSize = lookForSize;
                }
                else {
                    map.put(lookFor, nextIndex++);
                    break;
                }
            }
            tags.add(tag);
        }

        try {
            FileWriter writer = new FileWriter("OutputCompression.txt", true);
            for(int i = 0; i < tags.size(); i++)
                writer.write(tags.get(i) + " ");
            writer.close();
        }
        catch (Exception e) {
            System.out.println("An error occurs" + e.getMessage());
        }
    }

    public static void Decompress(String fileName) {
        // ArrayList to store generated code from compression
        ArrayList<Integer> codes = new ArrayList<>();

        // Initialize dictionary
        HashMap<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i <= 127; i++) {
            dictionary.put(i, Character.toString((char) i));
        }

        // Compressed stream that will be added to file
        StringBuilder stream = new StringBuilder();

        // Read codes from file add codes in my ArrayList codes
        try {
            // Scanner to read line in text file that contains codes
            Scanner scanner = new Scanner(new File(fileName));
            if (scanner.hasNextLine()) {
                // Iterate through each code in the returned strings from split function add to codes
                for (String token : scanner.nextLine().split(" ")) {
                    codes.add(Integer.parseInt(token));
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.printf("File not found: " + e.getMessage());
        }

        // Decompression logic
        for (int i = 0, j = 128; i < codes.size(); i++) {
            // first symbol case
            if (i == 0) {
                stream.append(dictionary.get(codes.get(i)));
            } else {
                // If the key is exist in the dictionary
                if (dictionary.containsKey(codes.get(i))) {
                    stream.append(dictionary.get(codes.get(i)));
                    dictionary.put(j, dictionary.get(codes.get(i - 1)) + dictionary.get(codes.get(i)).charAt(0));
                    j++;
                } else {
                    String missingString = dictionary.get(codes.get(i - 1)) + dictionary.get(codes.get(i - 1)).charAt(0);
                    stream.append(missingString);
                    dictionary.put(codes.get(i), missingString);
                    j++;
                }
            }
        }

        // Add compressed stream to file
        try (FileWriter writer = new FileWriter("OutputDecompression.txt", true)) {
            writer.write(stream.toString());
        } catch (IOException e) {
            System.out.println("An error occurred." + e.getMessage());
        }
    }
}
