package site.jealth.oauth.springbootoauth2exaple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jealth.oauth.springbootoauth2exaple.domain.Article;

public interface BlogRepository extends JpaRepository<Article,Long> {

}
