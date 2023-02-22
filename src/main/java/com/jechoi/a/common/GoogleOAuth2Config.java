package com.jechoi.a.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Component
public class GoogleOAuth2Config {
    @Value("${oauth2.google.url}")
    private String googleAuthUrl;

    @Value("${oauth2.google.login-url}")
    private String googleLoginUrl;

    @Value("${oauth2.google.redirect-uri}")
    private String googleRedirectUrl;

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleSecret;

    @Value("${oauth2.google.scope}")
    private String scopes;

    public String getGoogleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getGoogleClientId());
        params.put("redirect_uri", getGoogleRedirectUrl());
        params.put("response_type", "code%20");
        params.put("scope", getScopes().replaceAll(",", "%20"));
        // refresh-token을 매로그인마다 받기 위함.
        // 참고: https://hyeonic.github.io/woowacourse/dallog/google-refresh-token.html#%E1%84%84%E1%85%A9-%E1%84%83%E1%85%A1%E1%84%89%E1%85%B5-refresh-token%E1%84%8B%E1%85%A6-%E1%84%8E%E1%85%A2%E1%84%8B%E1%85%AF%E1%84%8C%E1%85%B5%E1%86%AB-null
        params.put("access_type", "offline");
        params.put("prompt", "consent");

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getGoogleLoginUrl()
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr;
    }
}
