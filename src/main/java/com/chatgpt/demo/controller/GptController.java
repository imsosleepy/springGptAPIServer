package com.chatgpt.demo.controller;

import com.chatgpt.demo.model.ReqeustQuestionVo;
import com.chatgpt.demo.model.RequestMakeNameVo;
import com.chatgpt.demo.model.ResponseVo;
import com.chatgpt.demo.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

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
    public String getModelList(){
        String answer = getGPTModels();

        System.out.println(answer);
        return "naming.html";
    }

    public String getGPTModels() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", "Bearer " + API_KEY);

        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.openai.com/v1/models",
                HttpMethod.GET,
                request,
                String.class
        );

        return response.toString();
    }
}
