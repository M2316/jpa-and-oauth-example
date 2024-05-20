package site.jealth.oauth.springbootoauth2exaple.config.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import site.jealth.oauth.springbootoauth2exaple.domain.User;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()),user);
    }

    //JWT 토큰 생성 메서드
    private String makeToken(Date expiry,User user){
        Date now = new Date();

        Claims claims = Jwts.claims();

        claims.put("id",user.getId());//클레임 id : 유저 ID

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 typ : JWT
                // 내용 iss : application.yml에 저장한 issuer 키의 값이 들어가게 됨
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)           //내용 iat : 현재 시간
                .setExpiration(expiry)      //내용 exp : expiry 멤버 변수값
                .setSubject(user.getEmail())//내용 sub : 유저의 이메일
                .setClaims(claims)
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
    //JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);

        /*
        *
            ROLE_USER: 일반 사용자로, 기본적인 사용 권한을 가집니다. 데이터 조회나 일부 데이터 입력 등의 작업을 수행할 수 있습니다.
            ROLE_ADMIN: 관리자 권한으로, 시스템 설정 변경, 사용자 관리, 보안 정책 설정 등의 고급 작업을 수행할 수 있습니다.
            ROLE_MODERATOR: 중간 관리자 권한으로, 게시판 관리, 댓글 삭제, 사용자 게시물 관리 등 커뮤니티 관련 작업을 수행할 수 있습니다.
            ROLE_SUPERUSER: 최상위 관리자로, 시스템 내 모든 리소스에 대한 접근과 수정 권한을 가집니다. 이 권한은 시스템의 모든 부분을 관리할 수 있으며, 주로 기술적 유지보수나 긴급 상황에서 사용됩니다.
            ROLE_GUEST: 게스트 권한으로, 로그인하지 않은 사용자에게 제한된 접근을 허용합니다. 주로 정보 검색이나 공개된 콘텐츠 열람에 사용됩니다.
            ROLE_AUDITOR: 감사관 권한으로, 로그 기록 검토나 시스템 사용 내역 감사 등, 보안 감사와 관련된 작업을 수행할 수 있습니다.
        * */
        //읽을 사용자의 권한 타입을 정의
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(),"",authorities
                )
                ,token
                ,authorities
            );
    }

    //토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }


    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
