package site.jealth.oauth.springbootoauth2exaple.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.jealth.oauth.springbootoauth2exaple.domain.User;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);//email로 사용자 정보를 가져옴
}
