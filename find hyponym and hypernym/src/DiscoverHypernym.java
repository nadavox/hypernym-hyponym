import java.io.File;
import java.io.IOException;

/**
 * the discovery class.
 */
public class DiscoverHypernym {

    /**
     * the main method of part 2.
     * @param args the input.
     * @throws IOException when there is no input.
     */
    public static void main(String[] args) throws IOException {
        //long startTime = System.nanoTime();
        if (args.length != 2) {
            System.out.println("2 arguments expected");
            return;
        }
        File file0 = new File(args[0]);
        String lemma = args[1];
        DoubleHashMap hypernymMap = new DoubleHashMap();
        hypernymMap.insertData(file0, lemma);
        hypernymMap.printDiscoverHypernym();
        //long middleTime = System.nanoTime();
        //System.out.println("the time for pogram is: " + TimeUnit.NANOSECONDS.toSeconds(middleTime - startTime));

    }
}
