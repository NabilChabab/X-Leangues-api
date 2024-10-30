package com.example.x_leagues.exceptions;

public class CompetitionAlreadyExistException extends RuntimeException {
    public CompetitionAlreadyExistException(String message) {
        super(message);
    }
}
