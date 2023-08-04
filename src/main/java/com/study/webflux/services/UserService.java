package com.study.webflux.services;

import com.study.webflux.DTO;
import com.study.webflux.models.User;
import com.study.webflux.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<User> findAllUsers() {
        // aqui o certo seria chamar o usecase, só não sei como ficaria
        // já que o usecase não deveria conhecer o framework
        // então ele chamaria o gateway que retorna um List<User>
        return userRepository.getAllUsers();
    }

    public Mono<User> findUserById(String id) {
        return userRepository.getUserById(id);
    }

    public Mono<DTO> saveUser(User user) {
        return userRepository.createUser(user);
    }

    public Mono<User> updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteUser(id);
    }
}
