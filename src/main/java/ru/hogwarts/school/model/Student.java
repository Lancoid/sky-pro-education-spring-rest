package ru.hogwarts.school.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;
}
