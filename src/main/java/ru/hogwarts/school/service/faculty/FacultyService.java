package ru.hogwarts.school.service.faculty;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty getById(Long id);

    List<Faculty> getByColor(String color);

    Faculty update(Faculty faculty);

    Faculty delete(Long id);

}
