package ru.hogwarts.school.service.faculty;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long generatedFacultyId = 1L;

    @Override
    public Faculty create(Faculty faculty) {
        facultyMap.put(generatedFacultyId, faculty);
        generatedFacultyId++;
        return faculty;
    }

    @Override
    public Faculty getById(Long facultyId) {
        return facultyMap.get(facultyId);
    }

    @Override
    public List<Faculty> getByColor(String color) {

        return facultyMap.values()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Faculty update(Long facultyId, Faculty faculty) {
        facultyMap.put(facultyId, faculty);

        return faculty;
    }

    @Override
    public Faculty delete(Long facultyId) {
        return facultyMap.remove(facultyId);
    }

}
