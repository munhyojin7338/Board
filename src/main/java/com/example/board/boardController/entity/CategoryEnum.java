package com.example.board.boardController.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CategoryEnum {

    FOOTBALL("축구"),
    BASKETBALL("농구"),
    VOLLEYBALL("배구"),
    TENNIS("테니스");

    private final String name;

}
