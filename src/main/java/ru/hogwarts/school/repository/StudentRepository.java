package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByNameEqualsIgnoreCaseAndAgeEquals(@NonNull String name, int age);

    boolean existsByNameEqualsIgnoreCaseAndAgeEqualsAndIdNot(@NonNull String name, int age, Long id);

    List<Student> findByAgeEquals(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT COUNT(id) FROM student", nativeQuery = true)
    int getStudentsCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int getStudentsAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY ID DESC LIMIT :count", nativeQuery = true)
    List<Student> getLastStudents(@Param("count") int count);
}
