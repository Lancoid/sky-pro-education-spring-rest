package ru.hogwarts.school.service.student;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.exception.ValidatorException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        if (studentRepository.existsByNameEqualsIgnoreCaseAndAgeEquals(student.getName(), student.getAge())) {
            throw new ValidatorException("Студент уже добавлен");
        }

        return studentRepository.save(student);
    }

    @Override
    public Student getById(Long id) {
        if (studentRepository.existsById(id)) {
            throw new NotFoundException("Студент с таким id не найден");
        }

        return studentRepository.getById(id);
    }

    @Override
    public List<Student> getByAge(int age) {
        List<Student> result = studentRepository.findByAgeEquals(age);

        if (result.size() == 0) {
            throw new NotFoundException("Студенты с таким age не найдены");
        }

        return result;
    }

    @Override
    public Student update(Student student) {
        if (studentRepository.existsById(student.getId())) {
            throw new NotFoundException("Студент с таким id не найден");
        }

        if (studentRepository.existsByNameEqualsIgnoreCaseAndAgeEqualsAndIdNot(student.getName(), student.getAge(), student.getId())) {
            throw new ValidatorException("Студент уже добавлен c другим id");
        }

        return studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        Student student = getById(id);

        studentRepository.deleteById(id);

        return student;
    }

}
