package com.chatgpt.demo.service;

import com.chatgpt.demo.model.ReqeustQuestionVo;
import com.chatgpt.demo.model.RequestMakeNameVo;
import com.chatgpt.demo.model.ResponseVo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GptService {
    private static final String API_KEY = "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";
    private static final String ENDPOINT = "https://api.openai.com/v1/completions";
    public ResponseVo getVariableName(RequestMakeNameVo requestMakeNameVo) {
        ResponseVo responseVo = new ResponseVo();
        String language = "";
        try {
            if(!ObjectUtils.isEmpty(requestMakeNameVo.getLanguage())){
                language = requestMakeNameVo.getLanguage() + "를 사용했습니다.";
            }

            String question = requestMakeNameVo.getText() + "(을)를 지칭하는 변수 명으로 뭐가 적절할까요? " + language;

            String answer = generateText(question, 0.5f, 100);

            responseVo.setCode(200);
            responseVo.setResponse(answer.replaceAll("\n", ""));
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo getClassName(RequestMakeNameVo requestMakeNameVo) {
        ResponseVo responseVo = new ResponseVo();
        String language = "";
        try {
            if(!ObjectUtils.isEmpty(requestMakeNameVo.getLanguage())){
                language = requestMakeNameVo.getLanguage() + "를 사용했습니다.";
            }

            String question = requestMakeNameVo.getText() + "(을)를 지칭하는 클래스 명으로 뭐가 적절할까요? " + language;

            String answer = generateText(question, 0.5f, 100);

            responseVo.setCode(200);
            responseVo.setResponse(answer.replaceAll("\n", ""));
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo getConversation(ReqeustQuestionVo reqeustQuestionVo) {
        ResponseVo responseVo = new ResponseVo();
        try {

            String answer = generateText(reqeustQuestionVo.getQuestion(), 0.5f, 1000);

            responseVo.setCode(200);
            responseVo.setResponse(answer.replaceAll("\n", ""));
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }


    public String generateText(String prompt, float temperature, int maxTokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","text-davinci-003");
        requestBody.put("prompt", prompt);
        requestBody.put("temperature", temperature);
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();
        System.out.println(responseBody.toString());

        List<Map<String, Object>> choicesList = (List<Map<String, Object>>)responseBody.get("choices");
        Map<String, Object> choiceMap = choicesList.get(0);
        String answer = (String)choiceMap.get("text");

        return answer;
    }
}
