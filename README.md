# OpenAI API를 이용한 스프링프레임워크 서버 구축
EC2에 구축된 서버에 요청이 들어오면, OpenAI API로 외부 요청을 한 후, 만들어진 응답을 내려준다.

사용 기술 : AWS EC2, Spring Framework, Java

블로그 : https://akku-dev.tistory.com/52

---
## **EC2 PUBLIC IP : ...**

### **공통 규격**
### **1. Status Code**
| Code | Description | 
|------|-------------|
| 200  | 성공 |
| 400  | 입력 데이터 오류 |
|500   | 서버 오류(외부 연동 실패) |
### **2. Header**
없음

---

## **1. 변수 생성하기**
|HTTP METHOD|URL|
|------|---|
|GET|htttp://{{[EC2 IP](#ec2-public-ip--54241652278080)}}/gptAPI/make/variable/name|
### **1.1. Request**

| Name | Type | Description | Mandatory |
|------|------|------|-----|
|text  | String |변수로 변환할 문자열| Y|
|caseName  | String |변환할 문자열 케이스 camel(default)/snake|N|

### **1.2. Response**
| Name | Type | Description | Mandatory |
|------|------|------|-----|
| code  | Integer | 응답 코드| Y|
| response  | String | 변수명| Y|

### **1.3. Example**
- Request  
`http://[ip]:[port}/gptAPI/make/variable/name?text="빨간 사과"&caseName=camel`
- Response 
```json
{
  "code": 200,
  "response": "redApple"
}
```


---
## **2. 클래스명 생성하기**
|HTTP METHOD|URL|
|------|---|
|GET|htttp://{{[EC2 IP](#ec2-public-ip--54241652278080)}}/gptAPI/make/class/name|
### **2.1. Request**

| Name | Type | Description | Mandatory |
|------|------|------|-----|
|text  | String |클래스로 변환할 문자열| Y|


### **2.2. Response**
| Name | Type | Description | Mandatory |
|------|------|------|-----|
| code  | Integer | 응답 코드| Y|
| response  | String | 클래스명| Y|

### **2.3. Example**
- Request :
`http://[ip]:[port}/gptAPI/make/variable/name?class="스포츠카"`

- Response 
```json
{
  "code": 200,
  "response": "SportsCar 또는 SportCar이 적절합니다"
}
```

---

>아래 API들은 openAPI에서 제공하는 기본 API를 OpenAI의 서버로 그대로 요청합니다. 질문이 구체적이지 않을 경우, 속도 이슈가 있으니 요청에 주의해야합니다.

## **3. 질답하기(Completions)**
|HTTP METHOD|URL|
|------|---|
|GET|htttp://{{[EC2 IP](#ec2-public-ip--54241652278080)}}/gptAPI/make/conversation|
### **3.1. Request**

| Name | Type | Description | Mandatory |
|------|------|------|-----|
|question  | String |질문할 문자열| Y|


### **3.2. Response**
| Name | Type | Description | Mandatory |
|------|------|------|-----|
| code  | Integer | 응답 코드| Y|
| response  | String | GPT의 대답 문자열| Y|

### **3.3. Exmaple**
- Request :
`http://[ip]:[port}/gptAPI/make/conversation?question="펭귄은 춤을 잘 추나요?"`

- Response 
```json
{
  "code": 200,
  "response": "펭귄은 춤을 잘 추지는 않습니다. 하지만 펭귄은 다른 동물들과 다르게 춤추는 능력이 있기 때문에, 인간들이 춤을 추는 것을 따라하는 것이 가능합니다. 펭귄의 춤은 인간의 춤과 다른 점이 많습니다."
}
```


---

## **4. 수정하기(Edit)**
|HTTP METHOD|URL|
|------|---|
|GET|htttp://{{[EC2 IP](#ec2-public-ip--54241652278080)}}/gptAPI/make/edit|
### **4.1. Request**

