package fa;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Fa fa = new Fa("src/FaFile.in");
        while (true) {
            printMenu();
            Scanner keyboard = new Scanner(System.in);
            int choice = Integer.parseInt(keyboard.next());
            switch (choice){
                case 1: fa.getStates().forEach(System.out::println);
                        break;
                case 2: fa.getAlphabet().forEach(System.out::println);
                        break;
                case 3: fa.getTransitions().forEach(System.out::println);
                         break;
                case 4: fa.getFinalStates().forEach(System.out::println);
                         break;
                case 5: {
                    String sequence = keyboard.next();
                    if(fa.checkSequence(sequence)){
                        System.out.println("Sequence can be obtained.");
                    }
                    else {
                        System.out.println("Sequence can NOT be obtained.");
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
        System.out.println("1.Display the set of states.");
        System.out.println("2.Display the alphabet.");
        System.out.println("3.Display all the transitions.");
        System.out.println("4.Display the final states");
        System.out.println("5.Check sequence");

    }
}
