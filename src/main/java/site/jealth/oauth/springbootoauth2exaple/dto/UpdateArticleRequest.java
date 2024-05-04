package site.jealth.oauth.springbootoauth2exaple.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    private String title;
    private String content;
}
