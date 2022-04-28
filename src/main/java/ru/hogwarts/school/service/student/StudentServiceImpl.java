package ru.hogwarts.school.service.student;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();

    @Override
    public Student create(Student student) {
        if (student.getId() <= 0) {
            throw new ValidatorException("id у студенты должен быть строго положительным");
        }
        if (studentMap.containsKey(student.getId())) {
            throw new ValidatorException("Студент с таким id уже добавлен");
        }

        return studentMap.put(student.getId(), student);
    }

    @Override
    public Student getById(Long id) {
        if (!studentMap.containsKey(id)) {
            throw new NotFoundException("Студент с таким id не найден");
        }

        return studentMap.get(id);
    }

    @Override
    public List<Student> getByAge(int age) {
        List<Student> result = studentMap.values()
                .stream()
                .filter(faculty -> faculty.getAge() == age)
                .collect(Collectors.toList());

        if (result.size() == 0) {
            throw new NotFoundException("Студенты с таким age не найдены");
        }

        return result;
    }

    @Override
    public Student update(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            throw new NotFoundException("Студент не найден");
        }

        return studentMap.put(student.getId(), student);
    }

    @Override
    public Student delete(Long id) {
        if (!studentMap.containsKey(id)) {
            throw new NotFoundException("Студент с таким id не найден");
        }

        return studentMap.remove(id);
    }

}
