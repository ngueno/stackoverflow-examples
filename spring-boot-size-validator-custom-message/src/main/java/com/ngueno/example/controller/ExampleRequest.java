package com.ngueno.example.controller;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExampleRequest {

    @Size(min = 3, max = 10, message = "{example.request.Size.message}")
    private String example;
}
