package io.davidarchanjo.code.service;

import io.davidarchanjo.code.model.dto.AppDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AppService {

    Mono<AppDTO> save(AppDTO dto);
    Mono<AppDTO> findById(Long id);
    Flux<AppDTO> findAll();
    Mono<AppDTO> updateById(Long id, AppDTO dto);
    Mono<Void> deleteById(Long id);

}
