package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testCreate() {
        String name = "test_student_name_" + (new Date()).getTime();

        Faculty faculty = new Faculty(1L);
        Student student = new Student(name, 12, faculty);

        Student result = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(name);
        Assertions.assertThat(result.getAge()).isEqualTo(12);
        Assertions.assertThat(result.getFaculty().getId()).isEqualTo(faculty.getId());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + result.getId());
    }

    @Test
    public void testUpdate() {
        Faculty faculty = new Faculty(1L);
        Student student = new Student("test_student_name_" + (new Date()).getTime(), 12, faculty);

        student = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        student.setName("test_student_name_" + (new Date()).getTime());
        student.setAge(13);

        Student result = restTemplate.exchange("http://localhost:" + port + "/student", HttpMethod.PUT, new HttpEntity<>(student), Student.class).getBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(student.getName());
        Assertions.assertThat(result.getAge()).isEqualTo(student.getAge());
        Assertions.assertThat(result.getFaculty().getId()).isEqualTo(faculty.getId());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + student.getId());
    }

    @Test
    public void testGetById() {
        Faculty faculty = new Faculty(1L);
        Student student = new Student("test_student_name_" + (new Date()).getTime(), 12, faculty);

        student = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        Student result = restTemplate.getForObject("http://localhost:" + port + "/student/byId?id=" + student.getId(), Student.class);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(student.getName());
        Assertions.assertThat(result.getAge()).isEqualTo(student.getAge());
        Assertions.assertThat(result.getFaculty().getId()).isEqualTo(faculty.getId());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + student.getId());
    }

    @Test
    public void testGetByAge() {
        Faculty faculty = new Faculty(1L);
        Student student = new Student("test_student_name_" + (new Date()).getTime(), 999, faculty);

        student = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<List<Student>> response =
                restTemplate.exchange("http://localhost:" + port + "/student/byAge?age=" + student.getAge(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<Student> result = response.getBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.get(0).getName()).isEqualTo(student.getName());
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(student.getAge());
        Assertions.assertThat(result.get(0).getFaculty().getId()).isEqualTo(faculty.getId());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + student.getId());
    }

    @Test
    public void testGetByAgeBetween() {
        Faculty faculty = new Faculty(1L);
        Student student = new Student("test_student_name_" + (new Date()).getTime(), 999, faculty);

        student = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<List<Student>> response =
                restTemplate.exchange("http://localhost:" + port + "/student/byAgeBetween?minAge=900&maxAge=1000",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<Student> result = response.getBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.get(0).getName()).isEqualTo(student.getName());
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(student.getAge());
        Assertions.assertThat(result.get(0).getFaculty().getId()).isEqualTo(faculty.getId());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + student.getId());
    }

    @Test
    public void testGetAll() {
        Faculty faculty = new Faculty(1L);
        Student student = new Student("test_student_name_" + (new Date()).getTime(), 999, faculty);

        student = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        ResponseEntity<List<Student>> response =
                restTemplate.exchange("http://localhost:" + port + "/student",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<Student> result = response.getBody();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.get(0).getName()).isEqualTo(student.getName());
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(student.getAge());
        Assertions.assertThat(result.get(0).getFaculty().getId()).isEqualTo(faculty.getId());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + student.getId());
    }

    @Test
    public void testGetFaculty() {
        Faculty faculty = new Faculty(1L, "test_faculty_name", "test_faculty_color");
        Student student = new Student("test_student_name_" + (new Date()).getTime(), 999, faculty);

        student = this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);

        Faculty result = this.restTemplate.getForObject("http://localhost:" + port + "/student/getFaculty?id=" + student.getId(), Faculty.class);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(faculty.getId());
        Assertions.assertThat(result.getName()).isEqualTo(faculty.getName());
        Assertions.assertThat(result.getColor()).isEqualTo(faculty.getColor());

        this.restTemplate.delete("http://localhost:" + port + "/student?id=" + student.getId());
    }
}
