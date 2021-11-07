package Exceptions;

public class LexicalError extends RuntimeException {
    public LexicalError(String errorMessage){
        super(errorMessage);
    }
}
