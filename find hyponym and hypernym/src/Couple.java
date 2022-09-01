/**
 * Class Couple, hold a integer and string.
 */
public class Couple {
    private Integer number;
    private String hypo;

    /**
     * constructor for couple.
     * @param number
     * @param hypo
     */
    public Couple(Integer number, String hypo) {
        this.hypo = hypo;
        this.number = number;
    }

    /**
     * get the number.
     * @return the number of the couple.
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * ge the hypo.
     * @return the word.
     */
    public String getHypo() {
        return hypo;
    }


}
