import java.util.*;

public class Huffman {

    private static ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> Read(String stream){
        ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> symbols = new ArrayList();
        Map<Character, Integer> symbolsCount = new TreeMap<>();
        
        for(int i = 0; i < stream.length(); i++){
            if(symbolsCount.containsKey(stream.charAt(i)))
                symbolsCount.put(stream.charAt(i), symbolsCount.get(stream.charAt(i)) + 1);
            else
                symbolsCount.put(stream.charAt(i), 1);
        }

        symbolsCount.forEach((key, value) -> {
            String s = new String();
            s += key;
            symbols.add(new AbstractMap.SimpleEntry(s, new AbstractMap.SimpleEntry(value, new String())));
        });

       return symbols;
    }

    public static String Compress(String Stream){
        
        String compressed = new String();
        Map<Character, String> codes = determineCodes(Stream);
        
        for(int i = 0; i < Stream.length(); i++)
            compressed += codes.get(Stream.charAt(i));
        
        return compressed;
    }

    public static Map<Character, String> determineCodes(String Stream){
        
        ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> symbols = Read(Stream);
        Map<Character, String> codes = new TreeMap<>();
        
        if(symbols.size() == 1)
            codes.put(symbols.get(0).getKey().charAt(0), "0");
        else if(symbols.size() > 1){
            symbols = determineCodesAux(symbols);
            for(int i = 0;i < symbols.size();i++)
                codes.put(symbols.get(i).getKey().charAt(0), symbols.get(i).getValue().getValue());
        }
        return codes;
    }

    private static ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> determineCodesAux(ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> inputSymbols){
        
        inputSymbols.sort(Comparator.comparingInt(entry -> entry.getValue().getKey()));
        
        if(inputSymbols.size() == 2){
            inputSymbols.get(0).getValue().setValue("1");
            inputSymbols.get(1).getValue().setValue("0");
            return inputSymbols;
        }

        ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> inputSymbolsAux = new ArrayList<>();
        ArrayList<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>>> result = new ArrayList<>();
        AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<Integer, String>> minimumtwoSymbols 
        = new AbstractMap.SimpleEntry<>(inputSymbols.get(0).getKey() + inputSymbols.get(1).getKey(),
         new AbstractMap.SimpleEntry<>(inputSymbols.get(0).getValue().getKey() + inputSymbols.get(1).getValue().getKey(),
          inputSymbols.get(0).getValue().getValue() + inputSymbols.get(1).getValue().getValue()));
        inputSymbolsAux.add(minimumtwoSymbols);
        
        for(int i = 2; i < inputSymbols.size(); i++)
            inputSymbolsAux.add(inputSymbols.get(i));

        inputSymbolsAux = determineCodesAux(inputSymbolsAux);
        
        for(int i = 0; i < inputSymbolsAux.size(); i++) {

            if(inputSymbolsAux.get(i).getKey().equals(minimumtwoSymbols.getKey())){
                result.add(new AbstractMap.SimpleEntry<>(inputSymbols.get(0).getKey(), new AbstractMap.SimpleEntry<>(inputSymbols.get(0).getValue().getKey(), inputSymbolsAux.get(i).getValue().getValue() + "1")));
                result.add(new AbstractMap.SimpleEntry<>(inputSymbols.get(1).getKey(), new AbstractMap.SimpleEntry<>(inputSymbols.get(1).getValue().getKey(), inputSymbolsAux.get(i).getValue().getValue() + "0")));
            }
            else
                result.add(inputSymbolsAux.get(i));
        }
        return result;
    }
}
