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
        ArrayList<CharacterData> charactersData = getCharactersData(stream, charactersFrequency);
        writeCharactersRangesToFile("CharacterRanges.txt", charactersData);

        // Assign lower and upper ranges for all characters in stream, and stop at the end
        for (int i = 1; i < stream.length(); i++) {

            CharacterData characterData = getCharacter(stream.charAt(i), charactersData);
            CharacterData prevCharacterData = getCharacter(stream.charAt(i-1), charactersData);
            characterData.lowerRange = prevCharacterData.lowerRange + ((prevCharacterData.higherRange - prevCharacterData.lowerRange) * characterData.lowerRange);
            characterData.higherRange = prevCharacterData.lowerRange + ((prevCharacterData.higherRange - prevCharacterData.lowerRange) * characterData.higherRange);

            // If it's last character
            if (i == stream.length() - 1) {
                compressedCode = (characterData.lowerRange + characterData.higherRange) / 2.0F;
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
    public static void writeCharactersRangesToFile(String fileName, List<CharacterData> dataList) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (CharacterData data : dataList) {
                writer.write(data.character + " " + data.lowerRange + " " + data.higherRange + "\n");
            }
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------------
    // Decompression
    // -------------
    public static void decompress(){}
}
