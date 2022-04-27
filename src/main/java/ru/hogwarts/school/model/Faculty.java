package ru.hogwarts.school.model;

import lombok.Getter;

@Getter
public class Faculty {

    private final Long id = null;
    private final String name;
    private final String color;

    public Faculty(String name, String color) {
        this.name = name;
        this.color = color;
    }

}
