import grammar.Grammar;
import parser.ParserAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("src/grammar.in");
//        while (true) {
//            printMenu();
//            Scanner keyboard = new Scanner(System.in);
//            int choice = Integer.parseInt(keyboard.next());
//            switch (choice){
//                case 1: grammar.getTerminals().forEach(System.out::println);
//                    break;
//                case 2: grammar.getNonTerminals().forEach(System.out::println);
//                    break;
//                case 3: grammar.getProductions().forEach(System.out::println);
//                    break;
//                case 4: {
//                    String nonTerminal = keyboard.next();
//                    grammar.getFirstProduction(nonTerminal).forEach(System.out::println);
//                }
//                    break;
//                case 5: {
//                    if(grammar.checkCFG()){
//                        System.out.println("CFG correct");
//                    }
//                    else {
//                        System.out.println("CFG incorrect");
//                    }
//                    break;
//                }
//                default: return;
//            }
//        }


        ParserAlgorithm parserAlgorithm = new ParserAlgorithm("src/grammar.in");
        List<String> sequence = readFromFile("src/sequence.txt");
        System.out.println(parserAlgorithm.parseAlgorithm(sequence));
    }

    private static List<String> readFromFile(String fileName){
        File file = new File(fileName);
        List<String> stringList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line =  scanner.nextLine();
                String[] splitBySpace = line.split(" ");
                String element = splitBySpace[0];
                stringList.add(element);
            }
            scanner.close();
        }catch (IOException err){
            err.printStackTrace();
        }
        return stringList;
    }

    public static void printMenu(){
        System.out.println('\n');
        System.out.println("0.Exit.");
        System.out.println("1.Display the set of terminals.");
        System.out.println("2.Display the nonTerminals.");
        System.out.println("3.Display all the productions.");
        System.out.println("4.Display the productions for a given nonTerminal");
        System.out.println("5.Check CFG");
    }
}
