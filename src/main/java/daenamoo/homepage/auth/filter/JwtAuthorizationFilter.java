package daenamoo.homepage.auth.filter;

import daenamoo.homepage.auth.userDetails.CustomUserDetails;
import daenamoo.homepage.auth.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
//    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            //Request 에서 access token 추출
            String accessToken = jwtUtil.resolveAccessToken(request);

            // accessToken 없이 접근할 경우 필터를 건너뜀
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // logout 처리된 accessToken -> 원래는 Redis 에서 해당 토큰이 logout 처리됐는지 검사함, 우리는 Redis 를 다루지 않을 것임.
//            if (redisUtil.get(accessToken) != null && redisUtil.get(accessToken).equals("logout")) {
//                logger.info("[*] Logout accessToken");
//                filterChain.doFilter(request, response);
//                return;
//            }

            authenticateAccessToken(accessToken);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 return
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Access Token 이 만료되었습니다.");
        }
    }

    private void authenticateAccessToken(String accessToken) {

        //AccessToken 유효성 검증
        jwtUtil.validateToken(accessToken);

        //CustomUserDetail 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(
                jwtUtil.getStudendId(accessToken),
                null,
                jwtUtil.getRoles(accessToken)
        );


        // Spring Security 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        // SecurityContextHolder 에 현재 인증 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
