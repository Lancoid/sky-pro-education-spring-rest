package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "create",
            description = "Create new Faculty",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Faculty.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка добавления нового факультета"),
            })
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.create(faculty));
    }

    @GetMapping("{id}")
    @Operation(summary = "getById",
            description = "Get one Faculty by id",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Faculty.class))),
                    @ApiResponse(responseCode = "404", description = "Факультет не найден"),
            })
    public ResponseEntity<Faculty> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getById(id));
    }

    @GetMapping("/")
    @Operation(summary = "getByColor",
            description = "Get Faculties by color",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Faculty.class)))),
                    @ApiResponse(responseCode = "404", description = "Факультеты не найдены"),
            })
    public ResponseEntity<List<Faculty>> getByColor(@RequestParam("color") String color) {
        return ResponseEntity.ok(facultyService.getByColor(color));
    }

    @PutMapping()
    @Operation(summary = "update",
            description = "Update existed Faculty",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = Faculty.class))),
                    @ApiResponse(responseCode = "404", description = "Факультет не найден"),
            })
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.update(faculty));
    }

}
