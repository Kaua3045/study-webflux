package com.study.webflux.controllers;

import com.study.webflux.DTO;
import com.study.webflux.models.User;
import com.study.webflux.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

//    @Autowired
    private final UserRepository userRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> findAllUsers() {
        return userRepository.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> findUserById(@PathVariable String id) {
        log.info("Id {} received in thread {}", id, Thread.currentThread().getName());
        return userRepository.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DTO> saveUser(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.createUser(user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> updateUser(@RequestBody User user) {
        return userRepository.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userRepository.deleteUser(id);
    }
}
