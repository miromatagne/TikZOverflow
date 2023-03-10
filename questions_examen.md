# Examen

Voici vos questions pour l'examen d'INFO-F-307.

Pour y répondre, concertez-vous (via le moyen de votre choix) pour répondre au mieux à chaque question.

Pour chaque question, répondez de manière claire, concise et complète, en utilisant la méthode qui vous paraît la plus appropriée, à savoir:
  - en répondant dans ce fichier
  - en annotant/modifiant vos fichiers
    - par exemple, si une question porte sur un mauvais code, vous pouvez proposer une correction
  - en ajoutant d'autres fichiers (PDF, ...)
  - en mettant un lien vers une vidéo de vous nous répondant oralement
  - ...

Qu'importe la manière utilisée, si vous ne répondez pas directement à une question dans ce fichier, décrivez clairement après cette question où la réponse peut être trouvée (par exemple, "voir le fichier Controller.java").

Avant 11h, une fois que vous pensez avoir fini de répondre à nos questions, commitez et pushez vos modifications sur le git.

## Question 1 : Organisation du travail

Si on regarde la répartition des tâches, on peut observer un écart de 14h entre le maximum et le minimum d'heures travaillées par personne. Comment pouvez-vous justifier cet écart? Est-ce conforme à la méthodologie de gestion de travail définie dans le cours? Oui/Non, pourquoi?

En effet, ce n’est pas tout à fait conforme à ce que propose la méthodologie; l'idéal de celle-ci suggère une charge de travail uniformément répartie au sein de l'équipe. Nous avons essayé de nous y tenir au maximum mais, compte tenu des circonstances, il nous a été compliqué de respecter cet engagement. En effet, le fait que nous ayons des horaires relativement différents a rendu l'organisation et la multiplication des binômes plus compliquées.

L'idéal de la méthodologie XP suggère une proximité de l'équipe à tout instant, afin de permettre une rotation des binômes lors du pair-programming très efficace et donne donc lieu à un lissage de la quantité de travail sur l'équipe. Bien que nous aurions pu faire un effort pour uniformiser davantage la charge de travail, ce qui a d'ailleurs été évoqué dans notre réunion de rétrospective de projet, le manque de proximité de l'équipe au vu de la diversité des horaires a été un obstacle considérable. De plus, du fait de notre novicité dans la méthodologie XP, les estimations des charges de travail que nous avons faites ne se sont pas toujours avérées très précises, surtout dans les premières itérations. Bien qu'il s'agit là d'un principe Agile dans le sens où l'équipe doit s'adapter à ce genre d'imprévus, la nouveauté de certains éléments (JavaFX,Tikz,...) a rendu assez difficile le "partage" de fonctionnalités entre les binômes, et donc cela a engendré une répartition parfois inéquitable dans la charge de travail.

D’autre part, certains membres de l'équipe ont fait des heures supplémentaires, notamment lors du Refactoring important qui a été opéré lors de l'itération 3, ce qui favorise les écarts entre les charges de travail des différents membres de l'équipe.

Cependant, la communication au sein de l'équipe s'est avérée très efficace et ouverte, aucun membre ne considère qu'il a travaillé beaucoup plus que le reste de l'équipe ni s’est senti surchargé, et aucun ne s'est senti délaissé dans le sens où il n'aurait pas assez contribué au projet. La communication a été l’élément le plus important de notre projet, et nous avons toujours réussi à régler au mieux les différents problèmes que nous avons rencontrés, qu’ils soient individuels ou collectifs, ainsi qu’à s’adapter aux imprévus.

## Question 2 : Architecture MVC

Le code présente beaucoup de `XListeners`. A quel design pattern correspondent-ils? Comment vous l'avez implémenté dans votre code? Pour quel objectif?

Les XListeners correspondent au pattern "Observer". Des objets peuvent "écouter" ou "observer" d'autres objets en attendant des informations via des interfaces. La classe de l'objet qui devrait être écouté contient une interface qu'il utilisera pour communiquer avec l'objet qui l'écoute. La classe de l'objet qui écoute (c'est l'observer/listener) implémente l'interface de l'objet observé. L'observé ne connaît a priori pas quelle classe pourrait l'écouter. Le but était de fortement découpler les différentes classes tout en permettant d'assurer leur communication afin de garder une grande indépendance entre les différentes parties du projet. Le code est donc facilement maintenable et l'intégration de nouveaux éléments dans la structure existante simplifiée. Nous avons surtout utilisé ce pattern pour permettre à la Vue d’automatiquement notifier tout changement (tel que l’action d’un bouton) au Contrôleur correspondant.

