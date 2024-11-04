import java.util.ArrayList;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class LZ77 {

public static ArrayList<Tag> Compress(String stream, int LWSize, int SWSize) {
        ArrayList<Tag> tags = new ArrayList<>();
        int LWStart = 0;
        int streamLength = stream.length();
        String lookFor;

        while(LWStart < streamLength) {
            // 
            Tag temp = new Tag();

            int LWMaxSize = min(LWSize, streamLength - LWStart);

            for (int currLookForSize = 1; currLookForSize <= LWMaxSize; currLookForSize++) {
                lookFor = stream.substring(LWStart, LWStart + currLookForSize);
                boolean isMatched = false;

                int SWMaxSize = max(0, LWStart - SWSize);

                for (int j = LWStart - 1; j >= SWMaxSize; j--) {
                    String subStrForStream = stream.substring(j, j + currLookForSize);

                    if (lookFor.equals(subStrForStream)) {
                        isMatched = true;
                        temp.length = currLookForSize;
                        temp.position = LWStart - j;
                        if ((LWStart + currLookForSize) < streamLength)
                            temp.nextSymbol = stream.charAt(LWStart + currLookForSize);
                        break;
                    }
                }
                // check if there is no match at the last currLookForSize
                if (!isMatched || currLookForSize == LWMaxSize) {
                    // there is no match of size 1
                    if (temp.position == -1) {
                        temp.position = 0;
                        temp.length = 0;
                        temp.nextSymbol = stream.charAt(LWStart);
                    }
                    tags.add(temp);
                    LWStart = LWStart + temp.length + 1;
                    break;
                }
            }
        }
        return tags;
    }

    public static String deCompress(ArrayList<Tag> tags) {
            String deCompressed = new String();
            int left = 0;
            for (Tag tag : tags) {
                
                for(int i = left - tag.position, counter = 0; counter < tag.length; i++, counter++, left++) {
                    deCompressed += deCompressed.charAt(i);
                }
                if(tag.nextSymbol != '_') {
                    deCompressed += tag.nextSymbol;
                    left++;
                }
            }
            return deCompressed;
    }

}