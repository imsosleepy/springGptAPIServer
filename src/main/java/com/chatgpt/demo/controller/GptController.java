package com.chatgpt.demo.controller;

import com.chatgpt.demo.model.ReqeustQuestionVo;
import com.chatgpt.demo.model.RequestMakeNameVo;
import com.chatgpt.demo.model.ResponseVo;
import com.chatgpt.demo.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RequestMapping("/gptAPI")
@RestController
public class GptController {
    @Autowired
    GptService gptService;

    @RequestMapping(value = "/make/variable/name")
    public ResponseVo makeVariableName(@Valid RequestMakeNameVo requestMakeNameVo){

        ResponseVo responseVo = gptService.getVariableName(requestMakeNameVo);

        return responseVo;
    }

    @RequestMapping(value = "/make/class/name")
    public ResponseVo makeClassName(@Valid RequestMakeNameVo requestMakeNameVo){

        ResponseVo responseVo = gptService.getClassName(requestMakeNameVo);

        return responseVo;
    }

    @RequestMapping(value = "/make/conversation")
    public ResponseVo makeConversation(@Valid ReqeustQuestionVo reqeustQuestionVo){
        ResponseVo responseVo = gptService.getConversation(reqeustQuestionVo);

        return responseVo;
    }

    @GetMapping("gpt/model/list")
    public ResponseVo getModelList(){
        ResponseVo responseVo = gptService.getGPTModels();

        return responseVo;
    }
}
