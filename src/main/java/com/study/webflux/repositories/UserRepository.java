package com.study.webflux.repositories;

import com.study.webflux.DTO;
import com.study.webflux.models.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.converters.multi.MultiReactorConverters;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class UserRepository {

    // https://www.javaguides.net/2018/11/hibernate-delete-or-remove-entity.html#:~:text=In%20Hibernate%2C%20an%20entity%20can,persistent%20object%20from%20the%20datastore.

    private final Mutiny.SessionFactory sessionFactory = Persistence
            .createEntityManagerFactory("default")
            .unwrap(Mutiny.SessionFactory.class);

    // Mono (retorna um objeto só) 0 ou 1 // single
    // Flux (retorna uma lista) 0 ou N // reactive sequence of items

    public Mono<User> getUserById(String id) {
        return sessionFactory.withSession(session -> {
                    log.info("Searching user {} in thread {}", id, Thread.currentThread().getName());
                    return session.find(User.class, id);
                })
                .convert().with(UniReactorConverters.toMono())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .doFinally(any -> log.info("User {} found in thread {}", id, Thread.currentThread().getName()));
        // faltou o close
    }

    public Flux<User> getAllUsers() {
        // faltou o close
        return sessionFactory.withSession(session -> session.createQuery("from User", User.class)
                        .getResultList())
                .toMulti()
                .flatMap(list -> Multi.createFrom().iterable(list))
                .convert()
                .with(MultiReactorConverters.toFlux());
    }

    public Mono<DTO> createUser(User user) {
        return sessionFactory
                .withSession(session -> session.persist(user)
                        .chain(session::flush))
                .replaceWith(user)
                .convert().with(UniReactorConverters.toMono())
                .flatMap(DTO::of);
        // faltou o close
    }

    public Mono<User> updateUser(User user) {
        return sessionFactory.withTransaction((session, tx) -> session.find(User.class, user.getId())
                .onItem().ifNotNull().transform(oldUser -> {
                    if (user.getName() != null) oldUser.setName(user.getName());
                    if (user.getUsername() != null) oldUser.setUsername(user.getUsername());
                    if (user.getEmail() != null) oldUser.setEmail(user.getEmail());
                    session.persist(oldUser);
                    return oldUser;
                })).onItem().ifNull().failWith(new RuntimeException("User not found"))
                .convert().with(UniReactorConverters.toMono());
    }

    public Mono<Void> deleteUser(String id) {
        return sessionFactory.withTransaction((session, tx) -> session.find(User.class, id)
                .onItem().ifNull().failWith(new RuntimeException("User not found"))
                .chain(session::remove)
                .chain(session::flush))
                .convert().with(UniReactorConverters.toMono());
        // Retornamos um Mono após ser concluido a exclusão ou um erro
        // O mono fara com que a rota retorne após a conclusão
    }
}