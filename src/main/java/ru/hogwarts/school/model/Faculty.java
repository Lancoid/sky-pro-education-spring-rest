package ru.hogwarts.school.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class Faculty {

    private Long id;
    private String name;
    private String color;

}
