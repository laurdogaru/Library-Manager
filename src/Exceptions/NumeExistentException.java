package Exceptions;

public class NumeExistentException extends RuntimeException {
    public NumeExistentException() {
        super("Nume deja existent");
    }
}
