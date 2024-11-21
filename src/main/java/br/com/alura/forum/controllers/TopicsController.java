package br.com.alura.forum.controllers;

import br.com.alura.forum.models.Course;
import br.com.alura.forum.models.Topic;
import br.com.alura.forum.models.dto.TopicRequestDTO;
import br.com.alura.forum.models.dto.TopicResponseDTO;
import br.com.alura.forum.models.dto.TopicUpdateDTO;
import br.com.alura.forum.repositories.CourseRepository;
import br.com.alura.forum.repositories.TopicRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("topics")
public class TopicsController {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CourseRepository courseRepository;

    @PostMapping("/create")
    @CacheEvict(value = "topicList" , allEntries = true)
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody @Valid TopicRequestDTO topicForm){
        Optional<Course> course = this.courseRepository.findByName(topicForm.courseName());
        if (course.isPresent()){
            var topic = topicRepository.save(new Topic(topicForm.title(), topicForm.message(), course.get()));

            return ResponseEntity.ok(new TopicResponseDTO(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getStatus().toString(), topic.getCreatedAt()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopic(@PathVariable UUID id){
        Optional<Topic> topic = this.topicRepository.findById(id);



        if(topic.isPresent()){
            var topicRow = topic.get();
            TopicResponseDTO topicResponse = new TopicResponseDTO(topicRow.getId(), topicRow.getTitle(), topicRow.getMessage(), topicRow.getStatus().toString(), topicRow.getCreatedAt());
            return ResponseEntity.ok(topicResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    @Cacheable(value = "topicList")
    public ResponseEntity<Page<TopicResponseDTO>> listTopics(@RequestParam (required = false) String couseName, @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable){

        Page<TopicResponseDTO> topics;

        if (couseName == null) topics = topicRepository.findAll(pageable).map(topic -> new TopicResponseDTO(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getStatus().toString(), topic.getCreatedAt()));
        else topics = topicRepository.findByCourseName(couseName, pageable).map(topic -> new TopicResponseDTO(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getStatus().toString(), topic.getCreatedAt()));
        return ResponseEntity.ok(topics);
    }

    @PutMapping("/update/{id}")
    @Transactional
    @CacheEvict(value = "topicList" , allEntries = true)
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable UUID id, @RequestBody @Valid TopicUpdateDTO topicForm){
        Optional<Topic> topic =  this.topicRepository.findById(id);
        if (topic.isPresent()){
            topic.get().setTitle(topicForm.title());
            topic.get().setMessage(topicForm.message());
            topic.get().setStatus(topicForm.status());


            return ResponseEntity.ok(new TopicResponseDTO(topic.get().getId(), topicForm.title(), topicForm.message(), topicForm.status().toString(), topic.get().getCreatedAt()));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(value = "topicList" , allEntries = true)
    public ResponseEntity<?> deleteTopic(@PathVariable UUID id){
        Optional<Topic> topic = this.topicRepository.findById(id);
        if (topic.isPresent()){
            this.topicRepository.deleteById(id);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}


