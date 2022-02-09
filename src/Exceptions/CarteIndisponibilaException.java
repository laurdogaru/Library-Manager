package Exceptions;

public class CarteIndisponibilaException extends Exception {
    public CarteIndisponibilaException() {
        super("Cartea este indisponibila.");
    }
}