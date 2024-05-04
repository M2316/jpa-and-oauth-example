package site.jealth.oauth.springbootoauth2exaple.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.jealth.oauth.springbootoauth2exaple.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleRequest {
    private String title;
    private String content;

    public Article toEntity(){
        return Article.builder()
            .title(title)
            .content(content)
            .build();
    }
}
