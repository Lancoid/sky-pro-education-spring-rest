package ru.hogwarts.school.service.student;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student getById(Long id);

    List<Student> getAll();

    List<Student> getByAge(int age);

    List<Student> getByAgeBetween(int minAge, int maxAge);

    List<String> getByFirstLetter(char firstLetter);

    List<Student> getLastStudents(int count);

    int getStudentsCount();

    float getStudentsAverageAge();

    Student update(Student student);

    Student delete(Long id);

    Faculty getFacultyById(Long id);

}
