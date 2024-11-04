import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class LZW {


    public static ArrayList<Integer> Compress(String stream) {
        
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
        return tags;
    }
}
