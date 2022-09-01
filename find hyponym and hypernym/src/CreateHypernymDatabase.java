import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * the main class of part one.
 */
public class CreateHypernymDatabase {
    /**
     * main pogram of part one.
     * @param args the input.
     * @throws IOException when there is no input.
     */
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        if (args.length != 2) {
            System.out.println("2 arguments expected");
            return;
        }
        File file0 = new File(args[0]);
        File output = new File(args[1]);
        FileWriter fileWriter = new FileWriter(output);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        DoubleHashMap hypernymMap = new DoubleHashMap();
        hypernymMap.insertData(file0, null);
        hypernymMap.toString(printWriter);
        fileWriter.close();
        printWriter.close();
        //long middleTime = System.nanoTime();
        //System.out.println("the time for pogram is: " + TimeUnit.NANOSECONDS.toSeconds(middleTime - startTime));
    }
}