## Question 3 : Qualité de code

Quel est le rôle de cette méthode? Quels sont les problèmes en terme de qualité de code?

```java
@Override
    public void setPosX(float x) {
        float distance12 = getX2() - getX1();
        float distance13 = getX3() - getX1();
        x1 = x;
        x2 = x + distance12;
        x3 = x + distance13;
    }
```

Pour information, voici le contenu complet de la classe:

```java
public class Triangle extends Node {
    private float x1, y1, x2, y2, x3, y3;

    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        super(x1, y1);
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    /**
     * Generate TikZ code that creates the shape using the properties.
     *
     * @return generated code
     */
    @Override
    public String generateAndGetTikzCode() {
        float labelPosX = (Math.min(Math.min(getX1(),getX2()),getX3())+Math.max(Math.max(getX1(),getX2()),getX3()))/2;
        float labelPosY = Math.min(Math.min(getY1(),getY2()),getY3());

        String code = super.generateAndGetTikzCode();
        //first triangle's corner position
        code += "(" + getX1() + "," + getY1() + ") ";
        //position of the label and his content
        code += "node[color=black, below] at (" + labelPosX + "," + labelPosY + "){" + getLabel() +"} --";
        //second triangle's corner position
        code += "(" + getX2() + "," + getY2() + ") --";
        //third triangle's corner position
        code += "(" + getX3() + "," + getY3() + ") -- cycle;\n";
        return code;
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getX3() {
        return x3;
    }

    public float getY3() {
        return y3;
    }

    @Override
    public void setPosX(float x) {
        float distance12 = getX2() - getX1();
        float distance13 = getX3() - getX1();
        x1 = x;
        x2 = x + distance12;
        x3 = x + distance13;
    }

    @Override
    public void setPosY(float y) {
        float distance12 = getY2() - getY1();
        float distance13 = getY3() - getY1();
        y1 = y;
        y2 = y + distance12;
        y3 = y + distance13;
    }

}

```

Ces méthodes, "setPosX()" et "setPosY()", permettent de positionner un triangle par une position (x, y). Il faut dès lors modifier les positions des trois sommets. 
Le problème est que les fonctions setPosX et setPosY ne respectent pas la convention “single-responsibility”.
Le code a été corrigé comme suit. Les changements ont également été effectués dans le fichier "Triangle.java".

```java
    @Override
    public void setPosX(float x) {
        float distance12 = getX2() - getX1();
        float distance13 = getX3() - getX1();
        setPosX1(x);
        setPosX2(x + distance12);
        setPosX3(x + distance13);
    }

    public void setPosX1(float x){
        x1 = x;
    }

    public void setPosX2(float x){
        x2 = x;
    }

    public void setPosX3(float x){
        x3 = x;
    }


    @Override
    public void setPosY(float y) {
        float distance12 = getY2() - getY1();
        float distance13 = getY3() - getY1();
        setPosY1(y);
        setPosY2(y + distance12);
        setPosY3(y + distance13);
    }

    public void setPosY1(float y){
        y1 = y;
    }

    public void setPosY2(float y){
        y2 = y;
    }

    public void setPosY3(float y){
        y3 = y;
    }
  
```


## Question 4 : Qualité de code

Quel est le rôle de cette classe? Quels sont les problèmes en terme de qualité de code? Comment cette classe pourrait être améliorée?

