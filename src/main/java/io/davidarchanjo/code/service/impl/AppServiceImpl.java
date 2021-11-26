package io.davidarchanjo.code.service.impl;

import io.davidarchanjo.code.builder.AppBuilder;
import io.davidarchanjo.code.exception.AppAlreadyExistException;
import io.davidarchanjo.code.exception.AppNotFoundException;
import io.davidarchanjo.code.model.domain.App;
import io.davidarchanjo.code.model.dto.AppDTO;
import io.davidarchanjo.code.repository.AppRepository;
import io.davidarchanjo.code.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AppServiceImpl implements AppService {
    
    private final AppBuilder builder;
    private final AppRepository repository;

    @Override
    public Mono<AppDTO> save(AppDTO dto) {
        return repository.findByName(dto.getName())
            .flatMap(__ -> Mono.error(new AppAlreadyExistException("App with name - {0}, already exist", dto.getName())))
            .switchIfEmpty(repository.save(builder.build(dto)))
            .cast(App.class)
            .flatMap(o -> Mono.just(builder.build(o)));
    }

    @Override
    public Mono<AppDTO> findById(Long id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new AppNotFoundException("App with id - {0}, not found", id)))
            .flatMap(o -> Mono.just(builder.build(o)));
    }

    @Override
    public Flux<AppDTO> findAll() {
        return repository.findAll()
            .flatMap(o -> Mono.just(builder.build(o)));
    }

    @Override
    public Mono<Void> updateById(Long id, AppDTO dto) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new AppNotFoundException("App with id - {0}, not found", id)))
            .flatMap(o -> Mono.just(builder.build(dto, o)))
            .flatMap(repository::save)
            .flatMap(__ -> Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return findById(id)
            .flatMap(__ -> repository.deleteById(id));
    }

}
