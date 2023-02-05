package com.chatgpt.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestEditTextVo {
    @NotNull @NotEmpty
    private String input;
    @NotNull @NotEmpty
    private String instruction;
}
