import Exceptions.LexicalError;
import model.SymbolTable;
import scanner.ScannerFile;

public class main {

    public static void main(String[] args) {
        ScannerFile scannerFile = new ScannerFile("src/p1.in");
        try {
            scannerFile.readFromProgramFile();
        }catch (LexicalError lexicalError){
            lexicalError.printStackTrace();
        }
    }
}