```java
/**
 * Handle the .Log file generated after a Latex compilation in order to extract the errors log.
 */

public class LatexErrorsHandler extends FileHandler {
    private int errorsCounter = 0;
    private String errors = "";
    private String[] linesLogFile;
    private String syntaxErrorPrefix;
    private final static String MODULE_ERROR_PREFIX = "! LaTeX Error";
    private final static String DOCUMENT_ERROR = "*** (job aborted, no legal \\end found)";

    /**
     * Get the errors in the compiler.
     *
     * @return string with all the errors that the user let in the compiler
     */
    public String getErrors() {
        return errors;
    }

    /**
     * Get the counter of errors in the compiler.
     *
     * @return quantity of errors that occur in the compiler
     */
    public int getErrorsCounter() {
        return errorsCounter;
    }

    /**
     * Build a String that allows to identify the errors in the .log file.
     *
     * @return the global prefix of an error message in the .log file
     */
    public String generalErrorsPrefix(){
        String[] allDirectories;
        String path = Controller.Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";
        allDirectories = path.split("\\\\");
        String errorsLogPath = "";
        for(int i = 0; i < allDirectories.length; i++){
            errorsLogPath+=allDirectories[i];
            if(i<allDirectories.length-1){
                errorsLogPath+="/";
            }else {
                errorsLogPath+=":";
            }
        }
        return errorsLogPath;
    }

    /**
     * Find the errors that the user has written in the compiler.
     *
     * @param path     the path to the log file which contains all information about the last compilation
     *                 that we made
     * @throws LogErrorException If there was an error while reading the file.
     */
    public void errorLogs(String path) throws LogErrorException {
        try {
            errors = "";
            errorsCounter = 0;
            linesLogFile = super.readInFile(path).split("\n");
            syntaxErrorPrefix = generalErrorsPrefix();
            addErrors();
        } catch (IOException e) {
            throw new LogErrorException(e);
        }
    }

    /**
     * An error in the log file can be written on multiple lines and this method manage to catch errors and add if the
     * error is on multiple lines.
     */
    private void addErrors(){
        boolean lineIsError = false;
        for (String s : linesLogFile) {
            if (lineIsError) {
                lineIsError = addErrorLine(s);

            } else {
                if (s.equals(DOCUMENT_ERROR)) {
                    errorsCounter++;
                    errors += DOCUMENT_ERROR;
                } else {
                    lineIsError = catchError(s);
                }
            }
        }
    }

    /**
     * Looks for errors in the .log file.
     *
     * @param line          line to check if it is an error message
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean catchError(String line) {
        String[] words = line.split(":");
        String[] toAddError;
        String catchFile = "";
        boolean lineIsError = false;
        int i=0;
        while (i<words.length-1) {
            catchFile += words[i];
            catchFile += ":";
            if (catchFile.equals(syntaxErrorPrefix) || words[i].equals(MODULE_ERROR_PREFIX)) {
                lineIsError = true;
                errorsCounter++;
                if(catchFile.equals(syntaxErrorPrefix)){
                    errors += "line ";
                    toAddError = line.split(syntaxErrorPrefix);
                }
                else{
                    toAddError = line.split(MODULE_ERROR_PREFIX);
                    toAddError = toAddError[1].split(":");
                }
                errors += toAddError[1];
                if(toAddError[1].charAt(toAddError[1].length()-1) == '.'){
                    lineIsError = false;
                    errors +="\n";
                }
                i=words.length;
            }
            i++;
        }
        return lineIsError;
    }

    /**
     * Add a full line from log file to error.
     *
     * @param line         the line to add
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean addErrorLine(String line) {
        boolean lineIsError = true;
        errors += line;
        if(line.charAt(line.length()-1) == '.'){
            errors += "\n";
            lineIsError=false;
        }
        return lineIsError;
    }
}

```
Le rôle de cette classe est de lire dans un fichier d’extension “.log” qui contient toutes les informations relatives à une compilation du projet Latex. Plus spécifiquement, il cherche tous les messages d’erreurs survenus lors d’une compilation. La recherche des messages d’erreur se fait en analysant le début d’une ligne car ils ont en effet des préfixes définis que l’on répertorie dans les variables globales de la classe.

Cette classe a été créée suite à la remarque lors de l'itération 3 concernant la multi-responsabilité de la classe FileHandler. Elle a donc été séparée en plusieurs classes à responsabilités uniques, dont LatexErrorsHandler, qui s'occupe exclusivement du fichier concernant les erreurs de compilation. 

Les erreurs reprérées dans cette classe sont :

-la méthode generalErrorsPrefix() était en public alors qu'elle n'était utilisée que dans cette classe, nous l'avons donc changée en private pour ne pas violer l'encapsulation.

-certaines méthodes n'avaient pas un nom très explicite, elles ont été renommées.

