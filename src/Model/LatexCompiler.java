package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Handles LaTeX files reading and compilation.
 */
public class LatexCompiler {

    private static final String DEFAULT_OUTPUT_DIRECTORY = "./Latex/out";
    private static LatexCompiler compiler = new LatexCompiler();

    /* Singleton class */
    private LatexCompiler() {

    }

    public static LatexCompiler getInstance() {
        return compiler;
    }

    /**
     * Check if the file corresponding to the filePath is valid (exist, is a file and can read)
     *
     * @param filePath A string containing the location of the tested file
     * @return TRUE if the file is valid and FALSE otherwise
     */
    private static boolean isFileValid(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }

    /**
     * getLines can access an InputStream and store in an String all the line that the stream contain
     *
     * @param input The inputstream from which the string will be extracted
     * @return The complete string coming from the stream
     * @throws Exception If the .readLine throw an exception, getLines will throw it to an upper function
     */
    private static String getLines(InputStream input) throws Exception {
        String line;
        StringBuilder allText = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        while ((line = in.readLine()) != null) {
            allText.append(line).append("\n");
        }

        return allText.toString();
    }

    /**
     * The file corresponding to the file path will be compile using pdfLatex and the output, if an exception doesn't
     * occur, will be stored in ./Latex/out.
     *
     * @param filePath String corresponding to the location of .tex file
     * @throws Exception Two Exception can be thrown : - The first one if the file is not valid(check isValid())
     *                   - The second one is if an error occur during the compilation
     */
    public static void runProcess(String filePath) throws Exception {
        //Check if the filePath is valid
        if (!isFileValid(filePath)) {
            throw new Exception("Error in compiling the latex : " + filePath + "is not valid");
        }
        String command = "pdflatex -file-line-error -interaction=nonstopmode -synctex=1 " +
                "-output-format=pdf -output-directory " + DEFAULT_OUTPUT_DIRECTORY + " " + filePath;
        String outStreamText = ""; //Is used to track error in the latex file
        String outStreamError = "";
        Process pro = Runtime.getRuntime().exec(command);
        outStreamText = getLines(pro.getInputStream());
        outStreamError = getLines(pro.getErrorStream());
        pro.waitFor();
        if (pro.exitValue() != 0) {
            throw new Exception("Error code : " + pro.exitValue());
        }
    }
}
