package br.com.alura.forum.controllers;

import br.com.alura.forum.models.Course;
import br.com.alura.forum.models.dto.CourseDTO;
import br.com.alura.forum.repositories.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        var courses = courseRepository.findAll().stream().map(course -> new CourseDTO(course.getName(), course.getCategory())).toList();
        return ResponseEntity.ok(courses);
    }

}
