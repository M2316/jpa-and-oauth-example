package site.jealth.oauth.springbootoauth2exaple.dto;

import lombok.Data;
import site.jealth.oauth.springbootoauth2exaple.domain.Article;

@Data
public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse(Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
