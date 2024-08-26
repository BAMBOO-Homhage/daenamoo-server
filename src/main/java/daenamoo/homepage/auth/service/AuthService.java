package daenamoo.homepage.auth.service;

import daenamoo.homepage.auth.Token;
import daenamoo.homepage.auth.dto.JwtDto;
import daenamoo.homepage.auth.repository.TokenRepository;
import daenamoo.homepage.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public JwtDto reissueToken(JwtDto jwtDto) {

        log.info("[ Auth Service ] 토큰 재발급을 시작합니다.");
        String accessToken = jwtDto.getAccessToken();
        String refreshToken = jwtDto.getRefreshToken();

        //Access Token 으로부터 사용자 studentId 추출
        String studentId = jwtUtil.getStudendId(refreshToken);
        log.info("[ Auth Service ] StudentId ---> {}", studentId);

        //Access Token 에서의 StudentId 로 부터 DB 에 저장된 Refresh Token 가져오기
        Token refreshTokenByDB = tokenRepository.findByStudentId(studentId).orElseThrow(
                () -> new SecurityException("access token 의 StudentId 로부터 Refresh Token 을 찾을 수 없습니다.")
        );

        //Refresh Token 이 유효한지 검사
        jwtUtil.validateToken(refreshToken);

        log.info("[ Auth Service ] Refresh Token 이 유효합니다.");

        //만약 DB 에서 찾은 Refresh Token 과 파라미터로 온 Refresh Token 이 일치하면 새로운 토큰 발급
        if (refreshTokenByDB.getToken().equals(refreshToken)) {
            log.info("[ Auth Service ] 토큰을 재발급합니다.");
            return jwtUtil.reissueToken(refreshToken);
        } else {
            throw new SecurityException("Refresh Token 이 일치하지 않습니다.");
        }
    }
}
