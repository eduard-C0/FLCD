package parser;

import grammar.Grammar;
import grammar.Production;

import java.util.*;

public class Parser {
    public String STANDARD_STATE = "STANDARD";
    public String BACK_STATE = "BACK";
    public String FINAL_STATE = "FINAL";
    public String ERROR_STATE = "ERROR";

    private int index;
    private Deque<GrammarElement> todoStack = new LinkedList<>();
    private Deque<GrammarElement> inputStack = new LinkedList<>();
    private Grammar grammar;
    private String currentState;
    private List<String> derivationString = new ArrayList<>();


    public Parser(Grammar grammar) {
        this.index = 0;
        this.grammar = grammar;
        this.currentState = STANDARD_STATE;
        this.inputStack.add(new GrammarElement(grammar.getStartingPoint(), "S", 0));
        this.derivationString.add(grammar.getStartingPoint());
    }

    public void expand() {
        GrammarElement nonTerminalElement = inputStack.pop();
        List<String> firstProduction = grammar.getFirstProduction(nonTerminalElement.getElement());
        nonTerminalElement.setIndexOfTheElement(0);
        todoStack.addLast(nonTerminalElement);
        for (int i = firstProduction.size() - 1; i >= 0; i--) {
            inputStack.addFirst(new GrammarElement(firstProduction.get(i), nonTerminalElement.getElement(), 0));
        }
        System.out.println("expand"+this.toString());

        String lastDerivationElement = derivationString.get(derivationString.size() - 1);
        StringBuilder stringBuilder = new StringBuilder();
        grammar.getFirstProduction(nonTerminalElement.getElement()).forEach(s -> stringBuilder.append(s));
        String productionToAdd = lastDerivationElement.replaceFirst(nonTerminalElement.getElement(),stringBuilder.toString());
        derivationString.add(productionToAdd);
    }

    public void success() {
        this.currentState = FINAL_STATE;
        System.out.println("success"+this.toString());
    }

    public void insuccess() {
        this.currentState = BACK_STATE;
        System.out.println("insuccess"+this.toString());

    }

    public void advance() {
        index++;
        GrammarElement grammarElement = inputStack.removeFirst();
        todoStack.addLast(grammarElement);
        System.out.println("advance"+this.toString());
    }

    public void back() {
        this.currentState = BACK_STATE;
        index--;
        GrammarElement nonTerminalElement = todoStack.getLast();
        todoStack.removeLast();
        inputStack.addFirst(nonTerminalElement);
        System.out.println("back"+this.toString());
    }

    public void tryAgain() {
        GrammarElement grammarElement = todoStack.getLast();
        int indexOfElement = grammarElement.getIndexOfTheElement() + 1;
        List<Production> productionsForNonTerminals = grammar.getProductionsForNonTerminal(grammarElement.getElement());

        if (indexOfElement >= productionsForNonTerminals.size()) {
            if (index == 0 && grammarElement.getElement().equals(grammar.getStartingPoint())) {
                boolean flag = true;
                currentState = ERROR_STATE;
                todoStack.removeLast();
                while (flag) {
                    if (inputStack.isEmpty()) {
                        flag = false;
                    } else {
                        GrammarElement element = inputStack.getFirst();
                        if (element.getNonTerminalElement().equals(grammarElement.getElement()) && element.getIndexOfTheElement() == grammarElement.getIndexOfTheElement()) {
                            inputStack.removeFirst();
                        } else {
                            flag = false;
                        }
                    }
                }
            } else {
                currentState = BACK_STATE;
                boolean flag = true;
                todoStack.removeLast();

                while (flag) {
                    if (inputStack.isEmpty()) {
                        flag = false;
                    } else {
                        GrammarElement element = inputStack.getFirst();
                        if (element.getNonTerminalElement().equals(grammarElement.getElement()) && element.getIndexOfTheElement() == grammarElement.getIndexOfTheElement()) {
                            inputStack.removeFirst();
                        } else {
                            flag = false;
                        }
                    }
                }
                inputStack.addFirst(grammarElement);
            }
        } else {
            currentState = STANDARD_STATE;
            GrammarElement element = todoStack.removeLast();
            element.setIndexOfTheElement(indexOfElement);
            todoStack.addLast(element);
            boolean flag = true;

            while (flag) {
                if (inputStack.isEmpty()) {
                    flag = false;
                } else {
                    GrammarElement inputElement = inputStack.getFirst();
                    if (inputElement.getNonTerminalElement().equals(grammarElement.getElement()) && inputElement.getIndexOfTheElement() == grammarElement.getIndexOfTheElement()) {
                        inputStack.removeFirst();
                    } else {
                        flag = false;
                    }
                }
            }
            Production production = productionsForNonTerminals.get(indexOfElement);

            for (int i = production.getRight().size() - 1; i >= 0; i--) {
                inputStack.addFirst(new GrammarElement(production.getRight().get(i), grammarElement.getElement(), indexOfElement));
            }

            derivationString.remove(derivationString.size() - 1);
            StringBuilder stringBuilder = new StringBuilder();
            production.getRight().forEach(s -> stringBuilder.append(s));
            String productionToString = stringBuilder.toString();
            String lastElement = derivationString.get(derivationString.size() - 1);
            String productionToAdd = lastElement.replaceFirst(grammarElement.getElement(),productionToString);
            derivationString.add(productionToAdd);
        }
        System.out.println("try again"+this.toString());

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Deque<GrammarElement> getTodoStack() {
        return todoStack;
    }

    public void setTodoStack(Deque<GrammarElement> todoStack) {
        this.todoStack = todoStack;
    }

    public Deque<GrammarElement> getInputStack() {
        return inputStack;
    }

    public void setInputStack(Deque<GrammarElement> inputStack) {
        this.inputStack = inputStack;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public List<String> getDerivationString() {
        return derivationString;
    }

    public void setDerivationString(List<String> derivationString) {
        this.derivationString = derivationString;
    }

    @Override
    public String toString() {
        return "Parser{" +
                ", currentState='" + currentState +
                "index=" + index +
                ", todoStack=" + todoStack +
                ", inputStack=" + inputStack + '\'' +
                '}';
    }
}
