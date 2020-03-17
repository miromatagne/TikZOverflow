package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LatexCompiler {

    private static LatexCompiler compiler = new LatexCompiler();
    private static final String DEFAULT_OUTPUT_DIRECTORY = "../Latex";

    /* Singleton class */
    private LatexCompiler() {

    }

    public static LatexCompiler getInstance() {
        return compiler;
    }

    private static boolean isFileValid(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }

    private static String getLines(String cmd, InputStream input) throws Exception {
        String line;
        StringBuilder allText = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        while ((line = in.readLine()) != null) {
            allText.append(line).append("\n");
        }

        return allText.toString();
    }

    public static void runProcess(String filePath) throws Exception {

        //Check if the filePath is valid
        if(isFileValid(filePath)){throw new Exception("Error in compiling the latex : " + filePath + "is not valid");}
        String command = "pdflatex -file-line-error -interaction=nonstopmode -synctex=1 " +
                "-output-format=pdf -output-directory=C:/IntelliJ_projet/groupe08/out " + filePath ;
        /*String command = "pdflatex -file-line-error -interaction=nonstopmode -synctex=1 " +
                "-output-format=pdf -output-directory=" + DEFAULT_OUTPUT_DIRECTORY + " " + filePath ;
         */
        String outStreamText = "" ; //Is used to track error in the latex file
        String outStreamError = "" ;
        Process pro = Runtime.getRuntime().exec(command);
        outStreamText = getLines(filePath, pro.getInputStream()) ;
        outStreamError = getLines(filePath, pro.getErrorStream());
        pro.waitFor();
        if(pro.exitValue() != 0){throw new Exception("Error code : " + pro.exitValue());}
    }
}