| Name | Type | Description | Mandatory |
|------|------|------|-----|
|input  | String |수정 작업의 타겟이 되는 문자열| Y|
|instruction  | String |수정할 내용을 알려주는 문자열| Y|


### **4.2. Response**
| Name | Type | Description | Mandatory |
|------|------|------|-----|
| code  | Integer | 응답 코드| Y|
| response  | String | GPT의 대답 문자열| Y|


### **4.3. Exmaple**
- Request :
`http://[ip]:[port}/gptAPI/make/edit?input="Do you known panguin&instruction=Fix the spelling mistakes"`
- Response 
```json
{
  "code": 200,
  "response": "Do you know the pinguin"
}
```

---

## **5. 이미지 그리기(Images)**
|HTTP METHOD|URL|
|------|---|
|GET|htttp://{{[EC2 IP](#ec2-public-ip--54241652278080)}}/gptAPI/make/images|
### **5.1. Request**

| Name | Type | Description | Mandatory |
|------|------|------|-----|
|question  | String |이미지로 변환할 문자열| Y|


### **5.2. Response**
| Name | Type | Description | Mandatory |
|------|------|------|-----|
| code  | Integer | 응답 코드| Y|
| response  | String | 이미지 URL| Y|


### **5.3. Exmaple**
- Request :
`http://[ip]:[port}/gptAPI/make/images?question=%22emperor%20penguin%22`
- Response 
```json
{
  "code": 200,
  "response": "https://oaidalleapiprodscus.blob.core.windows.net/private/org-GbVASL6oLIhz7KbwJmy0ig4T/user-J438M60flJjB83j8gKWlZaBF/img-axwqjBuZiE8g6z65UGKulsuX.png?st=2023-02-05T13%3A23%3A21Z&se=2023-02-05T15%3A23%3A21Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-02-04T21%3A09%3A29Z&ske=2023-02-05T21%3A09%3A29Z&sks=b&skv=2021-08-06&sig=ar2TN6JRRz3//M6e91sraj7HKTK7vfj6XtOyuscWfqs%3D"
}
```
---

## **6. Model 목록 가져오기**
응답이 정리되지 않아 엄청 나게깁니다.
|HTTP METHOD|URL|
|------|---|
|GET|htttp://{{[EC2 IP](#ec2-public-ip--54241652278080)}}/gptAPI/gpt/model/list|
### **5.1. Request**
없음(단일 모델을 조회할 수 있으나 현 서버에서는 구현하지 않음)
### **5.2. Response**
| Name | Type | Description | Mandatory |
|------|------|------|-----|
| code  | Integer | 응답 코드| Y|
| response  | String | 모델 정보 json| Y|

### **5.3. Exmaple**
- Request :
`http://[ip]:[port}/gptAPI/gpt/model/list`
- Response :
```json
{
  "code": 200,
  "response": "<200,{\n  \"object\": \"list\",\n  \"data\": [\n    {\n      \"id\": \"babbage\",\n      \"object\": \"model\",\n      \"created\": 1649358449,\n      \"owned_by\": \"openai\",\n      \"permission\": [\n        {\n          \"id\": \"modelperm-49FUp5v084tBB49tC4z8LPH5\",\n          \"object\": \"model_permission\",\n          \"created\": 1669085501,\n          \"allow_create_engine\": false,\n          \"allow_sampling\": true,\n          \"allow_logprobs\": true,\n          \"allow_search_indices\": false,\n          \"allow_view\": true,\n            ...
 }
```


---
# TODO
- 세부 파라미터로 조회 할 수 있도록 Request 파라미터 추가
- API를 잘못 요청했을 때, 자연스러운 에러 페이지를 출력하도록 수정
- 코드 리팩토링(하드코딩 된 코드 정리 및 매서드 분리)
- 어떤 이유에서인지 Docker에서 동작하지 않아서 AWS EC2 위에서 바로 동작함. Docker로 구동 필요.
