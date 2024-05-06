package site.jealth.oauth.springbootoauth2exaple.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

//이 파일은 JWT 토큰 서비스를 테스트하는 데 사용할 모킹(mocking)용 객체입니다.
//테스트를 실행할 때 객체를 대신하는 가짜 객체임...
@Getter
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = Collections.emptyMap();


    //빌더 패턴을 사요ㅕㅇ해 설정이 필요한 데이터만 선택 설정
    @Builder
    public JwtFactory(String subject,Date issuedAt, Date expiration, Map<String,Object> claims){
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = subject != null ? issuedAt : this.issuedAt;
        this.expiration = subject != null ? expiration : this.expiration;
        this.claims = subject != null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues(){
        return JwtFactory.builder().build();
    }

    //jjwt 라이브러리를 사용해 JWT 토큰 생성
    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder()
                .setSubject(subject)
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}
