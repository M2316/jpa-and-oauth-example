package site.jealth.oauth.springbootoauth2exaple.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.jealth.oauth.springbootoauth2exaple.domain.Article;
import site.jealth.oauth.springbootoauth2exaple.dto.ArticleRequest;
import site.jealth.oauth.springbootoauth2exaple.dto.ArticleResponse;
import site.jealth.oauth.springbootoauth2exaple.dto.UpdateArticleRequest;
import site.jealth.oauth.springbootoauth2exaple.repository.BlogRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(ArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public ArticleResponse update(long id, UpdateArticleRequest request) {

        Article article = blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(),request.getContent());
        return new ArticleResponse(article);
    }
}
