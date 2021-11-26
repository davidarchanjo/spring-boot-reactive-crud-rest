package io.davidarchanjo.code.handler;

import io.davidarchanjo.code.model.dto.AppDTO;
import io.davidarchanjo.code.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AppHandler {
    
    private final AppService service;

    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(service.findAll(), AppDTO.class);    
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(AppDTO.class)
            .flatMap(service::save)
            .flatMap(o -> {
                final UriComponents uri = UriComponentsBuilder
                    .fromPath(req.path()+"/{id}")
                    .buildAndExpand(o.getId());
                return ServerResponse.created(uri.toUri()).build();
            });
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        return service.findById(Long.valueOf(req.pathVariable("id")))
            .flatMap(o -> ServerResponse.ok().body(Mono.just(o), AppDTO.class));
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return req.bodyToMono(AppDTO.class)
            .flatMap(o -> service.updateById(Long.valueOf(req.pathVariable("id")), o))
            .flatMap(__ -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return service.deleteById(Long.valueOf(req.pathVariable("id")))
            .flatMap(__ -> ServerResponse.noContent().build());
    }
}
