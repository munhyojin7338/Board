package com.example.board.memberController.exception;

public class NotMatchPasswordException extends RuntimeException {
    public NotMatchPasswordException(String s) {
        super(s);
    }
}
