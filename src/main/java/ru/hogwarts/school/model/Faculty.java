package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Faculty {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonBackReference
    private Set<Student> students;

    public Faculty(Long id) {
        this.id = id;
    }

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
