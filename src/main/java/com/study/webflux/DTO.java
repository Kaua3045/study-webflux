package com.study.webflux;

import com.study.webflux.models.User;
import reactor.core.publisher.Mono;

public record DTO(String id) {

    public static Mono<DTO> of(User user) {
        return Mono.just(new DTO(user.getId()));
    }
}
