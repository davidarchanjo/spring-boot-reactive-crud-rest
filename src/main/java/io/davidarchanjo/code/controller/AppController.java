package io.davidarchanjo.code.controller;

import io.davidarchanjo.code.model.dto.AppDTO;
import io.davidarchanjo.code.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/annotation/apps")
public class AppController {
    
    private final AppService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)    
    public Mono<?> create(@Valid @RequestBody AppDTO dto) {
        return customerService.save(dto);
    }

    @GetMapping("{id}")
    public Mono<?> findById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping
    public Flux<?> findAll() {
        return customerService.findAll();
    }

    public Mono<?> update(@PathVariable("id") Long id, @Valid @RequestBody AppDTO dto) {
        return customerService.updateById(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> delete(@PathVariable("id") Long id) {
        return customerService.deleteById(id);
    }

}