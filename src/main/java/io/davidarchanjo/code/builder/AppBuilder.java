package io.davidarchanjo.code.builder;

import io.davidarchanjo.code.model.domain.App;
import io.davidarchanjo.code.model.dto.AppDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppBuilder {

    private final ModelMapper modelMapper;

    public AppDTO build(App obj) {
        return modelMapper.map(obj, AppDTO.class);
    }
    
    public App build(AppDTO obj) {
        return modelMapper.map(obj, App.class);
    }

    public App build(AppDTO dto, App app) {
        modelMapper.map(dto, app);
        return app;
    }

}
