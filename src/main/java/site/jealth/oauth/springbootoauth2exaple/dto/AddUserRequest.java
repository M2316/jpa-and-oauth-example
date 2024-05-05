package site.jealth.oauth.springbootoauth2exaple.dto;

import lombok.Data;

@Data
public class AddUserRequest {
    private String email;
    private String password;
}
