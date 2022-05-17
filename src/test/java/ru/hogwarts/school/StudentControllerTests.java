package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.config.TestProfileJPAConfig;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@SpringBootTest(classes = {
        DemoApplication.class,
        TestProfileJPAConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Faculty faculty;
    private Student student;

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @BeforeEach
    public void beforeTest() {
        faculty = facultyRepository.save(new Faculty(1L, "test_faculty_name", "test_faculty_color"));
        student = new Student("test_student_name", 12, faculty);
    }

    @AfterEach
    public void afterTest() {
        faculty = null;
        student = null;

        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void testCreate() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        Assertions.assertThat(createdStudent).isNotNull();
        Assertions.assertThat(createdStudent.getId()).isNotNull();
        Assertions.assertThat(createdStudent.getName()).isEqualTo(student.getName());
        Assertions.assertThat(createdStudent.getAge()).isEqualTo(student.getAge());
        Assertions.assertThat(createdStudent.getFaculty().getId()).isEqualTo(student.getFaculty().getId());
    }

    @Test
    public void testUpdate() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        createdStudent.setName("updated_student_name");
        createdStudent.setAge(13);

        Student updatedStudent = restTemplate.exchange(
                "http://localhost:" + port + "/student", HttpMethod.PUT,
                new HttpEntity<>(createdStudent),
                Student.class
        ).getBody();

        Assertions.assertThat(updatedStudent).isNotNull();
        Assertions.assertThat(updatedStudent.getId()).isEqualTo(createdStudent.getId());
        Assertions.assertThat(updatedStudent.getName()).isEqualTo(createdStudent.getName());
        Assertions.assertThat(updatedStudent.getAge()).isEqualTo(createdStudent.getAge());
        Assertions.assertThat(updatedStudent.getFaculty()).isEqualTo(createdStudent.getFaculty());
    }

    @Test
    public void testGetById() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        Student foundedStudent = restTemplate.getForObject(
                "http://localhost:" + port + "/student/byId?id=" + createdStudent.getId(),
                Student.class
        );

        Assertions.assertThat(foundedStudent).isNotNull();
        Assertions.assertThat(foundedStudent.getId()).isEqualTo(createdStudent.getId());
        Assertions.assertThat(foundedStudent.getName()).isEqualTo(createdStudent.getName());
        Assertions.assertThat(foundedStudent.getAge()).isEqualTo(createdStudent.getAge());
        Assertions.assertThat(foundedStudent.getFaculty()).isEqualTo(createdStudent.getFaculty());
    }

    @Test
    public void testGetByAge() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        ResponseEntity<List<Student>> response =
                restTemplate.exchange("http://localhost:" + port + "/student/byAge?age=" + createdStudent.getAge(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<Student> studentList = response.getBody();

        Assertions.assertThat(studentList).isNotNull();
        Assertions.assertThat(studentList.get(0).getId()).isEqualTo(createdStudent.getId());
        Assertions.assertThat(studentList.get(0).getName()).isEqualTo(createdStudent.getName());
        Assertions.assertThat(studentList.get(0).getAge()).isEqualTo(createdStudent.getAge());
        Assertions.assertThat(studentList.get(0).getFaculty()).isEqualTo(createdStudent.getFaculty());
    }

    @Test
    public void testGetByAgeBetween() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        ResponseEntity<List<Student>> response =
                restTemplate.exchange("http://localhost:" + port + "/student/byAgeBetween?minAge=10&maxAge=15",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<Student> result = response.getBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.get(0).getId()).isEqualTo(createdStudent.getId());
        Assertions.assertThat(result.get(0).getName()).isEqualTo(createdStudent.getName());
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(createdStudent.getAge());
        Assertions.assertThat(result.get(0).getFaculty()).isEqualTo(createdStudent.getFaculty());
    }

    @Test
    public void testGetAll() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        ResponseEntity<List<Student>> response =
                restTemplate.exchange("http://localhost:" + port + "/student",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<Student> studentList = response.getBody();

        Assertions.assertThat(studentList).isNotEmpty();
        Assertions.assertThat(studentList.get(0).getId()).isEqualTo(createdStudent.getId());
        Assertions.assertThat(studentList.get(0).getName()).isEqualTo(createdStudent.getName());
        Assertions.assertThat(studentList.get(0).getAge()).isEqualTo(createdStudent.getAge());
        Assertions.assertThat(studentList.get(0).getFaculty()).isEqualTo(createdStudent.getFaculty());
    }

    @Test
    public void testGetFaculty() {
        Student createdStudent = this.restTemplate.postForObject(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        Faculty studentFaculty = this.restTemplate.getForObject(
                "http://localhost:" + port + "/student/getFaculty?id=" + createdStudent.getId(),
                Faculty.class
        );

        Assertions.assertThat(studentFaculty).isNotNull();
        Assertions.assertThat(studentFaculty.getId()).isEqualTo(createdStudent.getFaculty().getId());
        Assertions.assertThat(studentFaculty.getName()).isEqualTo(createdStudent.getFaculty().getName());
        Assertions.assertThat(studentFaculty.getColor()).isEqualTo(createdStudent.getFaculty().getColor());
    }
}
