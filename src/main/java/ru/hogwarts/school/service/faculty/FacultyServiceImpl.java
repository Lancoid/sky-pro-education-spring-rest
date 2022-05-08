package ru.hogwarts.school.service.faculty;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        if (facultyRepository.existsByNameEqualsAndColorEqualsAllIgnoreCase(faculty.getName(), faculty.getColor())) {
            throw new ValidatorException("Факультет уже добавлен");
        }

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getById(Long id) {
        if (facultyRepository.existsById(id)) {
            throw new NotFoundException("Факультет с таким id не найден");
        }

        return facultyRepository.getById(id);
    }

    @Override
    public List<Faculty> getByColor(String color) {
        List<Faculty> result = facultyRepository.findByColorEqualsIgnoreCase(color);

        if (result.size() == 0) {
            throw new NotFoundException("Факультеты с таким color не найдены");
        }

        return result;
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (facultyRepository.existsById(faculty.getId())) {
            throw new NotFoundException("Факультет с таким id не найден");
        }

        if (facultyRepository.existsByNameEqualsIgnoreCaseAndColorEqualsIgnoreCaseAndIdNot(faculty.getName(), faculty.getColor(), faculty.getId())) {
            throw new ValidatorException("Факультет уже добавлен c другим id");
        }

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(Long id) {
        Faculty faculty = getById(id);

        facultyRepository.deleteById(id);

        return faculty;
    }

}
