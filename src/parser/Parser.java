package parser;

import grammar.Grammar;
import grammar.Production;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private static final String STANDARD_STATE = "STANDARD";
    private static final String BACK_STATE = "BACK";
    private static final String FINAL_STATE = "FINAL";
    private static final String ERROR_STATE = "ERROR";

    private int index;
    private Deque<GrammarElement> todoStack = new LinkedList<>();
    private Deque<GrammarElement> inputStack = new LinkedList<>();
    private Grammar grammar;
    private String currentState;


    public Parser(Grammar grammar) {
        this.index = 0;
        this.grammar = grammar;
        this.currentState = STANDARD_STATE;
        this.inputStack.add(new GrammarElement(grammar.getStartingPoint(), "", 0));
    }

    private void expand() {
        GrammarElement nonTerminalElement = inputStack.pop();
        List<String> firstProduction = grammar.getFirstProduction(nonTerminalElement.getElement());
        nonTerminalElement.setIndexOfTheElement(0);
        todoStack.add(nonTerminalElement);
        firstProduction.stream()
                .sorted(Collections.reverseOrder())
                .forEach(production -> inputStack.addFirst(new GrammarElement(production, nonTerminalElement.getElement(), 0)));
    }

    private void success() {
        this.currentState = FINAL_STATE;
    }

    private void insuccess() {
        this.currentState = BACK_STATE;
    }

    private void advance() {
        index++;
        GrammarElement grammarElement = inputStack.pop();
        todoStack.addFirst(grammarElement);
    }

    private void back() {
        index--;
        GrammarElement nonTerminalElement = todoStack.pop();
        inputStack.addFirst(nonTerminalElement);
    }

    private void tryAgain() {
        GrammarElement grammarElement = todoStack.getLast();
        int indexOfElement = grammarElement.getIndexOfTheElement() + 1;
        List<Production> productionsForNonTerminals = grammar.getProductionsForNonTerminal(grammarElement.getElement());

        if (indexOfElement >= productionsForNonTerminals.size()) {
            if (index == 0 && grammarElement.getElement().equals(grammar.getStartingPoint())) {
                boolean flag = true;
                currentState = ERROR_STATE;
                while (flag) {
                    if (inputStack.isEmpty()) {
                        flag = false;
                    } else {
                        GrammarElement element = inputStack.getFirst();
                        if (element.getNonTerminalElement().equals(element.getElement()) && element.getIndexOfTheElement() == grammarElement.getIndexOfTheElement()) {
                            inputStack.removeFirst();
                        } else {
                            flag = false;
                        }
                    }
                }
                todoStack.removeLast();
            } else {
                currentState = BACK_STATE;
                boolean flag = true;

                while (flag) {
                    if (inputStack.isEmpty()) {
                        flag = false;
                    } else {
                        GrammarElement element = inputStack.getFirst();
                        if (element.getNonTerminalElement().equals(element.getElement()) && element.getIndexOfTheElement() == grammarElement.getIndexOfTheElement()) {
                            inputStack.removeFirst();
                        } else {
                            flag = false;
                        }
                    }
                }
                todoStack.removeLast();
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
                    if (inputElement.getNonTerminalElement().equals(grammarElement.getElement()) && inputElement.getIndexOfTheElement() == index - 1) {
                        inputStack.removeFirst();
                    } else {
                        flag = false;
                    }
                }
            }
            Production production = productionsForNonTerminals.get(index);

            production.getRight().stream()
                    .sorted(Collections.reverseOrder())
                    .forEach(p -> inputStack.addFirst(new GrammarElement(p, element.getElement(), indexOfElement)));
        }

    }

}
