package ru.hogwarts.school.service.student;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student getById(Long id);

    List<Student> getByAge(int age);

    Student update(Student student);

    Student delete(Long id);

}
