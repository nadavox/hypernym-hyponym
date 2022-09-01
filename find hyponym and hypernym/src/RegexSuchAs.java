import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * the regex such as.
 */
public class RegexSuchAs implements Regex {
    private String regex;
    private Pattern pt;

    /**
     * the builder for the class.
     */
    public RegexSuchAs() {
        this.regex = "<np>([^<>/])+</np>( ,)? such as <np>([^<>/])+</np>(( ,)?"
                + " <np>([^<>/])+</np>)*(( ,)? (or|and) <np>([^<>/])+</np>)?";
        pt = Pattern.compile(regex);
    }

    /**
     * the cuilder for the class.
     * @return the pateern.
     */
    public Pattern getPt() {
        return pt;
    }

    @Override
    public List<String> getHyponym(String sentence) {
        List<String> hyponymList = new ArrayList<>();
        int startIndex = 0, index = 4;
        while (sentence.charAt(index) != '>') {
            index++;
        }
        //skip the letter s in such
        index = index + 6;
        while (sentence.charAt(index) != 's') {
            index++;
        }
        //skip the first <np>
        index = index + 6;
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
        String hyperny = sentence.substring(4, length);
        hypernymList.add(hyperny.toLowerCase());
        return hypernymList;
    }
}
