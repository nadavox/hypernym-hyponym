import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * regex including.
 */
public class RegexIncluding implements Regex {
    private String regex;
    private Pattern pt;

    /**
     * the builder for the class.
     */
    public RegexIncluding() {
        regex = "<np>([^<>/])+</np>( ,)? including <np>([^<>/])+</np>(( ,)?"
                + " <np>([^<>/])+</np>)*(( ,)? (or|and) <np>([^<>/])+</np>)?";
        pt = Pattern.compile(regex);
    }

    @Override
    public List<String> getHyponym(String sentence) {
        List<String> hyponymList = new ArrayList<>();
        int startIndex, index = 4;
        while (sentence.charAt(index) != '>') {
            index++;
        }
        //skip the word "including" in such
        index = index + 5;
        while (sentence.charAt(index) != '>') {
            index++;
        }
        //skip to the first letter of the first word.
        index = index + 1;
        //while you didnt get the end of the sentence.
        while (index < sentence.length() - 5) {
            startIndex = index;
            while (sentence.charAt(index) != '/') {
                index++;
            }
            hyponymList.add(sentence.substring(startIndex, index - 1).toLowerCase());
            index = index + 4;
            if (index < sentence.length() - 5) {
                //while loop for the start of the next word
                while (sentence.charAt(index) != '<') {
                    index++;
                }
                index = index + 4;
            } else {
                break;
            }
        }
        return hyponymList;
    }

    @Override
    public List<String> getHypernym(String sentence) {
        List<String> hypernymList = new ArrayList<>();
        int length = 0;
        for (int j = 0; j < sentence.length(); j++) {
            char letter = sentence.charAt(j);
            if (letter == '/') {
                length = j - 1;
                j = sentence.length();
            }
        }
        String hyperny = sentence.substring(4, length).toLowerCase();
        hypernymList.add(hyperny);
        return hypernymList;
    }

    @Override
    public Pattern getPt() {
       return pt;
    }
}
