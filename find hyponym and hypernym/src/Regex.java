import java.util.List;
import java.util.regex.Pattern;

/**
 * the regex interface.
 */
public interface Regex {
    /**
     * get the hypno from the sentence.
     * @param sentence the sentece we get the hypno.
     * @return a list with all the hypno
     */
    List<String> getHyponym(String sentence);
    /**
     * get the hyper from the sentence.
     * @param sentence the sentece we get the hyper.
     * @return a list with all the hyper
     */
    List<String> getHypernym(String sentence);

    /**
     * ge the pattern.
     * @return the pattren.
     */
    Pattern getPt();

}
