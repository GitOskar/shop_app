package pl.umcs.shop_app.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.umcs.shop_app.security.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.ERROR_OCCURRED_DURING_TOKEN_VERIFYING;

@Slf4j
@RequiredArgsConstructor
public class AppAuthorizationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String LOGIN_URL = "/login";
    private static final String PUBLIC_URL = "/public";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (LOGIN_URL.equals(servletPath) || servletPath.startsWith(PUBLIC_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (isNull(authorizationHeader) || !authorizationHeader.startsWith(BEARER)) {
            log.error("No bearer authorization header present");
            setForbiddenResponse(response);
            return;
        }

        try {
            DecodedJWT decodedJWT = jwtService.decodeToken(authorizationHeader.substring(BEARER.length()));
            Collection<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(decodedJWT);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities));
        } catch (Exception e) {
            log.error("Error occurred during decoding JWT token: ", e);
            setForbiddenResponse(response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setForbiddenResponse(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.setStatus(FORBIDDEN.value());
        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("message", ERROR_OCCURRED_DURING_TOKEN_VERIFYING.getMessage()));
    }
}
