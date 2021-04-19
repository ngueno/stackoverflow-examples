package com.ngueno.example.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/example")
public class ExampleController {

    @PostMapping
    public ResponseEntity<?> validateExample(@RequestBody @Valid ExampleRequest request) {
        log.info("Received {}", request);
        return ResponseEntity.ok().build();
    }
}
