package parser;

import grammar.Grammar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ParserAlgorithm {
    private Grammar grammar;
    private Parser parser;

    public ParserAlgorithm(String fileName) {
        this.grammar = new Grammar(fileName);
        this.parser = new Parser(grammar);
    }

    public void writeToFile(String type){
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));
            if(type.equals("error")){
                bufferedWriter.write("NOT ACCEPTED");
                bufferedWriter.close();
            }
            else{
                List<String> derivationString = parser.getDerivationString();
                StringBuilder stringBuilder = new StringBuilder();
                derivationString.forEach(derivation -> stringBuilder.append("[").append(derivation).append("]->"));
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.close();
            }
        }catch (IOException err){
            err.printStackTrace();
        }
    }

    public boolean parseAlgorithm(List<String> string) {
        while ((!parser.getCurrentState().equals(parser.FINAL_STATE)) && (!parser.getCurrentState().equals(parser.ERROR_STATE))) {
            if(parser.getCurrentState().equals(parser.STANDARD_STATE)){
                if(parser.getIndex() == string.size() && parser.getInputStack().isEmpty()){
                    parser.success();
                    //System.out.println(parser.getInputStack().toString());
                }
                else {
                    if (parser.getInputStack().isEmpty()) {
                        parser.insuccess();
                    } else {
                        GrammarElement grammarElementOfInput = parser.getInputStack().getFirst();
                        if (grammar.getNonTerminals().contains(grammarElementOfInput.getElement())) {
                            parser.expand();
                           // System.out.println(parser.getInputStack().toString());
                        } else {
                            if (string.size() <= parser.getIndex()) {
                                parser.insuccess();
                               // System.out.println(parser.getInputStack().toString());
                            }
                            else{
                                String character = string.get(parser.getIndex());
                                if (grammarElementOfInput.getElement().equals(character)) {
                                    parser.advance();
                                   // System.out.println(parser.getInputStack().toString());
                                } else {
                                    parser.insuccess();
                                  //  System.out.println(parser.getInputStack().toString());
                                }
                            }
                        }
                    }
                }
            }
            else {
                if(parser.getCurrentState().equals(parser.BACK_STATE)){
                    GrammarElement grammarElementOfToDo = parser.getTodoStack().getLast();
                    if(grammar.getTerminals().contains(grammarElementOfToDo.getElement())){
                        parser.back();
                        //System.out.println(parser.getInputStack().toString());
                    }else {
                        parser.tryAgain();
                        //System.out.println(parser.getInputStack().toString());
                    }
                }
            }
        }
        if(parser.getCurrentState().equals(parser.ERROR_STATE)){
            writeToFile("error");
            return false;
        }
        else{
            writeToFile("derivation");
            return true;
        }

    }
}
