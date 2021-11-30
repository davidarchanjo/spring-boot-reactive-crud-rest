package io.davidarchanjo.code.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.davidarchanjo.code.model.dto.AppDTO;
import io.davidarchanjo.code.service.AppService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/annotation/apps")
public class AppController {
    
    private final AppService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)    
    public Mono<?> create(@Valid @RequestBody AppDTO dto) {
        return service.save(dto);
    }

    @GetMapping("{id}")
    public Mono<?> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public ResponseEntity<?> find(
        @RequestParam(name = "appName", required = false) String name, 
        @RequestParam(name = "appVersion", required = false) String version
    ) {
        return Objects.nonNull(name) && Objects.nonNull(version) 
            ? ResponseEntity.ok(service.findByNameAndVersion(name, version))
            : ResponseEntity.ok(service.findAll());
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> update(@PathVariable Long id, @Valid @RequestBody AppDTO dto) {
        return service.updateById(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> delete(@PathVariable Long id) {
        return service.deleteById(id);
    }

}