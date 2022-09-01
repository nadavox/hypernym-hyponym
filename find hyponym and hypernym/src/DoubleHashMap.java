import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Matcher;
import java.io.IOException;
import java.io.FileReader;

/**
 * the data srutcter class.
 */
public class DoubleHashMap {
    private HashMap<String, HashMap<String, Integer>> hypernymMap;
    private List<Regex> regexesList;
    private HashMap<String, Integer> discoverMap;

    /**
     * constructor for the data structuere.
     */
    public DoubleHashMap() {
        RegexSuchAs suchAs = new RegexSuchAs();
        RegexSuchNPAs suchNPAs = new RegexSuchNPAs();
        RegexIncluding including = new RegexIncluding();
        RegexEspecially especially = new RegexEspecially();
        RegexWhichIs whichIs = new RegexWhichIs();
        hypernymMap = new HashMap<>();
        regexesList = new ArrayList<>();
        this.regexesList .add(suchAs);
        this.regexesList .add(suchNPAs);
        this.regexesList .add(including);
        this.regexesList .add(especially);
        this.regexesList .add(whichIs);
    }

    /**
     * get the hyper map.
     * @return the hyper map.
     */
    public HashMap<String, HashMap<String, Integer>> getHypernymMap() {
        return hypernymMap;
    }

    /**
     * put values in the map.
     * @param hypernym the key.
     * @param hyponymsList the value.
     */
    public void putValuesInMap(String hypernym, List<String> hyponymsList) {
        HashMap<String, Integer> hyponymMap = hypernymMap.get(hypernym);
        for (String s : hyponymsList) {
            if (hyponymMap.containsKey(s)) {
                int value = hyponymMap.get(s) + 1;
                hyponymMap.put(s, value);
            } else {
                hyponymMap.put(s, 1);
            }
        }
        hypernymMap.put(hypernym, hyponymMap);
    }

    /**
     * delete keys with values under 3.
     */
    public void deleteHypernymsLessThanThree() {
        List<String> hypernyList = new ArrayList<>();
        for (String hyponym : hypernymMap.keySet()) {
            hypernyList.add(hyponym);
        }
        for (String key : hypernyList) {
            HashMap<String, Integer> hyponymMap = hypernymMap.get(key);
            if (hyponymMap.size() < 3) {
                this.hypernymMap.remove(key, hyponymMap);
            }
        }
    }

    /**
     * swap for the quicksort.
     * @param arrCouple the array.
     * @param i the position.
     * @param j the position.
     */
    public void swap(Couple[] arrCouple, int i, int j) {
        Couple temp = arrCouple[i];
        arrCouple[i] = arrCouple[j];
        arrCouple[j] = temp;
    }

