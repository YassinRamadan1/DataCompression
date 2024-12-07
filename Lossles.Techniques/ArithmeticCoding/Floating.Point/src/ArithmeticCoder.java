import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ArithmeticCoder {
    // Compression
    // -----------
    public static float compress(String stream) {
        float compressedCode = 0;
        TreeMap<Character, Integer> charactersFrequency = new TreeMap<>();

        // Calculate frequency for all characters
        calculateFrequency(charactersFrequency, stream);

        // Calculate probabilities for all characters
        ArrayList<CharacterData> charactersDataOriginal = getCharactersData(stream, charactersFrequency);
        ArrayList<CharacterData> charactersDataModified = getCharactersData(stream, charactersFrequency);

        writeCharactersRangesToFile("CharacterRanges.txt", charactersDataModified);

        // Assign lower and upper ranges for all characters in stream, and stop at the end
        for (int i = 1; i < stream.length(); i++) {

            CharacterData characterDataModified = getCharacter(stream.charAt(i), charactersDataModified);
            CharacterData characterDataOriginal = getCharacter(stream.charAt(i), charactersDataOriginal);

            CharacterData prevCharacterData = getCharacter(stream.charAt(i-1), charactersDataModified);
            characterDataModified.lowerRange = prevCharacterData.lowerRange + ((prevCharacterData.higherRange - prevCharacterData.lowerRange) * characterDataOriginal.lowerRange);
            characterDataModified.higherRange = prevCharacterData.lowerRange + ((prevCharacterData.higherRange - prevCharacterData.lowerRange) * characterDataOriginal.higherRange);

            // If it's last character
            if (i == stream.length() - 1) {
                compressedCode = (characterDataModified.lowerRange + characterDataModified.higherRange) / 2.0F;
                compressedCode = Math.round(compressedCode * 10000) / 10000f;
            }
        }
        return compressedCode;
    }
    private static void calculateFrequency(TreeMap<Character, Integer> charactersFrequency, String stream) {
        for (int i = 0; i < stream.length(); i++) {
            char c = stream.charAt(i);
            if (charactersFrequency.containsKey(c)) {
                charactersFrequency.put(c, charactersFrequency.get(c) + 1);
            } else {
                charactersFrequency.put(c, 1);
            }
        }
    }
    private static ArrayList<CharacterData> getCharactersData(String stream, TreeMap<Character, Integer> charactersFrequency) {
        ArrayList<CharacterData> charactersData = new ArrayList<>();
        float current = 0.0F;
        float lowerRange;
        float higherRange;

        for (Map.Entry<Character, Integer> entry : charactersFrequency.entrySet()) {
            lowerRange = current;
            higherRange = lowerRange + ((float) entry.getValue() / stream.length());

            CharacterData characterData = new CharacterData(entry.getKey(),lowerRange,higherRange);
            charactersData.add(characterData);

            current = characterData.higherRange;

        }
        return charactersData;
    }
    private static CharacterData getCharacter(char character, ArrayList<CharacterData> charactersData) {
        CharacterData characterData = null;
        for (CharacterData characterData1 : charactersData) {
            if (characterData1.character == character) {
                characterData = characterData1;
                break;
            }
        }
        return characterData;
    }
    public static void writeCharactersRangesToFile(String fileName, List<CharacterData> dataList, int streamLength) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (CharacterData data : dataList) {
                writer.write(data.character + " " + data.lowerRange + " " + data.higherRange + "\n");
            }
            writer.write(" " + streamLength);
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------------
    // Decompression
    // -------------

    private static int length;
    private static ArrayList<CharacterData> getRanges(){

        String data = FileManager.readFromTextFile("CharacterRanges.txt");
        String[] parts = data.split(" ");
        length = Integer.parseInt(parts[parts.length - 1]);
        data = new String();
        ArrayList<CharacterData> ranges = new ArrayList<>();
        for(int i = 0; i < parts.length - 1; i++){
            if(parts[i].length() != 1 && !Character.isDigit(parts[i].charAt(parts[i].length() - 1))){
                data += " " + parts[i].substring(0, parts[i].length() - 1) + " " + parts[i].substring(parts[i].length() - 1);
            }
            else{
                data += " " + parts[i];
            }
        }
        String[] sections = data.split(" ");
        for(int i = 1; i < sections.length; i += 3){
            ranges.add(new CharacterData(sections[i].charAt(0), Float.parseFloat(sections[i + 1]), Float.parseFloat(sections[i + 2])));
        }
        return ranges;
    }

    public static void decompress(){

        
        float val = 0.0f;
        try {
            val = FileManager.readFromBinaryFile("OutputCompression.bin");
        } catch(IOException e){
             System.out.println("Error!" + e.getMessage());
        }

        float lwRange = 0.0f, hiRange = 1.0f;
        String Stream = new String();
        
        ArrayList<CharacterData> ranges = getRanges();
        for(int i = 0; i < length; i++) {

            int l = 0, r = ranges.size() - 1, mid;
            while(l <= r) {
            
                mid = (l + r) / 2;
                if(ranges.get(mid).lowerRange >= val)
                    r = mid - 1;
                else if(ranges.get(mid).higherRange < val)
                    l = mid + 1;
                else{
                    Stream += ranges.get(mid).character;
                    float temp1 = hiRange - lwRange;
                    float temp2 = lwRange;
                    lwRange = temp2 + ranges.get(mid).lowerRange * temp1;
                    hiRange = temp2 + ranges.get(mid).higherRange  * temp1;
                    val = temp2 + val * temp1;
                    val = (val - lwRange) / (hiRange - lwRange);
                    break;
                }
            }
        }
        FileManager.writeToTextFile("OutputDecompression.txt", Stream);
        System.out.println("Decompression finished successfully and the stream was Written to OutputDecompression text file!");
    }
}
