package Model;

import Model.Exceptions.LatexCompilationException;

import java.io.*;

/**
 * Handles LaTeX files reading and compilation.
 */
public class LatexCompiler {

    private static final String DEFAULT_OUTPUT_DIRECTORY = "./Latex/out";
    private static final LatexCompiler compiler = new LatexCompiler();

    /* Singleton class */
    private LatexCompiler() {

    }

    public static LatexCompiler getInstance() {
        return compiler;
    }

    /**
     * Clear the stream
     *
     * @param input The inputstream to clear
     * @throws IOException If the readLine throw an exception, getLines will throw it to an upper function
     */
    private static void clearStream(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        while (true) {
            if ((in.readLine()) == null) {
                break;
            }
        }
    }

    /**
     * The file corresponding to the file path will be compile using pdfLatex and the output, if an exception doesn't
     * occur, will be stored in ./Latex/out.
     *
     * @param filePath String corresponding to the location of .tex file
     * @throws LatexCompilationException Two Exception can be thrown : - The first one if the file is not valid(check isValid())
     *                   - The second one is if an error occur during the compilation
     */
    public static void runProcess(String filePath) throws LatexCompilationException {
        try {
            String command = "pdflatex -file-line-error -interaction=nonstopmode -synctex=1 " +
                    "-output-format=pdf -output-directory " + DEFAULT_OUTPUT_DIRECTORY + " " + filePath;
            Process pro = Runtime.getRuntime().exec(command);
            clearStream(pro.getInputStream());
            clearStream(pro.getErrorStream());
            pro.waitFor();
            if (pro.exitValue() != 0){
                throw new LatexCompilationException();
            }
        } catch (IOException | InterruptedException e){
            throw new LatexCompilationException(e);
        }
    }
}
