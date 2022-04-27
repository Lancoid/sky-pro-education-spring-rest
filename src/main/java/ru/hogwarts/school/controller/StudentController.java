package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.student.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student createdFaculty = studentService.create(student);

        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getById(@PathVariable Long studentId) {
        Student student = studentService.getById(studentId);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @PostMapping("{age}")
    public ResponseEntity<List<Student>> getByAge(@PathVariable int age) {
        List<Student> list = studentService.getByAge(age);

        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(list);
    }

    @PutMapping()
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedFaculty = studentService.update(student.getId(), student);

        return ResponseEntity.ok(updatedFaculty);
    }

}
