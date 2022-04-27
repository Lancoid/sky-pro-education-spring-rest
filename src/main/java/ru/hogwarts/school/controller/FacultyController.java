package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.faculty.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.create(faculty);

        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getById(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getById(facultyId);

        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @PostMapping("{color}")
    public ResponseEntity<List<Faculty>> getByColor(@PathVariable String color) {
        List<Faculty> list = facultyService.getByColor(color);

        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(list);
    }

    @PutMapping()
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty.getId(), faculty);

        return ResponseEntity.ok(updatedFaculty);
    }

}
