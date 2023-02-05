package com.chatgpt.demo.service;

import com.chatgpt.demo.model.RequestEditTextVo;
import com.chatgpt.demo.model.RequestMakeNameVo;
import com.chatgpt.demo.model.RequestQuestionVo;
import com.chatgpt.demo.model.ResponseVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@PropertySource("classpath:apikey.properties")
public class GptService {
    @Value("${apikey}")
    private String API_KEY;
    private static final String COMPLETION_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String EDIT_ENDPOINT = "https://api.openai.com/v1/edits";
    private static final String IMAGES_ENDPOINT = "https://api.openai.com/v1/images/generations";
    public ResponseVo getVariableName(RequestMakeNameVo requestMakeNameVo) {
        ResponseVo responseVo = new ResponseVo();
        String caseName = "";
        try {
            if(!ObjectUtils.isEmpty(requestMakeNameVo.getCaseName())){
                caseName = "Please make it into a " + requestMakeNameVo.getCaseName() + " case";
            }

            String question = requestMakeNameVo.getText() + "(을)를 지칭하는 변수 명으로 뭐가 적절할까요? " + caseName;

            String answer = generateText(question, 0.0f, 20);

            String answerFilter1 = answer.replaceAll("\n", "");
            String result =  answerFilter1.replaceAll("\\.","");
            responseVo.setCode(200);
            responseVo.setResponse(result);
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo getClassName(RequestMakeNameVo requestMakeNameVo) {
        ResponseVo responseVo = new ResponseVo();
        try {
            String question = requestMakeNameVo.getText() + "(을)를 지칭하는 클래스 명으로 뭐가 적절할까요?";

            String answer = generateText(question, 0.5f, 100);

            responseVo.setCode(200);
            String answerFilter1 = answer.replaceAll("\n", "");
            String result =  answerFilter1.replaceAll("\\.","");
            responseVo.setResponse(result);
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo getConversation(RequestQuestionVo requestQuestionVo) {
        ResponseVo responseVo = new ResponseVo();
        try {

            String answer = generateText(requestQuestionVo.getQuestion(), 0.5f, 1000);

            responseVo.setCode(200);
            String answerFilter1 = answer.replaceAll("\n", "");
            String result =  answerFilter1.replaceAll("\\.","");
            result =  result.replaceAll("\\\\","");
            result =  result.replaceAll("\"","");

            responseVo.setResponse(result);
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo editText(RequestEditTextVo requestEditTextVo) {
        ResponseVo responseVo = new ResponseVo();
        try {
            String answer = editText(requestEditTextVo.getInput(), requestEditTextVo.getInstruction(),0.0f);
            String answerFilter1 = answer.replace("\n", "");
            String result =  answerFilter1.replace("\\.","");
            result =  result.replace("\\\\","");
            result =  result.replace("\"","");
            responseVo.setCode(200);
            responseVo.setResponse(result);
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo makeImages(RequestQuestionVo requestQuestionVo) {
        ResponseVo responseVo = new ResponseVo();
        try {
            String url = makeImages(requestQuestionVo.getQuestion());
            responseVo.setCode(200);
            responseVo.setResponse(url);
        } catch (Exception e) {
            responseVo.setCode(500);
            responseVo.setResponse("generateText error(서버 에러)");
        }
        return responseVo;
    }

    public ResponseVo getGPTModels() {
        ResponseVo responseVo = new ResponseVo();
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity request = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.openai.com/v1/models",
                    HttpMethod.GET,
                    request,
                    String.class
            );

            String answer = response.toString();

            responseVo.setCode(200);
            responseVo.setResponse(answer);
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
        ResponseEntity<Map> response = restTemplate.postForEntity(COMPLETION_ENDPOINT, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();
        System.out.println(responseBody.toString());

        List<Map<String, Object>> choicesList = (List<Map<String, Object>>)responseBody.get("choices");
        Map<String, Object> choiceMap = choicesList.get(0);
        String answer = (String)choiceMap.get("text");

        return answer;
    }

    public String editText(String prompt, String instruction, float temperature) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","text-davinci-edit-001");
        requestBody.put("input", prompt);
        requestBody.put("instruction", instruction);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(EDIT_ENDPOINT, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();
        System.out.println(responseBody.toString());

        List<Map<String, Object>> choicesList = (List<Map<String, Object>>)responseBody.get("choices");
        Map<String, Object> choiceMap = choicesList.get(0);
        String answer = (String)choiceMap.get("text");

        return answer;
    }

    public String makeImages(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("size","1024x1024");
        requestBody.put("n", 1);
        requestBody.put("response_format", "url");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(IMAGES_ENDPOINT, requestEntity, Map.class);
        Map<String, Object> responseBody = response.getBody();
        System.out.println(responseBody.toString());

        List<Map<String,Object>> dataList = (List<Map<String,Object>>)responseBody.get("data");
        String url = (String)(dataList.get(0).get("url"));
        return url;
    }
}
