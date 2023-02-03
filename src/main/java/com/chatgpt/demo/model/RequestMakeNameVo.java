package com.chatgpt.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RequestMakeNameVo {
    @NotNull
    @Size(max = 100)
    private String text;

    private String language;
}
