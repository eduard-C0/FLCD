import model.SymbolTable;

public class main {

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        System.out.println(symbolTable.addAndReturnPosition("a"));
        System.out.println(symbolTable.addAndReturnPosition("a"));
        System.out.println(symbolTable.addAndReturnPosition("1"));
        System.out.println(symbolTable.addAndReturnPosition("2"));
        System.out.println(symbolTable.addAndReturnPosition("3"));
        System.out.println(symbolTable.addAndReturnPosition("3"));
        System.out.println(symbolTable.addAndReturnPosition("1"));
        System.out.println(symbolTable.addAndReturnPosition("a"));
    }
}
