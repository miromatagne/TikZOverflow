package Model.Latex;

import Model.Exceptions.LatexHandler.LatexCompilationException;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles LaTeX files reading and compilation.
 */
public class LatexHandler {

    private static final LatexHandler compiler;

    static {
        compiler = new LatexHandler();
    }

    /**
     * Singleton class
     */
    private LatexHandler() {

    }

    public static LatexHandler getInstance() {
        return compiler;
    }

    /**
     * Clear the stream.
     *
     * @param input The inputstream to clear
     * @throws IOException If the readLine throw an exception, getLines will throw it to an upper function
     */
    private void clearStream(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        while (true) {
            if ((in.readLine()) == null) {
                break;
            }
        }
    }

    /**
     * Extracts shapes-only code from full LaTeX code.
     *
     * @param fullCode full LaTeX source code.
     * @param trim     option to trim lines or not
     * @return TikZ shapes-only code
     */
    public String extractShapesSubCode(String fullCode, boolean trim) {
        Pattern pattern = Pattern.compile("\\\\begin\\{tikzpicture}.*\\\\end\\{tikzpicture}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fullCode);
        if (matcher.find()) {
            int index = 1;
            String totalString = "";
            String[] strings = matcher.group(0).split("\n");
            while (index < strings.length - 1) {
                if (trim) {
                    totalString = totalString.concat(strings[index++].trim() + "\n");
                } else {
                    totalString = totalString.concat(strings[index++] + "\n");
                }
            }
            return totalString;
        }
        return null;
    }

    /**
     * Builds full LaTeX code from shapes-only code.
     *
     * @param shapesOnlyCode shapes-only TikZ code
     * @param codeToReplace  old save where the shapesOnlyCode is replaced by the new one
     * @return full LaTeX code
     */
    public String buildFullCodeFromShapesOnlyCode(String shapesOnlyCode, String codeToReplace) {
        StringBuilder shapesOnlyCodeBuilder = new StringBuilder();
        for (String line : shapesOnlyCode.split("\n")) {
            shapesOnlyCodeBuilder.append("\t").append(line.trim()).append("\n");
        }
        String shapesSubCode = LatexHandler.getInstance().extractShapesSubCode(codeToReplace, false);
        return codeToReplace.replace(Objects.requireNonNull(shapesSubCode), shapesOnlyCodeBuilder.toString());
    }

    /**
     * The file corresponding to the file path will be compile using pdfLatex and the output, if an exception doesn't
     * occur, will be stored in ./Latex/out.
     *
     * @param filePath String corresponding to the location of .tex file
     * @param outputDirectoryPath Directory where we should put the result of the compilation
     * @throws LatexCompilationException Two Exception can be thrown : - The first one if the file is not valid(check isValid())
     *                                   - The second one is if an error occur during the compilation
     */
    public void runProcess(String filePath, String outputDirectoryPath) throws LatexCompilationException {
        try {
            String command = "pdflatex -file-line-error -interaction=nonstopmode -synctex=1 " +
                    "-output-format=pdf -output-directory " + outputDirectoryPath + " " + filePath;
            Process pro = Runtime.getRuntime().exec(command);
            clearStream(pro.getInputStream());
            clearStream(pro.getErrorStream());
            pro.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new LatexCompilationException(e);
        }
    }
}
