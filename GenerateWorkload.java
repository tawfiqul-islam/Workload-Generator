package WorkloadGenerator;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateWorkload {

    static String workloadPath;
    static String inputPath;
    static String outputPath;
    static String jarPath;
    static int mode;
    static String prIteration;
    static String mainClassNameWC="cn.ac.ict.bigdatabench.WordCount";
    static String mainClassNameSort="cn.ac.ict.bigdatabench.Sort";
    static String mainClassNamePR="cn.ac.ict.bigdatabench.PageRank";
    static double deadline=0;
    public static JSONObject constructJobRequest(int resultID, int exec, int cpe, double mpe, String inPath, String outPath, String jarPath, String mainClass, String appArgs, boolean shutdown) {

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("resultID", resultID);
            jsonObj.put("executors", exec);
            jsonObj.put("coresPerExecutor", cpe);
            jsonObj.put("memoryPerExecutor", mpe);
            jsonObj.put("inputPath", inPath);
            jsonObj.put("outputPath", outPath);
            jsonObj.put("appJarPath", jarPath);
            jsonObj.put("mainClassName", mainClass);
            jsonObj.put("appArgs", appArgs);
            jsonObj.put("shutDown", shutdown);
            jsonObj.put("priority", true);
            jsonObj.put("deadline", deadline);

        } catch (Exception e) {
            System.out.println("Exception while constructing job request json " + e);
        }
        return jsonObj;
    }

    public static void main(String args[]) {

        JSONObject jsonObj = null;
        Random ran = new Random();
        boolean shutdown = false;
        PrintWriter out = null;

        SettingsLoader.loadSettings();
        TSVReader.parseArrivalTimes();
        try {
            out = new PrintWriter(workloadPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < TSVReader.jobArrivalTimes.size(); i++) {

            if (i == (TSVReader.jobArrivalTimes.size() - 1)) {
                shutdown = true;
            }

            int exec, cpe, mpe;
            exec = 1 + ran.nextInt(8);
            cpe = 1 + ran.nextInt(4);
            mpe = 4 + ran.nextInt(9);
            deadline=120+ran.nextInt(180);

            if(mode==1)
            {
                jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                        inputPath+"WC",
                        outputPath,
                        jarPath,
                        mainClassNameWC,
                        "", shutdown);
            }
            else if(mode==2)
            {
                jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                        inputPath+"Sort",
                        outputPath,
                        jarPath,
                        mainClassNameSort,
                        "", shutdown);
            }
            else if(mode==3)
            {
                int tmpIteration = 1 + ran.nextInt(Integer.parseInt(prIteration));
                deadline=500+ran.nextInt(180);
                jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                        inputPath+"PR",
                        outputPath,
                        jarPath,
                        mainClassNamePR,
                        Integer.toString(tmpIteration), shutdown);
            }
            else if(mode==4)
            {
                int applicationType=ran.nextInt(100);
                String prItTemp=Integer.toString(1+ran.nextInt(10));
                if(applicationType>=0&&applicationType<=45)
                {
                    jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                            inputPath+"WC",
                            outputPath,
                            jarPath,
                            mainClassNameWC,
                            "", shutdown);
                }
                else if(applicationType>=46&&applicationType<=90)
                {
                    jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                            inputPath+"Sort",
                            outputPath,
                            jarPath,
                            mainClassNameSort,
                            "", shutdown);
                }
                else
                {
                    deadline=500+ran.nextInt(180);

                    jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                            inputPath+"PR",
                            outputPath,
                            jarPath,
                            mainClassNamePR,
                            prItTemp, shutdown);
                }
            }
            else if(mode==5)
            {
                cpe = 2;
                mpe = 6;
                deadline=300;

                int applicationType=ran.nextInt(100);
                String prItTemp=Integer.toString(1+ran.nextInt(10));
                if(applicationType>=0&&applicationType<=45)
                {
                    exec=2;
                    jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                            inputPath+"WC",
                            outputPath,
                            jarPath,
                            mainClassNameWC,
                            "", shutdown);
                }
                else if(applicationType>=46&&applicationType<=90)
                {
                    exec=4;
                    jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                            inputPath+"Sort",
                            outputPath,
                            jarPath,
                            mainClassNameSort,
                            "", shutdown);
                }
                else
                {
                    exec=6;
                    deadline=800;

                    jsonObj = constructJobRequest(i+1, exec, cpe, mpe,
                            inputPath+"PR",
                            outputPath,
                            jarPath,
                            mainClassNamePR,
                            prItTemp, shutdown);
                }
            }
            else {
                //error ..unknown mode
            }

            out.println(TSVReader.jobArrivalTimes.get(i)+" "+jsonObj);
        }
        if(out!=null)
            out.close();
    }
}
