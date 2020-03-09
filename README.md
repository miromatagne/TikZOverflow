# TikZOverflow : INFO-F-307 project

TikZOverflow is a Java software that aims to simplify the design of TikZ diagrams
by allowing graphical composition of the diagrams and automatic generation of the associated
TikZ/LaTeX code.

## Using TikZOverflow

TikZOverflow requires Java 11, and the unit tests need to be run with JUnit 5.4.
To compile the project, you will need to install ant.

## Compilation
Run the ant command with the tikzoverflow.xml structure file.
```
ant -buildfile tikzoverflow.xml
```

### Starting the app

Run the jar file to start the software.
```
cd out/artifacts
```
Now every folder corresponds to a specific version of the software. Choose the most recent, and execute
the jar file inside it.
```
java -jar *.jar
```
<!--
## Configuration
## Serveur 

TO DO: Informations sur la configuration du serveur

## Client

TO DO: Informations sur la configuration du client
-->
## Tests

Run any class in the test folder to execute the corresponding unit tests.

<!--
# Misc

## Développement

## Screenshot

## License
-->