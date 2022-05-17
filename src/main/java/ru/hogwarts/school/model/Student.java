package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;

    @ManyToOne()
    @JoinColumn(name = "faculty_id")
    @JsonProperty
    private Faculty faculty;

    @OneToOne(mappedBy = "student")
    @PrimaryKeyJoinColumn
    @JsonBackReference
    private Avatar avatar;

    public Student(String name, int age, Faculty faculty) {
        this.name = name;
        this.age = age;
        this.faculty = faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;

        return age == student.age && id.equals(student.id) && name.equals(student.name) && faculty.equals(student.faculty) && avatar.equals(student.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, faculty, avatar);
    }
}
