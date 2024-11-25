package br.com.alura.forum.controllers;

import br.com.alura.forum.models.User;
import br.com.alura.forum.models.dto.UserDTO;
import br.com.alura.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){

        String encriptedPassword = new BCryptPasswordEncoder().encode(user.password());

        User newUser = new User(user.name(), user.email(), encriptedPassword);
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable UUID id){
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        };
        return ResponseEntity.notFound().build();
    }
}
