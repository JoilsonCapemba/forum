package br.com.alura.forum.models;

import br.com.alura.forum.models.dto.CourseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.GenericDeclaration;
import java.util.UUID;
@Entity(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String  name;

    private String category;

    public Course(CourseDTO courseDTO){
        this.name = courseDTO.name();
        this.category = courseDTO.category();
    }
}
