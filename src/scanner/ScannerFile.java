package scanner;

import Exceptions.LexicalError;
import model.SymbolTable;
import pif.Pif;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerFile {

    private final List<String> acceptedTokens;
    private final String tokensFile ;
    private SymbolTable symbolTable;
    private Pif pif;
    private String programFile;


    public ScannerFile(String programFile) {
        this.tokensFile = "src/tokens.in";
        this.acceptedTokens = new ArrayList<>();
        this.symbolTable = new SymbolTable();
        this.pif = new Pif();
        this.programFile = programFile;
        readFromTokenFile();
        System.out.println(acceptedTokens);
    }

    private void readFromTokenFile(){
        try {
            File tokens = new File(tokensFile);
            Scanner reader = new Scanner(tokens);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                data = data.replaceAll(" ","");
                System.out.println(data);
                acceptedTokens.add(data);
            }
            reader.close();
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    public void readFromProgramFile() throws LexicalError {
        try {
            File programFile = new File(this.programFile);
            Scanner reader = new Scanner(programFile);
            int lineCounter = 0;
            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                lineCounter++;
                try{
                    parseLine(lineCounter,currentLine);
                }
                catch (LexicalError error){
                    pif.writeToPifFile();
                    symbolTable.writeToFile();
                    reader.close();
                    error.printStackTrace();
                }
            }
            reader.close();
            symbolTable.writeToFile();
            pif.writeToPifFile();
        }
        catch (IOException error){
            error.printStackTrace();
        }
    }

    private void parseLine(int lineNumber, String line) throws LexicalError{
        String[] lineSplitedBySpace = splitBySpace(line);
        //System.out.println(Arrays.toString(lineSplitedBySpace));
        Deque<String> elements = new LinkedList<>(Arrays.asList(lineSplitedBySpace));
        //System.out.println(elements);
        while (!elements.isEmpty()){
            String element = elements.remove();
            //System.out.println(element);
            if (element.isEmpty() || element.equals(" ")){
                continue;
            }
            if(acceptedTokens.contains(element)){
                pif.addElement(element,-1);
                continue;
            }
            //integer
            Matcher matcherInteger = Utils.integerPattern.matcher(element);
            if(matcherInteger.matches()){
                int positionInSymbolTable = symbolTable.addAndReturnPosition(element);
                pif.addElement("integer",positionInSymbolTable);
                continue;
            }
            //string
            Matcher matcherString = Utils.stringPattern.matcher(element);
            if (matcherString.matches()){
                int positionInSymbolTable = symbolTable.addAndReturnPosition(element);
                pif.addElement("string",positionInSymbolTable);
                continue;
            }
            //identifier
            Matcher matcherIdentifier = Utils.identifierPattern.matcher(element);
            if(matcherIdentifier.matches()){
                int positionInSymbolTable = symbolTable.addAndReturnPosition(element);
                pif.addElement("identifier",positionInSymbolTable);
                continue;
            }

            boolean wasAdded = false;


            for (int i = 0; i < Utils.separators.size(); i++){
                String separator = Utils.separators.get(i);
                if(element.contains(separator)){
                    System.out.println(
                            separator
                    );
                    String separatorWithRegex = Utils.separatorsRegex.get(i);
                    String patternWithSeparator = "(?<=[" + separatorWithRegex+"])" +"|(?=["+separatorWithRegex+"])";
                    String[] splitedElement = element.split(patternWithSeparator);
                    System.out.println(Arrays.toString(splitedElement));
                    for (int j = splitedElement.length - 1; j >= 0; j--){
                        elements.addFirst(splitedElement[j]);
                    }
                    wasAdded = true;
                    break;
                }
            }

            if(wasAdded){
                continue;
            }
            throw new LexicalError("Lexical Error line: " + lineNumber + " tokne: " + element);
        }
    }

    private String[] splitBySpace(String line){
        String[] elements =  line.split("\\s+");
        //System.out.println(Arrays.toString(elements));
        String[] elementsWhitoutSpace = new String[elements.length];
        int i = 0;
        for(String element : elements){
           elementsWhitoutSpace[i] = element.replaceAll("\\s+","");
           i++;
        }
        return elementsWhitoutSpace;
    }


}
