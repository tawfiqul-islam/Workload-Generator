package WorkloadGenerator;

import java.util.Properties;
import java.lang.*;
import java.io.*;

public class SettingsLoader {

    public static void loadSettings() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            //specify properties file with full path... or only file name if it's in current directory
            input = new FileInputStream("/home/tawfiq/research/workload/workloadgen.ini");

            // load a properties file
            prop.load(input);

            TSVReader.traceTSVfile=prop.getProperty("tracepath");
            GenerateWorkload.workloadPath=prop.getProperty("workloadpath");
            GenerateWorkload.inputPath=prop.getProperty("inputpath");
            GenerateWorkload.outputPath=prop.getProperty("outputpath");
            GenerateWorkload.jarPath=prop.getProperty("jarpath");
            GenerateWorkload.mode=Integer.parseInt(prop.getProperty("mode"));
            GenerateWorkload.prIteration=prop.getProperty("prIteration");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
