package site.jealth.oauth.springbootoauth2exaple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.jealth.oauth.springbootoauth2exaple.domain.RefreshToken;
import site.jealth.oauth.springbootoauth2exaple.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected Token"));
    }
}