    /**
     * the partition part.
     * @param arrCouple the couple.
     * @param low the index from left.
     * @param high index from right.
     * @return the new index.
     */
    public int partition(Couple[] arrCouple, int low, int high) {
        // pivot
        Couple pivot = arrCouple[high];

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is biger
            // than the pivot
            if (arrCouple[j].getNumber() < pivot.getNumber()) {
                // Increment index of
                // smaller element
                i++;
                swap(arrCouple, i, j);
            }
        }
        swap(arrCouple, i + 1, high);
        return (i + 1);
    }

    /**
     * the qucik sort algorithm.
     * @param arrCouple the array.
     * @param low the start index.
     * @param high the lsat index.
     */
    public void quickSort(Couple[] arrCouple, int low, int high) {
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arrCouple, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arrCouple, low, pi - 1);
            quickSort(arrCouple, pi + 1, high);
        }
    }

    /**
     * sorting by the string.
     * @param arrCouple the array.
     */
    public void sortByString(Couple[] arrCouple) {
        int number = arrCouple[0].getNumber();
        List<String> coupleList = new ArrayList<>();
        for (Couple couple : arrCouple) {
            coupleList.add(couple.getHypo());
        }
        Collections.sort(coupleList);
        for (int i = 0; i < arrCouple.length; i++) {
            arrCouple[i] = new Couple(number, coupleList.get(i));
        }
    }

    /**
     * sort the array of the couples.
     * @param arrCouple the array we want to sort.
     */
    public void sortList(Couple[] arrCouple) {
        quickSort(arrCouple, 0, arrCouple.length - 1);
        List<Couple> coupleList = new ArrayList<>();
        Collections.addAll(coupleList, arrCouple);
        Collections.reverse(coupleList);
        for (int i = 0; i < arrCouple.length; i++) {
            arrCouple[i] = coupleList.get(i);
        }
        int startIndex = 0, endIndex = 0, saveTheStartIndex = 0;
        int length = arrCouple.length;
        for (int i = 0; i < length; i++) {
            if (i == length - 1) {
                break;
            }
            if (arrCouple[i].getNumber() == arrCouple[i + 1].getNumber()) {
                startIndex = i;
                for (int j = i + 1; j < length; j++) {
                    if (arrCouple[i].getNumber() != arrCouple[j].getNumber()) {
                        endIndex = j - 1;
                        break;
                    }
                    endIndex++;
                }
                if (endIndex > startIndex) {
                    Couple[] subArr = new Couple[endIndex - startIndex + 1];
                    saveTheStartIndex = startIndex;
                    for (int j = 0; j < subArr.length; j++) {
                        subArr[j] = arrCouple[startIndex];
                        startIndex++;
                    }
                    sortByString(subArr);
                    for (int j = 0; j < subArr.length; j++) {
                        arrCouple[saveTheStartIndex] = subArr[j];
                        saveTheStartIndex++;
                    }
                    i = endIndex;
                }
            }
        }
    }

    /**
     * print the map.
     * @param printWriter the parameter we write to tge file.
     */
    public void toString(PrintWriter printWriter) {
        List<String> keys = new ArrayList<>();
        for (String key : hypernymMap.keySet()) {
            keys.add(key);
        }
        Collections.sort(keys);
        for (String key : keys) {
            HashMap<String, Integer> hyopMap = hypernymMap.get(key);
            List<Couple> coupleList = new ArrayList<>();
            for (String hyponym : hyopMap.keySet()) {
                coupleList.add(new Couple(hyopMap.get(hyponym), hyponym));
            }
            Couple[] arrCouple = new Couple[coupleList.size()];
            coupleList.toArray(arrCouple);
            sortList(arrCouple);
            printWriter.print(key + ": ");
            //System.out.print(key + ": " );
            int length = hypernymMap.get(key).keySet().size();
            int count = 1;
            for (Couple hyponym : arrCouple) {
                if (count == length) {
                    printWriter.print(hyponym.getHypo() + " (" +  hyponym.getNumber() + ")");
                    //System.out.print(hyponym.getHypo() + " (" + hyponym.getNumber() + ")");
                } else {
                    printWriter.print(hyponym.getHypo() + " (" +  hyponym.getNumber() + "), ");
                    //System.out.print(hyponym.getHypo() + " (" +  hyponym.getNumber() + "), ");
                }
                count++;
            }
            printWriter.print("\n");
            //System.out.print("\n");
        }
    }

    /**
     * check if the lemma exist in the list.
     * @param hyponymsList the list we check
     * @param lemma the lemma
     * @param hypernymList the list we want to show i the end the lemma show.
     */
    public void checkToLemma(List<String> hyponymsList, String lemma, List<String> hypernymList) {
        //check if the lemma shows in hyponym1
        if (lemma == null) {
            return;
        }
        for (String hyponym : hyponymsList) {
            if (hyponym.equals(lemma)) {
                if (!discoverMap.containsKey(hypernymList.get(0))) {
                    discoverMap.put(hypernymList.get(0), 1);
                } else {
                    discoverMap.replace(hypernymList.get(0), discoverMap.get(hypernymList.get(0)) + 1);
                }
            }
        }
    }

    /**
     * print the map of discovery.
     */
    public void printDiscoverHypernym() {
        Couple[] arrCouple = new Couple[discoverMap.size()];
        int i = 0;
        for (String word : discoverMap.keySet()) {
            arrCouple[i] = new Couple(discoverMap.get(word), word);
            i++;
        }
        sortList(arrCouple);
        for (Couple word : arrCouple) {
            System.out.println(word.getHypo() + ": (" + word.getNumber() + ")");
        }
    }

    /**
     * find the mauch with such as.
     * @param m the mucher.
     * @param lemma the lemma.
     */
    public void findSuch(Matcher m, String lemma) {
        //the sentence I got from the text.
        String sentence = m.group();
        //get the hypernym from the sentence
        List<String> hypernymList = regexesList.get(0).getHypernym(sentence);
        // if I have this hypernym so dont add him.
        if (!getHypernymMap().containsKey(hypernymList.get(0))) {
            getHypernymMap().put(hypernymList.get(0), new HashMap<>());
        }
        //list of hyponym
        List<String> hyponymsList = regexesList.get(0).getHyponym(sentence);
        //check if the lemma shows in hyponym1
        checkToLemma(hyponymsList, lemma, hypernymList);
        //put the words in the map.
        putValuesInMap(hypernymList.get(0), hyponymsList);
    }
    /**
     * find the mauch with suchNPas.
     * @param m the mucher.
     * @param lemma the lemma.
     */
    public void findsuchasNp(Matcher m, String lemma) {
        //the sentence I got from the text.
        String sentence = m.group();
        //get the hypernym from the sentence
        List<String> hypernymList = regexesList.get(1).getHypernym(sentence);
        // if I have this hypernym so dont add him.
        if (!getHypernymMap().containsKey(hypernymList.get(0))) {
            getHypernymMap().put(hypernymList.get(0), new HashMap<>());
        }
        //list of hyponym
        List<String> hyponymsList = regexesList.get(1).getHyponym(sentence);
        //check if the lemma shows in hyponym1
        checkToLemma(hyponymsList, lemma, hypernymList);
        //put the words in the map.
        putValuesInMap(hypernymList.get(0), hyponymsList);
    }
    /**
     * find the mauch with INCLUDING.
     * @param m the mucher.
     * @param lemma the lemma.
     */
    public void findincluding(Matcher m, String lemma) {
        //the sentence I got from the text.
        String sentence = m.group();
        //get the hypernym from the sentence
        List<String> hypernymList = regexesList.get(2).getHypernym(sentence);
        // if I have this hypernym so dont add him.
        if (!getHypernymMap().containsKey(hypernymList.get(0))) {
            getHypernymMap().put(hypernymList.get(0), new HashMap<>());
        }
        //list of hyponym
        List<String> hyponymsList = regexesList.get(2).getHyponym(sentence);
        //check if the lemma shows in hyponym1
        checkToLemma(hyponymsList, lemma, hypernymList);
        //put the words in the map.
        putValuesInMap(hypernymList.get(0), hyponymsList);
    }
    /**
     * find the mauch with especilllay.
     * @param m the mucher.
     * @param lemma the lemma.
     */
    public void findespecially(Matcher m, String lemma) {
        //the sentence I got from the text.
        String sentence = m.group();
        //get the hypernym from the sentence
        List<String> hypernymList = regexesList.get(3).getHypernym(sentence);
        // if I have this hypernym so dont add him.
        if (!getHypernymMap().containsKey(hypernymList.get(0))) {
            getHypernymMap().put(hypernymList.get(0), new HashMap<>());
        }
        //list of hyponym
        List<String> hyponymsList = regexesList.get(3).getHyponym(sentence);
        //check if the lemma shows in hyponym1
        checkToLemma(hyponymsList, lemma, hypernymList);
        //put the words in the map.
        putValuesInMap(hypernymList.get(0), hyponymsList);
    }
    /**
     * find the mauch with wich is.
     * @param m the mucher.
     * @param lemma the lemma.
     */
    public void findWichIs(Matcher m, String lemma) {
        //the sentence from the regix.
        String sentence = m.group();
        //get the hypernym from the sentence
        List<String> hypernymList = regexesList.get(4).getHypernym(sentence);
        // if I have this hypernym so dont add him.
        if (!getHypernymMap().containsKey(hypernymList.get(0))) {
            getHypernymMap().put(hypernymList.get(0), new HashMap<>());
        }
        //list of hyponym
        List<String> hyponymsList = regexesList.get(4).getHyponym(sentence);
        //check if the lemma shows in hyponym1
        checkToLemma(hyponymsList, lemma, hypernymList);
        //put the words in the map.
        putValuesInMap(hypernymList.get(0), hyponymsList);
    }

    /**
     * inser data to the hash map.
     * @param file0 the file we read from.
     * @param lemma the lemma we search.
     * @throws IOException when there is no file.
     */
    public void insertData(File file0, String lemma) throws IOException {
        Matcher m1, m2, m3, m4, m5;
        discoverMap = new HashMap<String, Integer>();
        File[] filesList = file0.listFiles();
        for (File file : filesList) {
            Scanner scanner = new Scanner(file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str = reader.readLine();
            while (str != null) {
                if (str.contains("<np>") && str.contains("</np>")) {
                    if (lemma == null) {
                        if (str.contains("such")) {
                            m1 = regexesList.get(0).getPt().matcher(str);
                            //NP such as NP
                            if (m1.find()) {
                                findSuch(m1, null);
                            }
                            m2 = regexesList.get(1).getPt().matcher(str);
                            //such NP as NP
                            if (m2.find()) {
                                findsuchasNp(m2, null);
                            }
                        }
                        if (str.contains("including")) {
                            m3 = regexesList.get(2).getPt().matcher(str);
                            //including
                            if (m3.find()) {
                                findincluding(m3, null);
                            }
                        }
                        if (str.contains("especially")) {
                            m4 = regexesList.get(3).getPt().matcher(str);
                            //especially
                            if (m4.find()) {
                                findespecially(m4, null);
                            }
                        }
                        if (str.contains("which is")) {
                            m5 = regexesList.get(4).getPt().matcher(str);
                            //which is
                            if (m5.find()) {
                                findWichIs(m5, lemma);
                            }
                        }
                    } else {
                        if (str.contains(lemma)) {
                            if (str.contains("such")) {
                                m1 = regexesList.get(0).getPt().matcher(str);
                                //NP such as NP
                                if (m1.find()) {
                                    findSuch(m1, lemma);
                                }
                                m2 = regexesList.get(1).getPt().matcher(str);
                                //such NP as NP
                                if (m2.find()) {
                                    //the sentence I got from the text.
                                    String sentence = m2.group();
                                    //get the hypernym from the sentence
                                    List<String> hypernymList = regexesList.get(1).getHypernym(sentence);
                                    // if I have this hypernym so dont add him.
                                    if (!getHypernymMap().containsKey(hypernymList.get(0))) {
                                        getHypernymMap().put(hypernymList.get(0), new HashMap<>());
                                    }
                                    //list of hyponym
                                    List<String> hyponymsList = regexesList.get(1).getHyponym(sentence);
                                    //check if the lemma shows in hyponym1
                                    checkToLemma(hyponymsList, lemma, hypernymList);
                                    //put the words in the map.
                                    putValuesInMap(hypernymList.get(0), hyponymsList);
                                }
                            }
                            if (str.contains("including")) {
                                m3 = regexesList.get(2).getPt().matcher(str);
                                //including
                                if (m3.find()) {
                                    //the sentence I got from the text.
                                    String sentence = m3.group();
                                    //get the hypernym from the sentence
                                    List<String> hypernymList = regexesList.get(2).getHypernym(sentence);
                                    // if I have this hypernym so dont add him.
                                    if (!getHypernymMap().containsKey(hypernymList.get(0))) {
                                        getHypernymMap().put(hypernymList.get(0), new HashMap<>());
                                    }
                                    //list of hyponym
                                    List<String> hyponymsList = regexesList.get(2).getHyponym(sentence);
                                    //check if the lemma shows in hyponym1
                                    checkToLemma(hyponymsList, lemma, hypernymList);
                                    //put the words in the map.
                                    putValuesInMap(hypernymList.get(0), hyponymsList);
                                }
                            }
                            if (str.contains("especially")) {
                                m4 = regexesList.get(3).getPt().matcher(str);
                                //especially
                                if (m4.find()) {
                                    //the sentence I got from the text.
                                    String sentence = m4.group();
                                    //get the hypernym from the sentence
                                    List<String> hypernymList = regexesList.get(3).getHypernym(sentence);
                                    // if I have this hypernym so dont add him.
                                    if (!getHypernymMap().containsKey(hypernymList.get(0))) {
                                        getHypernymMap().put(hypernymList.get(0), new HashMap<>());
                                    }
                                    //list of hyponym
                                    List<String> hyponymsList = regexesList.get(3).getHyponym(sentence);
                                    //check if the lemma shows in hyponym1
                                    checkToLemma(hyponymsList, lemma, hypernymList);
                                    //put the words in the map.
                                    putValuesInMap(hypernymList.get(0), hyponymsList);
                                }
                            }
                            if (str.contains("which is")) {
                                m5 = regexesList.get(4).getPt().matcher(str);
                                //which is
                                if (m5.find()) {
                                    //the sentence from the regix.
                                    String sentence = m5.group();
                                    //get the hypernym from the sentence
                                    List<String> hypernymList = regexesList.get(4).getHypernym(sentence);
                                    // if I have this hypernym so dont add him.
                                    if (!getHypernymMap().containsKey(hypernymList.get(0))) {
                                        getHypernymMap().put(hypernymList.get(0), new HashMap<>());
                                    }
                                    //list of hyponym
                                    List<String> hyponymsList = regexesList.get(4).getHyponym(sentence);
                                    //check if the lemma shows in hyponym1
                                    checkToLemma(hyponymsList, lemma, hypernymList);
                                    //put the words in the map.
                                    putValuesInMap(hypernymList.get(0), hyponymsList);
                                }
                            }
                        }
                    }
                }
                str = reader.readLine();
            }
            scanner.close();
        }
        deleteHypernymsLessThanThree();
    }
}
