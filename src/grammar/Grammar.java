package grammar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Grammar {
    private String fileName;
    private final List<String> terminals = new ArrayList<>();
    private final List<String> nonTerminals = new ArrayList<>();
    private String startingPoint;
    private final List<Production> productions = new ArrayList<>();


    public Grammar(String fileName) {
        this.fileName = fileName;
        readFromGrammarFile();
    }

    private void readFromGrammarFile() {
        try {
            File FaFile = new File(fileName);
            Scanner reader = new Scanner(FaFile);
            String type = "";
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data.equals("Terminals")) {
                    type = data;
                    continue;
                }
                if (data.equals("Nonterminals")) {
                    type = data;
                    continue;
                }
                if (data.equals("Starting")) {
                    type = data;
                    continue;
                }
                if (data.equals("Productions")) {
                    type = data;
                    continue;
                }

                if (type.equals("Terminals")) {
                    terminals.add(data);
                } else if (type.equals("Nonterminals")) {
                    nonTerminals.add(data);
                } else if (type.equals("Starting")) {
                    startingPoint = data;
                } else if (type.equals("Productions")) {

                    String[] elements = data.split("->");
                    String left = elements[0];
                    String right = elements[1];
                    List<String> productionLeftElement = Arrays.asList(left.split(" "));
                    String[] rightElements = right.split("\\|");
                    for (String rightElement : rightElements) {
                        List<String> productionRightElement = Arrays.asList(rightElement.split(" "));
                        productions.add(new Production(productionLeftElement, productionRightElement));
                    }
                }
            }
            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<Production> getProductionsForNonTerminal(String nonTerminal) {
        return productions.stream().filter(production ->
                production.getLeft().get(0).equals(nonTerminal)
        ).collect(Collectors.toList());
    }

    public boolean checkCFG() {
        AtomicBoolean checker = new AtomicBoolean(true);
        productions.forEach(production -> {
            if (production.getLeft().size() > 1 || !nonTerminals.contains(production.getLeft().get(0))) {
                checker.set(false);
            }
        });
        return checker.get();

    }

    public List<String> getFirstProduction(String nonTerminal) {
        return productions.stream().filter(production -> production.getLeft().size() == 1 && production.getLeft().get(0)
                .equals(nonTerminal)).findFirst().get().getRight();
    }
}
