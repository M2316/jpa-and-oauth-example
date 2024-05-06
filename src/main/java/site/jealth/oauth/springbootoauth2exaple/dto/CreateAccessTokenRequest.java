package site.jealth.oauth.springbootoauth2exaple.dto;

import lombok.Data;

@Data
public class CreateAccessTokenRequest {
    private String refreshToken;
}
