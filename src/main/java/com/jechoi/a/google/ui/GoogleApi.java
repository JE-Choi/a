package com.jechoi.a.google.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jechoi.a.common.GoogleOAuth2Config;
import com.jechoi.a.common.constant.SessionKey;
import com.jechoi.a.google.domain.GoogleTokenInfo;
import com.jechoi.a.google.domain.GoogleLoginRequest;
import com.jechoi.a.google.domain.GoogleLoginResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController("/google")
@RequiredArgsConstructor
public class GoogleApi {
    private final GoogleOAuth2Config googleOAuth2Config;
    private final ObjectMapper objectMapper;

    @GetMapping(value = "/login")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        try {
            final URI redirectUri = new URI(googleOAuth2Config.getGoogleInitUrl());
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    /**
     * access/refresh token 발행
     * - 백투백으로 구글에 권한부여코드를 전달해서 token을 발급받는다.
     *
     * @param authCode
     * @return
     */
    @GetMapping(value = "/login/oauth2/code/google")
    public ResponseEntity<String> redirectGoogleLogin(
            @RequestParam(value = "code") String authCode,
            HttpSession session
    ) throws JsonProcessingException {
        // HTTP 통신을 위해 RestTemplate 활용
        final RestTemplate restTemplate = new RestTemplate();
        final GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
                .clientId(googleOAuth2Config.getGoogleClientId())
                .clientSecret(googleOAuth2Config.getGoogleSecret())
                .code(authCode)
                .redirectUri(googleOAuth2Config.getGoogleRedirectUrl())
                .grantType("authorization_code")
                .build();

        // Http Header 설정
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
        final ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(googleOAuth2Config.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);

        // ObjectMapper를 통해 String to Object로 변환
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
        GoogleLoginResponse googleLoginResponse = this.objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {
        });

        session.setAttribute(SessionKey.OAUTH.name(), googleLoginResponse);

        return ResponseEntity.badRequest().body("success");
    }

    @GetMapping(value = "/login/info")
    public ResponseEntity<GoogleTokenInfo> getLoginInfo(HttpSession session) throws Exception {
        GoogleLoginResponse googleLoginResponse = (GoogleLoginResponse) session.getAttribute(SessionKey.OAUTH.name());
        // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
        String jwtToken = googleLoginResponse.getIdToken();

        // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
        String requestUrl = UriComponentsBuilder.fromHttpUrl(googleOAuth2Config.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

        // HTTP 통신을 위해 RestTemplate 활용
        final RestTemplate restTemplate = new RestTemplate();
        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        if (resultJson != null) {
            GoogleTokenInfo userInfoDto = this.objectMapper.readValue(resultJson, GoogleTokenInfo.class);

            return ResponseEntity.ok().body(userInfoDto);
        } else {
            throw new Exception("Google OAuth failed!");
        }
    }
}
