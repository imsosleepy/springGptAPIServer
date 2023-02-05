package com.chatgpt.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestMakeNameVo {
    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String text;
    @Size(max = 10)
    @Pattern(regexp = "camel|snake")
    private String caseName;
}
