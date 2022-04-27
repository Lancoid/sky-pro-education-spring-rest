package ru.hogwarts.school.model;

import lombok.Getter;

@Getter
public class Student {

    private final Long id = null;
    private final String name;
    private final int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

}
