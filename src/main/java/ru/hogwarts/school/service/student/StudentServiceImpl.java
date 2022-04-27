package ru.hogwarts.school.service.student;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();
    private Long generatedFacultyId = 1L;

    @Override
    public Student create(Student student) {
        studentMap.put(generatedFacultyId, student);
        generatedFacultyId++;
        return student;
    }

    @Override
    public Student getById(Long studentId) {
        return studentMap.get(studentId);
    }

    @Override
    public List<Student> getByAge(int age) {

        return studentMap.values()
                .stream()
                .filter(faculty -> faculty.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public Student update(Long facultyId, Student student) {
        studentMap.put(facultyId, student);

        return student;
    }

    @Override
    public Student delete(Long facultyId) {
        return studentMap.remove(facultyId);
    }

}
