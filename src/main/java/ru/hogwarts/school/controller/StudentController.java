package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    @Operation(summary = "create",
            description = "Create new Student",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка добавления нового студента"),
            })
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.create(student));
    }

    @PutMapping
    @Operation(summary = "update",
            description = "Update existed Student",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
                    @ApiResponse(responseCode = "404", description = "Студент не найден"),
            })
    public ResponseEntity<Student> update(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(student));
    }

    @DeleteMapping
    @Operation(summary = "delete",
            description = "delete existed Student",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
                    @ApiResponse(responseCode = "404", description = "Студент не найден"),
            })
    public ResponseEntity<Student> delete(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(studentService.delete(id));
    }

    @GetMapping()
    @Operation(summary = "getAll",
            description = "Get all Students",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
                    @ApiResponse(responseCode = "404", description = "Студенты не найдены"),
            })
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping(path = "byId")
    @Operation(summary = "getById",
            description = "Get Student by id",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
                    @ApiResponse(responseCode = "404", description = "Студент не найден"),
            })
    public ResponseEntity<Student> getById(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping(path = "byAge")
    @Operation(summary = "getByAge",
            description = "Get Students by age",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса неправильные"),
                    @ApiResponse(responseCode = "404", description = "Студенты не найдены"),
            })
    public ResponseEntity<List<Student>> getByAge(
            @RequestParam(value = "age") int age
    ) {
        return ResponseEntity.ok(studentService.getByAge(age));
    }

    @GetMapping(path = "byAgeBetween")
    @Operation(summary = "getByAgeBetween",
            description = "Get Students by age between",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса неправильные"),
                    @ApiResponse(responseCode = "404", description = "Студенты не найдены"),
            })
    public ResponseEntity<List<Student>> getByAgeBetween(
            @RequestParam(value = "minAge") int minAge,
            @RequestParam(value = "maxAge") int maxAge
    ) {
        return ResponseEntity.ok(studentService.getByAgeBetween(minAge, maxAge));
    }

    @GetMapping(path = "byFirstLetter")
    @Operation(summary = "getByFirstLetter",
            description = "Get Students names by first letter",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса неправильные"),
                    @ApiResponse(responseCode = "404", description = "Имена не найдены"),
            })
    public ResponseEntity<List<String>> getByFirstLetter(
            @RequestParam(value = "firstLetter") char firstLetter
    ) {
        return ResponseEntity.ok(studentService.getByFirstLetter(firstLetter));
    }

    @GetMapping(path = "getFaculty")
    @Operation(summary = "getFaculty",
            description = "Get student's faculty",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))),
                    @ApiResponse(responseCode = "404", description = "Студент не найден"),
            })
    public ResponseEntity<Faculty> getFaculty(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(studentService.getById(id).getFaculty());
    }

    @GetMapping(path = "getCount")
    @Operation(summary = "getCount",
            description = "Get student's count",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
            })
    public ResponseEntity<Integer> getCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping(path = "getAverageAge")
    @Operation(summary = "getAverageAge",
            description = "Get student's average age",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Float.class)))
            })
    public ResponseEntity<Float> getAverageAge() {
        return ResponseEntity.ok(studentService.getStudentsAverageAge());
    }

    @GetMapping(path = "getLastStudents")
    @Operation(summary = "getLastStudents",
            description = "Get last Students by count",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Student.class)))),
                    @ApiResponse(responseCode = "400", description = "Параметры запроса неправильные"),
                    @ApiResponse(responseCode = "404", description = "Студенты не найдены"),
            })
    public ResponseEntity<List<Student>> getLastStudents(
            @RequestParam(value = "count") int count
    ) {
        return ResponseEntity.ok(studentService.getLastStudents(count));
    }

}
