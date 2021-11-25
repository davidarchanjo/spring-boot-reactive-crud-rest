package io.davidarchanjo.code.builder;

import io.davidarchanjo.code.model.domain.App;
import io.davidarchanjo.code.model.dto.AppDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AppBuilder {

    private final ModelMapper modelMapper;

    public Mono<AppDTO> build(App obj) {
        return Mono.just(modelMapper.map(obj, AppDTO.class));
    }
    
    public Mono<App> build(AppDTO obj) {
        return Mono.just(modelMapper.map(obj, App.class));
    }

    public Mono<App> build(AppDTO dto, App app) {
        modelMapper.map(dto, app);
        return Mono.just(app);
    }

}
