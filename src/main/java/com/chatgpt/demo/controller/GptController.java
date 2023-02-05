package com.chatgpt.demo.controller;

import com.chatgpt.demo.model.RequestEditTextVo;
import com.chatgpt.demo.model.RequestMakeNameVo;
import com.chatgpt.demo.model.RequestQuestionVo;
import com.chatgpt.demo.model.ResponseVo;
import com.chatgpt.demo.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RequestMapping("/gptAPI")
@RestController
public class GptController {
    @Autowired
    GptService gptService;

    @RequestMapping(value = "/make/variable/name", method = RequestMethod.GET)
    public ResponseVo makeVariableName(@Valid RequestMakeNameVo requestMakeNameVo){

        ResponseVo responseVo = gptService.getVariableName(requestMakeNameVo);

        return responseVo;
    }

    @RequestMapping(value = "/make/class/name", method = RequestMethod.GET)
    public ResponseVo makeClassName(@Valid RequestMakeNameVo requestMakeNameVo){

        ResponseVo responseVo = gptService.getClassName(requestMakeNameVo);

        return responseVo;
    }

    @RequestMapping(value = "/make/conversation", method = RequestMethod.GET)
    public ResponseVo makeConversation(@Valid RequestQuestionVo requestQuestionVo){
        ResponseVo responseVo = gptService.getConversation(requestQuestionVo);

        return responseVo;
    }

    @RequestMapping(value = "/make/edit", method = RequestMethod.GET)
    public ResponseVo makeEdit(@Valid RequestEditTextVo requestEditTextVo){
        ResponseVo responseVo = gptService.editText(requestEditTextVo);

        return responseVo;
    }

    @RequestMapping(value = "/make/images", method = RequestMethod.GET)
    public ResponseVo makeImages(@Valid RequestQuestionVo requestQuestionVo){
        ResponseVo responseVo = gptService.makeImages(requestQuestionVo);

        return responseVo;
    }

    @GetMapping("gpt/model/list")
    public ResponseVo getModelList(){
        ResponseVo responseVo = gptService.getGPTModels();

        return responseVo;
    }

}
