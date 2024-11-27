package br.com.alura.forum.controllers;

import br.com.alura.forum.models.Course;
import br.com.alura.forum.models.dto.CourseDTO;
import br.com.alura.forum.repositories.CourseRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CursoController {

    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/create")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseDTO courseDTO){
        Course newCourse = new Course(courseDTO);
        this.courseRepository.save(newCourse);

        return ResponseEntity.ok(courseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id){
        Optional<Course> course = this.courseRepository.findById(id);
        if (course.isPresent()){
            CourseDTO courseDTO = new CourseDTO(course.get().getName(), course.get().getCategory());
            return ResponseEntity.ok(courseDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Page<CourseDTO>> getAllCourses(@PageableDefault(direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable){
        Page<CourseDTO> courses;
        courses = courseRepository.findAll(pageable).map(course ->  new CourseDTO(course.getName(), course.getCategory()));
        System.out.println("Teste "+courses);
        return ResponseEntity.ok(courses);
    }

}
