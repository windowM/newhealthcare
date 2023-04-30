package com.example.newhealthcare.chat;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ChatController {



    @GetMapping("/chatbot")
    public String chatBot(Model model, @RequestParam String id){
        model.addAttribute("id",id);
        return "chatbot1.html";
    }

//    private static String secretKey = "시크릿 키 입력";
//    private static String apiUrl = "api url 입력";


    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Map<String,Object> sendMessage(@Payload String chatMessage) throws IOException
    {
        System.out.println("chatMessage :"+chatMessage);
        URL url = new URL(apiUrl);

        String message =  getReqMessage(chatMessage);
        String encodeBase64String = makeSignature(message, secretKey);

        //api서버 접속 (서버 -> 서버 통신)
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;UTF-8");
        con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.write(message.getBytes("UTF-8"));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

        BufferedReader br;
        Map<String,Object> resultMap=new HashMap<>();

        if(responseCode==200) { // 정상 호출

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            con.getInputStream(), "UTF-8"));
            String decodedString;
            String jsonString = "";
            String chatType="";
            while ((decodedString = in.readLine()) != null) {
                jsonString = decodedString;
            }

            //받아온 값을 세팅하는 부분 (text)
            JSONParser jsonparser = new JSONParser();

            try {
                JSONObject json = (JSONObject)jsonparser.parse(jsonString);
                System.out.println("json:"+json.toString());
                JSONArray bubblesArray = (JSONArray)json.get("bubbles");
                JSONObject bubbles = (JSONObject)bubblesArray.get(0);
                chatType=(String)bubbles.get("type");

                //응답 메시지가 text형식이라면
                if(chatType.equals("text")) {
                    resultMap.put("msgType",chatType);

                    JSONObject data = (JSONObject) bubbles.get("data");
                    System.out.println("data :" + data.toString());

                    String description = "";
                    description = (String) data.get("description");
                    chatMessage = description;

                    resultMap.put("description",description);
                }
                //응답 메시지가 template형식 이라면
                else if(chatType.equals("template")){
                    resultMap.put("msgType",chatType);

                    JSONObject data=(JSONObject) bubbles.get("data");
                    JSONObject cover=(JSONObject) data.get("cover");
                    JSONArray contentTableArray= (JSONArray) data.get("contentTable");

                    String type= (String) cover.get("type");
                    resultMap.put("type",type);
                    //"cover" 타입이 이미지면
                    if(type.equals("image")){
                        JSONObject data2=(JSONObject) cover.get("data");
                        for (int i = 0; i < contentTableArray.size(); i++) {
                            JSONArray innerArray = (JSONArray) contentTableArray.get(i);
                            for (int j = 0; j < innerArray.size(); j++) {
                                JSONObject data3 = (JSONObject) innerArray.get(j);
                                JSONObject data4 = (JSONObject) data3.get("data");
                                JSONObject data5 = (JSONObject) data4.get("data");
                                JSONObject data6 = (JSONObject) data5.get("action");
                                JSONObject data7 = (JSONObject) data6.get("data");
                                resultMap.put("title" + i, cover.get("title"));
                                resultMap.put("imageUrl" + i, data2.get("imageUrl"));
                                resultMap.put("description", (data2.get("description")));
                                resultMap.put("url" + i, data7.get("url"));
                            }
                        }

                    }
                    //"cover" 타입이 텍스트면
                    else if(type.equals("text")){
                        JSONObject data2=(JSONObject) cover.get("data");
                        System.out.println("contentTableArray : "+contentTableArray.size());
                        resultMap.put("description", (data2.get("description")));
                        for (int i = 0; i < contentTableArray.size(); i++) {
                            JSONArray innerArray = (JSONArray) contentTableArray.get(i);
                            resultMap.put("arraylen",contentTableArray.size());

                            for (int j = 0; j < innerArray.size(); j++) {
                                JSONObject data3 = (JSONObject) innerArray.get(j);
                                JSONObject data4 = (JSONObject) data3.get("data");
                                JSONObject data5 = (JSONObject) data4.get("data");
                                JSONObject data6 = (JSONObject) data5.get("action");
                                JSONObject data7 = (JSONObject) data6.get("data");
                                resultMap.put("contentType",data6.get("type"));

                                String ctype= (String) data6.get("type");
                                if(ctype.equals("postback")){
                                    resultMap.put("buttonText"+i,data7.get("displayText"));
                                    resultMap.put("postback"+i,data7.get("postback"));

                                }else if(ctype.equals("link")) {
                                    resultMap.put("title" + i, cover.get("title"));
                                    resultMap.put("imageUrl" + i, data2.get("imageUrl"));
                                    resultMap.put("url" + i, data7.get("url"));
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("error");
                e.printStackTrace();
            }
            in.close();
        } else {  // 에러 발생
            System.out.println("error: "+responseCode);
            chatMessage = con.getResponseMessage();
            resultMap.put("error",con.getResponseCode());
        }
        System.out.println("resultMessage:"+resultMap);
        return resultMap;
    }

    //객관식 응답 postback처리
    @ResponseBody
    @RequestMapping("/postback")
    public String handlePostback(@RequestParam("postback") String postback) {
        if (postback.equals("UnexpiredForm␞68256␞0")) {
            // 버튼이 클릭된 경우 처리할 코드 작성
            System.out.println("버튼 1이 클릭되었습니다.");
            return "1";
        }else if (postback.equals("UnexpiredForm␞68256␞1")) {
            // 버튼이 클릭된 경우 처리할 코드 작성
            System.out.println("버튼 2이 클릭되었습니다.");
            return "2";
        } else if (postback.equals("UnexpiredForm␞68256␞2")) {
            // 버튼이 클릭된 경우 처리할 코드 작성
            System.out.println("버튼 3이 클릭되었습니다.");
            return "3";
        }else if (postback.equals("UnexpiredForm␞68256␞3")) {
            // 버튼이 클릭된 경우 처리할 코드 작성
            System.out.println("버튼 4이 클릭되었습니다.");
            return "4";
        }else if (postback.equals("UnexpiredForm␞68256␞4")) {
            // 버튼이 클릭된 경우 처리할 코드 작성
            System.out.println("버튼 5이 클릭되었습니다.");
            return "5";
        }
        else {
            // postback 값이 다른 경우 처리할 코드 작성
            System.out.println("잘못 클릭");
            return "error!!";
        }
    }

    //보낼 메세지를 네이버에서 제공해준 암호화로 변경해주는 메소드
    public static String makeSignature(String message, String secretKey) {

        String encodeBase64String = "";

        try {
            byte[] secrete_key_bytes = secretKey.getBytes("UTF-8");

            SecretKeySpec signingKey = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.encodeBase64String(rawHmac);

            return encodeBase64String;

        } catch (Exception e){
            System.out.println(e);
        }

        return encodeBase64String;

    }

    //보낼 메세지를 네이버 챗봇에 포맷으로 변경해주는 메소드
    public static String getReqMessage(String voiceMessage) {

        String requestBody = "";

        try {

            JSONObject obj = new JSONObject();

            long timestamp = new Date().getTime();

            System.out.println("##"+timestamp);

            obj.put("version", "v2");
            obj.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
            obj.put("timestamp", timestamp);

            JSONObject bubbles_obj = new JSONObject();

            bubbles_obj.put("type", "text");

            JSONObject data_obj = new JSONObject();
            data_obj.put("description", voiceMessage);

            bubbles_obj.put("type", "text");
            bubbles_obj.put("data", data_obj);

            JSONArray bubbles_array = new JSONArray();
            bubbles_array.add(bubbles_obj);

            obj.put("bubbles", bubbles_array);
            obj.put("event", "send");

            requestBody = obj.toString();

        } catch (Exception e){
            System.out.println("## Exception : " + e);
        }

        return requestBody;
    }



}