package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    boolean existsByNameEqualsAndColorEqualsAllIgnoreCase(@NonNull String name, @NonNull String color);

    boolean existsByNameEqualsIgnoreCaseAndColorEqualsIgnoreCaseAndIdNot(@NonNull String name, @NonNull String color, @NonNull Long id);

    List<Faculty> findByColorEqualsIgnoreCase(@NonNull String color);

    List<Faculty> findByColorEqualsIgnoreCaseOrNameEqualsIgnoreCase(String color, String name);

}
