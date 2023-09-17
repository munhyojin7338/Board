package com.example.board.memberController.controller.kakao;

import com.example.board.memberController.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoService {

    //인증코드로 token요청하기
    public KakaoToken requestToken(String code) {

        String strUrl = "https://kauth.kakao.com/oauth/token"; //request를 보낼 주소
        KakaoToken kakaoToken = new KakaoToken(); //response를 받을 객체

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url Http 연결 생성

            //POST 요청
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//outputStreamm으로 post 데이터를 넘김

            //파라미터 세팅
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            //0번 파라미터 grant_type 입니다 authorization_code로 고정이라니 고정등록해줍니다
            sb.append("grant_type=authorization_code");

            //1번 파라미터 client_id입니다. ***자신의 앱 REST API KEY로 변경해주세요***
            sb.append("&client_id=0c4dac238e4b5d119155fe349c57b471");

            //2번 파라미터 redirect_uri입니다. ***자신의 redirect uri로 변경해주세요***
            sb.append("&redirect_uri=http://powerboard.shop/kakaoPage");

            //3번 파라미터 code입니다. 인자로 받아온 인증코드입니다.
            sb.append("&code=" + code);

            // 4번 파라미터 clientSecret
            sb.append("&client_secret=1soroY5vx3FqbX51mRQdo1bwiQb4xAVh");
            bw.write(sb.toString());
            bw.flush();//실제 요청을 보내는 부분

            //실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("responsecode(200이면성공): {}", responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            log.info("response body: {}", result);


            //Jackson으로 json 파싱할 것임
            ObjectMapper mapper = new ObjectMapper();

            //kakaoToken에 result를 KakaoToken.class 형식으로 변환하여 저장
            kakaoToken = mapper.readValue(result, KakaoToken.class);

            //api호출용 access token
            String access_Token = kakaoToken.getAccess_token();

            //access 토큰 만료되면 refresh token사용(유효기간 더 김)
            String refresh_Token = kakaoToken.getRefresh_token();


            log.info("access_token = {}", access_Token);
            log.info("refresh_token = {}", refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("카카오토큰생성완료>>>{}", kakaoToken);
        return kakaoToken;
    }

    //인증코드로 token요청하기
    public Member requestUser(String accessToken){
        log.info("requestUser 시작");
        String strUrl = "https://kapi.kakao.com/v2/user/me"; //request를 보낼 주소
        Member user = new Member(); //response를 받을 객체

        try{
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //url Http 연결 생성

            //POST 요청
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);//outputStreamm으로 post 데이터를 넘김

            //전송할 header 작성, 인자로 받은 access_token전송
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);


            //실제 요청을 보내는 부분, 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("requestUser의 responsecode(200이면성공): {}",responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";



            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();

            log.info("response body: {}",result);


            //Jackson으로 json 파싱할 것임
            ObjectMapper mapper = new ObjectMapper();
            //결과 json을 HashMap 형태로 변환하여 resultMap에 담음
            HashMap<String,Object> resultMap = mapper.readValue(result, HashMap.class);
            //json 파싱하여 id 가져오기
            Long kakaoId = Long.valueOf(String.valueOf(resultMap.get("id")));

            //결과json 안에 properties key는 json Object를 value로 가짐
            HashMap<String,Object> properties = (HashMap<String, Object>) resultMap.get("properties");
            String nickname = (String)properties.get("nickname");



            //결과json 안에 kakao_account key는 json Object를 value로 가짐
            HashMap<String,Object> kakao_account = (HashMap<String, Object>) resultMap.get("kakao_account");
            String email=null;//이메일은 동의해야 알 수 있음
            if(kakao_account.containsKey("email")){//동의하면 email이 kakao_account에 key로 존재함
                email=(String)kakao_account.get("email");
            }

            //유저정보 세팅
            user.setEmail(email);
            // 카카오에서 받아온 고유 ID를 Member 엔티티의 kakaoId 필드에 설정
            user.setKakaoId(kakaoId);
            user.setNickName(nickname);

            log.info("resultMap= {}",resultMap);
            log.info("properties= {}",properties);


        }catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

}
