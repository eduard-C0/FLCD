package parser;

public class GrammarElement {
    private String element;
    private String nonTerminalElement;
    private int indexOfTheElement;


    public GrammarElement(String element, String nonTerminalElement, int indexOfTheElement) {
        this.element = element;
        this.nonTerminalElement = nonTerminalElement;
        this.indexOfTheElement = indexOfTheElement;
    }


    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getNonTerminalElement() {
        return nonTerminalElement;
    }

    public void setNonTerminalElement(String nonTerminalElement) {
        this.nonTerminalElement = nonTerminalElement;
    }

    public int getIndexOfTheElement() {
        return indexOfTheElement;
    }

    public void setIndexOfTheElement(int indexOfTheElement) {
        this.indexOfTheElement = indexOfTheElement;
    }
}
