package ru.hogwarts.school.service.faculty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->create");

        if (facultyRepository.existsByNameEqualsAndColorEqualsAllIgnoreCase(faculty.getName(), faculty.getColor())) {
            throw new ValidatorException("Факультет уже добавлен");
        }

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getById(Long id) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getById");

        if (!facultyRepository.existsById(id)) {
            throw new NotFoundException("Факультет с таким id не найден");
        }

        return facultyRepository.getById(id);
    }

    @Override
    public List<Faculty> getAll() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getAll");

        List<Faculty> result = facultyRepository.findAll();

        if (result.size() == 0) {
            throw new NotFoundException("Факультеты не найдены");
        }

        return result;
    }


    @Override
    public List<Faculty> getByColor(String color) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getByColor");

        List<Faculty> result = facultyRepository.findByColorEqualsIgnoreCase(color);

        if (result.size() == 0) {
            throw new NotFoundException("Факультеты с таким color не найдены");
        }

        return result;
    }

    @Override
    public List<Faculty> getByColorOrName(String color, String name) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getByColorOrName");

        if (color.isEmpty() && name.isEmpty()) {
            throw new ValidatorException("Пустые параметры запроса");
        }

        List<Faculty> result = facultyRepository.findByColorEqualsIgnoreCaseOrNameEqualsIgnoreCase(color, name);

        if (result.size() == 0) {
            throw new NotFoundException("Факультеты с таким color не найдены");
        }

        return result;
    }

    public String getByLongerName() {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->getByLongerName");

        return facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->update");

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
        logger.info("Был вызван метод: " + this.getClass().getSimpleName() + "->delete");

        Faculty faculty = getById(id);

        facultyRepository.deleteById(id);

        return faculty;
    }

}
