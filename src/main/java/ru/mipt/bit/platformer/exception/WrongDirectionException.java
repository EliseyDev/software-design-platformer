package ru.mipt.bit.platformer.exception;

public class WrongDirectionException extends RuntimeException {

    public WrongDirectionException(String message) {
        super(message);
    }
}
