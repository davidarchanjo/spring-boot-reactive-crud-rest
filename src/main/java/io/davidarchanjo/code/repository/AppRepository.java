package io.davidarchanjo.code.repository;

import io.davidarchanjo.code.model.domain.App;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AppRepository extends ReactiveCrudRepository<App, Long> {

    Mono<App> findByName(String name);
    Mono<App> findByNameAndVersion(String name, String version);

}