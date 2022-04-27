package ru.hogwarts.school.service.faculty;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty getById(Long facultyId);

    List<Faculty> getByColor(String color);

    Faculty update(Long facultyId, Faculty faculty);

    Faculty delete(Long facultyId);

}