-certaines règles de codage n'ont pas été respectées, comme les espaces avant les accolades, etc. Les modifications nécessaires ont été apportées.

Les modifications sont indiquées ci-dessous, mais ont également été appliquées sur la branche master.

```java
/**
 * Handle the .Log file generated after a Latex compilation in order to extract the errors log.
 */

public class LatexErrorsHandler extends FileHandler {
    private  int errorsCounter = 0;
    private String errors = "";
    private String[] linesLogFile;
    private String syntaxErrorPrefix;
    private final static String MODULE_ERROR_PREFIX = "! LaTeX Error";
    private final static String DOCUMENT_ERROR = "*** (job aborted, no legal \\end found)";

    /**
     * Get the errors in the compiler.
     *
     * @return string with all the errors that the user let in the compiler
     */
    public String getErrors() {
        return errors;
    }

    /**
     * Get the counter of errors in the compiler.
     *
     * @return quantity of errors that occur in the compiler
     */
    public int getErrorsCounter() {
        return errorsCounter;
    }

    /**
     * Build a String that allows to identify the errors in the .log file.
     *
     * @return the global prefix of an error message in the .log file
     */
    private String formatPath() {
        String[] allDirectories;
        String path = Controller.Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";
        allDirectories = path.split("\\\\");
        String errorsLogPath = "";
        for(int i = 0; i < allDirectories.length; i++) {
            errorsLogPath+=allDirectories[i];
            if(i<allDirectories.length-1){
                errorsLogPath+="/";
            } else {
                errorsLogPath+=":";
            }
        }
        return errorsLogPath;
    }

    /**
     * Find the errors that the user has written in the compiler.
     *
     * @param path     the path to the log file which contains all information about the last compilation
     *                 that we made
     * @throws LogErrorException If there was an error while reading the file.
     */
    public void errorLogs(String path) throws LogErrorException {
        try {
            errors = "";
            errorsCounter = 0;
            linesLogFile = super.readInFile(path).split("\n");
            syntaxErrorPrefix = formatPath();
            addErrors();
        } catch (IOException e) {
            throw new LogErrorException(e);
        }
    }

    /**
     * An error in the log file can be written on multiple lines and this method manage to catch errors and add if the
     * error is on multiple lines.
     */
    private void addErrors() {
        boolean lineIsError = false;
        for (String s : linesLogFile) {
            if (lineIsError) {
                lineIsError = addErrorLine(s);

            } else {
                if (s.equals(DOCUMENT_ERROR)) {
                    errorsCounter++;
                    errors += DOCUMENT_ERROR;
                } else {
                    lineIsError = formatErrors(s);
                }
            }
        }
    }

    /**
     * Looks for errors in the .log file, and formats it to be shown to the user.
     *
     * @param line          line to check if it is an error message
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean formatErrors(String line) {
        String[] words = line.split(":");
        String[] toAddError;
        String catchFile = "";
        boolean lineIsError = false;
        int i=0;
        while (i<words.length-1) {
            catchFile += words[i];
            catchFile += ":";
            if (catchFile.equals(syntaxErrorPrefix) || words[i].equals(MODULE_ERROR_PREFIX)) {
                lineIsError = true;
                errorsCounter++;
                if(catchFile.equals(syntaxErrorPrefix)){
                    errors += "line ";
                    toAddError = line.split(syntaxErrorPrefix);
                }
                else{
                    toAddError = line.split(MODULE_ERROR_PREFIX);
                    toAddError = toAddError[1].split(":");
                }
                errors += toAddError[1];
                if(toAddError[1].charAt(toAddError[1].length()-1) == '.') {
                    lineIsError = false;
                    errors +="\n";
                }
                i=words.length;
            }
            i++;
        }
        return lineIsError;
    }

    /**
     * Add a full line from log file to error.
     *
     * @param line         the line to add
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean addErrorLine(String line) {
        boolean lineIsError = true;
        errors += line;
        if(line.charAt(line.length()-1) == '.') {
            errors += "\n";
            lineIsError=false;
        }
        return lineIsError;
    }
}

```


## Question 5 : Tests et Test Driven Development

Quels sont les problèmes avec cette classe de test, en relation à la méthodologie vue en cours ? Comment auriez-vous pu améliorer cette classe de test à partir de la phase de conception ? 

