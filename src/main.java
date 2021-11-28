import grammar.Grammar;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("src/grammar.in");
        while (true) {
            printMenu();
            Scanner keyboard = new Scanner(System.in);
            int choice = Integer.parseInt(keyboard.next());
            switch (choice){
                case 1: grammar.getTerminals().forEach(System.out::println);
                    break;
                case 2: grammar.getNonTerminals().forEach(System.out::println);
                    break;
                case 3: grammar.getProductions().forEach(System.out::println);
                    break;
                case 4: {
                    String nonTerminal = keyboard.next();
                    grammar.getProductionsForNonTerminal(nonTerminal).forEach(System.out::println);
                }
                    break;
                case 5: {
                    if(grammar.checkCFG()){
                        System.out.println("CFG correct");
                    }
                    else {
                        System.out.println("CFG incorrect");
                    }
                    break;
                }
                default: return;
            }
        }
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
