package ru.hogwarts.school.service.faculty;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> facultyMap = new HashMap<>();

    @Override
    public Faculty create(Faculty faculty) {
        if (faculty.getId() <= 0) {
            throw new ValidatorException("id у факультета должен быть строго положительным");
        }
        if (facultyMap.containsKey(faculty.getId())) {
            throw new ValidatorException("Факультет с таким id уже добавлен");
        }

        return facultyMap.put(faculty.getId(), faculty);
    }

    @Override
    public Faculty getById(Long id) {
        if (!facultyMap.containsKey(id)) {
            throw new NotFoundException("Факультет с таким id не найден");
        }

        return facultyMap.get(id);
    }

    @Override
    public List<Faculty> getByColor(String color) {
        List<Faculty> result = facultyMap.values()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());

        if (result.size() == 0) {
            throw new NotFoundException("Факультеты с таким color не найдены");
        }

        return result;
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            throw new NotFoundException("Факультет не найден");
        }

        return facultyMap.put(faculty.getId(), faculty);
    }

    @Override
    public Faculty delete(Long id) {
        if (!facultyMap.containsKey(id)) {
            throw new NotFoundException("Факультет с таким id не найден");
        }

        return facultyMap.remove(id);
    }

}
