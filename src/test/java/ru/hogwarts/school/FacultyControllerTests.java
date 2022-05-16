package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.faculty.FacultyServiceImpl;
import ru.hogwarts.school.service.student.StudentServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@WebMvcTest
class FacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private FacultyController facultyController;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void saveFacultyCreateTest() throws Exception {
        Long id = 1L;
        String name = "test_faculty_name";
        String color = "test_faculty_color";

        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsByNameEqualsAndColorEqualsAllIgnoreCase(any(String.class), any(String.class))).thenReturn(false);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void saveFacultyUpdateTest() throws Exception {
        Long id = 1L;
        String name = "test_faculty_name";
        String color = "test_faculty_color";

        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.existsByNameEqualsAndColorEqualsAllIgnoreCase(any(String.class), any(String.class))).thenReturn(false);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(object.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void saveFacultyGetAllTest() throws Exception {
        List<Faculty> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Faculty faculty = new Faculty();
            faculty.setId((long) i);
            faculty.setName("test_faculty_name" + i);
            faculty.setColor("test_faculty_color" + i);

            list.add(faculty);
        }

        when(facultyRepository.findAll()).thenReturn(list);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        for (int i = 0; i < 5; i++) {
            result.andExpect(jsonPath("$[" + i + "].id").value((long) i))
                    .andExpect(jsonPath("$[" + i + "].name").value("test_faculty_name" + i))
                    .andExpect(jsonPath("$[" + i + "].color").value("test_faculty_color" + i));
        }
    }

    @Test
    public void saveFacultyGetByIdTest() throws Exception {
        Long id = 1L;
        String name = "test_faculty_name";
        String color = "test_faculty_color";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.getById(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byId?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void saveFacultyGetByColorTest() throws Exception {
        Long id = 1L;
        String name = "test_faculty_name";
        String color = "test_faculty_color";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.findByColorEqualsIgnoreCase(any(String.class))).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byColor?color=" + color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @Test
    public void saveFacultyGetByColorOrNameTest() throws Exception {
        Long id = 1L;
        String name = "test_faculty_name";
        String color = "test_faculty_color";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.findByColorEqualsIgnoreCaseOrNameEqualsIgnoreCase(any(String.class), any(String.class))).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byColorOrName?color=" + color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byColorOrName?name=" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byColorOrName?color=" + color + "&name=" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @Test
    public void saveFacultyGetStudentsTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("test_faculty_name");
        faculty.setColor("test_faculty_color");

        Set<Student> set = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.setId((long) i + 1);
            student.setName("test_student_name" + i);
            student.setAge(i + 10);
            student.setFaculty(faculty);

            set.add(student);
        }

        faculty.setStudents(set);

        when(facultyRepository.existsById(any(Long.class))).thenReturn(true);
        when(facultyRepository.getById(any(Long.class))).thenReturn(faculty);

        String result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudents?id=" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        for (Student student : set) {
            String expectedString = "\"id\":" + student.getId() + ",\"name\":\"" + student.getName() + "\",\"age\":" + student.getAge();
            Assertions.assertTrue(result.contains(expectedString));
        }
    }

}
