package parser;

import grammar.Grammar;
import grammar.Production;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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

    public void success() {
        this.currentState = FINAL_STATE;
    }

    public void insuccess() {
        this.currentState = BACK_STATE;
    }

    public void back() {
        this.index--;
        inputStack.addFirst(todoStack.removeLast());
        this.currentState = BACK_STATE;
    }

    public void advance() {
        this.index++;
        this.todoStack.addLast(inputStack.removeFirst());
        this.currentState = STANDARD_STATE;
    }

    public void expand() {
        this.currentState = STANDARD_STATE;
        GrammarElement element = inputStack.removeFirst();
        element.setIndexOfTheElement(0);
        todoStack.addLast(element);
        List<String> firstProductionForElement = grammar.getFirstProduction(element.getElement());
        for (int i = firstProductionForElement.size() - 1; i >= 0; i--) {
            inputStack.addFirst(new GrammarElement(firstProductionForElement.get(i), element.getElement(), 0));
        }

        String lastDerivationElement = derivationString.get(derivationString.size() - 1);
        StringBuilder stringBuilder = new StringBuilder();
        grammar.getFirstProduction(element.getElement()).forEach(s -> stringBuilder.append(s));
        String productionToAdd = lastDerivationElement.replaceFirst(element.getElement(),stringBuilder.toString());
        derivationString.add(productionToAdd);
    }

    public void tryAgain() {
        GrammarElement grammarElement = todoStack.removeLast();
        int indexOfElement = grammarElement.getIndexOfTheElement();
        List<Production> productionsForElement = grammar.getProductionsForNonTerminal(grammarElement.getElement());

        if (indexOfElement < (productionsForElement.size()-1)) {
            this.currentState = STANDARD_STATE;
            grammarElement.setIndexOfTheElement(indexOfElement + 1);
            todoStack.addLast(grammarElement);
            List<String> productions = grammar.getProductionsForNonTerminal(grammarElement.getElement()).get(indexOfElement + 1).getRight();
            boolean flag = true;
            while (flag) {
                if (inputStack.isEmpty()) {
                    flag = false;
                } else {
                    GrammarElement grammarElementFromInputStack = inputStack.getFirst();
                    if (grammarElementFromInputStack.getNonTerminalElement().equals(grammarElement.getElement())
                            && grammarElementFromInputStack.getIndexOfTheElement() == indexOfElement) {
                        inputStack.removeFirst();
                    }
                    else
                    {
                        flag = false;
                    }
                }
            }
            for (int i = productions.size() - 1; i >= 0; i--){
                inputStack.addFirst(new GrammarElement(productions.get(i),grammarElement.getElement(),indexOfElement + 1));
            }
            derivationString.remove(derivationString.size() - 1);
            StringBuilder stringBuilder = new StringBuilder();
            productions.forEach(s -> stringBuilder.append(s));
            String productionToString = stringBuilder.toString();
            String lastElement = derivationString.get(derivationString.size() - 1);
            String productionToAdd = lastElement.replaceFirst(grammarElement.getElement(),productionToString);
            derivationString.add(productionToAdd);
        }
        else{
            if (this.index == 0 && grammarElement.getElement().equals(grammar.getStartingPoint())) {
                this.currentState = ERROR_STATE;
                return;
            }
            this.currentState = BACK_STATE;
            boolean flag = true;
            while (flag) {
                if(inputStack.isEmpty()){
                    flag = false;
                }else {
                    GrammarElement grammarElementFromInputStack = inputStack.getFirst();
                    if(grammarElementFromInputStack.getNonTerminalElement().equals(grammarElement.getElement())
                            && grammarElementFromInputStack.getIndexOfTheElement() == indexOfElement){
                        inputStack.removeFirst();
                    }
                    else {
                        flag = false;
                    }
                }
            }
            inputStack.addFirst(grammarElement);
        }
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
