package site.jealth.oauth.springbootoauth2exaple.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jealth.oauth.springbootoauth2exaple.domain.Article;
import site.jealth.oauth.springbootoauth2exaple.dto.ArticleRequest;
import site.jealth.oauth.springbootoauth2exaple.dto.ArticleResponse;
import site.jealth.oauth.springbootoauth2exaple.dto.UpdateArticleRequest;
import site.jealth.oauth.springbootoauth2exaple.service.BlogService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody ArticleRequest request){
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){

        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);
        ArticleResponse articleResponse = new ArticleResponse(article);
        return ResponseEntity.status(HttpStatus.OK).body(articleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable long id,
            @RequestBody UpdateArticleRequest request){
        ArticleResponse article = blogService.update(id,request);
        return ResponseEntity.status(HttpStatus.OK).body(article);
    }
}
