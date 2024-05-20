//package site.jealth.oauth.springbootoauth2exaple.config;
//
//import jakarta.servlet.http.HttpServlet;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import site.jealth.oauth.springbootoauth2exaple.service.UserDetailService;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private final UserDetailService userDetailService;
//
//    // 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure(){
//        return (web) -> web.ignoring() // 스프링 시큐리티 예외처리
//                .requestMatchers(toH2Console())
//                .requestMatchers(new AntPathRequestMatcher("/static/**"));
//    }
//
//    //특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .authorizeRequests(auth -> auth // 인증, 인가 설정
//                                .requestMatchers( // 특정 요청과 일치하는 url에 대한 액세스를 설정
//                                        new AntPathRequestMatcher("/login"),
//                                        new AntPathRequestMatcher("/signup"),
//                                        new AntPathRequestMatcher("/user")
//                                )
//                                .permitAll()// 누구나 접근이 가능하도록 설정하는 메서드 (인증/인가 없이도 접근 가능하게 함)
//                                .anyRequest() //위에서 설정한 url이외의 요청에 대해서 설정
//                                .authenticated())// 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근할 수 있음
//                .formLogin(formLogin -> formLogin //폼 기반 로그인 설정
//                        .loginPage("/login") // 로그인 페이지 경롤르 설정
//                        .defaultSuccessUrl("/home") // 로그인이 완료되었을 때 이동할 경로를 설정
//                )
//                .logout(logout -> logout // 로그아웃 설정
//                        .logoutSuccessUrl("/login")// 로그아웃이 완료되었을 때 이동할 경로를 설정
//                        .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정
//                )
//                .csrf(AbstractHttpConfigurer::disable) // csrf공격방지 비활성화 (서비스 시에는 csrf를 활성화 시키는게 좋음)
//                .build();
//
//    }
//
//
//    //인증 관리자 관련 설정
//    // 사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법, 예를 들어 LDAP,JDBC 기반 인증 등을 설정할 때 사용
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
//                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
//                                                       UserDetailService userDetailService) throws Exception{
//
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//
//        // 사용자 정보 서비스 설정
//        // userDetailService() : 사용자 정보를 가져올 서비스를 설정함
//        // 이때 설정하는 서비스 클래스는 반드시 UserDetailsService를 상속받은 클래스여야함
//        authenticationProvider.setUserDetailsService(userDetailService);
//
//        //passwordEncoder() : 비밀번호를 암호화하기 위한 인코더를 설정
//        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//
//
//        return new ProviderManager(authenticationProvider);
//    }
//
//
//    // 패스워드 인코더로 사용할 빈 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
