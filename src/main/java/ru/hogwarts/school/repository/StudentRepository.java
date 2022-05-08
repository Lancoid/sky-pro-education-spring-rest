package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByNameEqualsIgnoreCaseAndAgeEquals(@NonNull String name, int age);

    boolean existsByNameEqualsIgnoreCaseAndAgeEqualsAndIdNot(@NonNull String name, int age, Long id);

    List<Student> findByAgeEquals(int age);
}
