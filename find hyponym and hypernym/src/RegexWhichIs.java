import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * the regex which is.
 */
public class RegexWhichIs implements Regex {
    private String regex;
    private Pattern pt;

    /**
     * the builder for the class.
     */
    public RegexWhichIs() {
        regex = "<np>([^<>/])+</np>( ,)? which is ((an example|a kind|a class) of )?<np>([^<>/])+</np>";
        pt = Pattern.compile(regex);
    }

    @Override
    public List<String> getHyponym(String sentence) {
        List<String> hyponymList = new ArrayList<>();
        int length = 0;
        for (int j = 0; j < sentence.length(); j++) {
            char letter = sentence.charAt(j);
            if (letter == '/') {
                length = j - 1;
                j = sentence.length();
            }
        }
        String hyponym = sentence.substring(4, length).toLowerCase();
        hyponymList.add(hyponym);
        return hyponymList;
    }

    @Override
    public List<String> getHypernym(String sentence) {
        List<String> hyponymList = new ArrayList<>();
        int i = sentence.length() - 2;
        while (sentence.charAt(i) != '>') {
            i--;
        }
        //to print it without the symbole: >
        i++;
        String hypernym = sentence.substring(i, sentence.length() - 5);
        hyponymList.add(hypernym.toLowerCase());
        return hyponymList;
    }

    @Override
    public Pattern getPt() {
        return pt;
    }
}