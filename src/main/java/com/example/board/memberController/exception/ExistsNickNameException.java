package com.example.board.memberController.exception;

public class ExistsNickNameException extends RuntimeException {
    public ExistsNickNameException(String s) {
        super(s);
    }
}
