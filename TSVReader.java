package WorkloadGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TSVReader {

    static String traceTSVfile;
    public static ArrayList<Long> jobArrivalTimes = new ArrayList<>();
    public static void parseArrivalTimes() {


        BufferedReader br = null;
        String line;
        String cvsSplitBy = "\\t";

        try {

            br = new BufferedReader(new FileReader(traceTSVfile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                jobArrivalTimes.add(Long.parseLong(country[2]));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}