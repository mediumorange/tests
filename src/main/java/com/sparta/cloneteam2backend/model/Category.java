package com.sparta.cloneteam2backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum Category {
    PENSION("펜션"),
    APARTMENT("아파트"),
    ISLAND("섬"),
    ETC("기타");
    private final String category;
}