```java
/**
 * Tests the Triangle objects (extending Node)
 */

class TestTriangle {

    @Test
    void generateAndGetTikzCode() {
        Triangle t = new Triangle(2,1,4,5,1,8);
        t.setColor(Color.valueOf("#990000"));
        t.setOutlineThickness(60);
        t.setLabel("Triangle");
        String code = t.generateAndGetTikzCode();
        assertEquals("\\filldraw[fill={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0},line width=3.0] (2.0,1.0) node[color=black, below] at (2.5,1.0){Triangle} --(4.0,5.0) --(1.0,8.0) -- cycle;\n",code);
    }

    @Test
    void setPos() {
        Triangle t = new Triangle(1,1,6,5,3,2);
        t.setPosX((float)6.4);
        t.setPosY((float)7.9);
        assertEquals(6.4,Math.round(t.getX1() * 100.0) / 100.0);
        assertEquals(11.4,Math.round(t.getX2() * 100.0) / 100.0);
        assertEquals(8.4,Math.round(t.getX3() * 100.0) / 100.0);
        assertEquals(7.9,Math.round(t.getY1() * 100.0) / 100.0);
        assertEquals(11.9,Math.round(t.getY2() * 100.0) / 100.0);
        assertEquals(8.9,Math.round(t.getY3() * 100.0) / 100.0);
    }
}
```

La méthodologie vue en cours, le “test-driven development”, consiste à créer des tests lors de la conception de nouvelles classes ou méthodes et de développer le code afin que ceux-ci soient valides. De plus, chaque fonction testée doit l’être de façon unitaire.

On remarque que le test “setPos()” ne teste pas une mais bien deux méthodes de la classe Triangle. En effet, les méthodes “setPosX()” et “setPosY()” ne possèdent pas de tests séparés, ce qui fait que le seul endroit où elles sont testées est dans ce test commun. Ce test n’est donc pas unitaire. Nous avons fait une erreur lors de la phase de conception car nous n’avons pas divisé cette tâche en tâches atomiques. Malheureusement, cette erreur est également présente chez Arrow et Rectangle. Le fait de n’avoir que des test unitaires permet d’identifier directement quelle action est erronée ou bien voir où se situe une potentielle erreur. Avoir plein de fonctions testées dans un seul test ne peut que mener à une confusion sur la responsabilité de l’erreur si le test échoue.

Afin d’améliorer cela, nous aurions dû dès le début diviser de façon itérative ce test en différentes sous-tâches jusqu’à n’obtenir que des tâches atomiques. Une telle décomposition est indiquée ci-dessous. Ces corrections ont également été apportées dans les fichiers de test correspondant, c'est-à-dire "TestArrow.java", "TestRectangle.java" et "TestTriangle.java".

```java
/**
 * Tests the Triangle objects (extending Node)
 */

class TestTriangle {

    @Test
    void generateAndGetTikzCode() {
        Triangle t = new Triangle(2,1,4,5,1,8);
        t.setColor(Color.valueOf("#990000"));
        t.setOutlineThickness(60);
        t.setLabel("Triangle");
        String code = t.generateAndGetTikzCode();
        assertEquals("\\filldraw[fill={rgb,1:red,0.6000000238418579;green,0.0;blue,0.0},line width=3.0] (2.0,1.0) node[color=black, below] at (2.5,1.0){Triangle} --(4.0,5.0) --(1.0,8.0) -- cycle;\n",code);
    }

    @Test
    void setPosX() {
        Triangle t = new Triangle(1,1,6,5,3,2);
        t.setPosX((float)6.4);
        assertEquals(6.4,Math.round(t.getX1() * 100.0) / 100.0);
        assertEquals(11.4,Math.round(t.getX2() * 100.0) / 100.0);
        assertEquals(8.4,Math.round(t.getX3() * 100.0) / 100.0);
    }

    @Test
    void setPosY() {
        Triangle t = new Triangle(1,1,6,5,3,2);
        t.setPosY((float)7.9);
        assertEquals(7.9,Math.round(t.getY1() * 100.0) / 100.0);
        assertEquals(11.9,Math.round(t.getY2() * 100.0) / 100.0);
        assertEquals(8.9,Math.round(t.getY3() * 100.0) / 100.0);
    }

}
```

