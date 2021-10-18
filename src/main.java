import model.SymbolTable;

public class main {

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        System.out.println(symbolTable.getPosition("a"));
        System.out.println(symbolTable.getPosition("a"));
        System.out.println(symbolTable.getPosition("1"));
        System.out.println(symbolTable.getPosition("2"));
        System.out.println(symbolTable.getPosition("3"));
        System.out.println(symbolTable.getPosition("3"));
        System.out.println(symbolTable.getPosition("1"));
        System.out.println(symbolTable.getPosition("a"));
    }
}
