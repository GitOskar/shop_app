package pl.umcs.shop_app.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.security.dto.TokenPairDto;
import pl.umcs.shop_app.security.service.JwtService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.ERROR_OCCURRED_DURING_TOKEN_VERIFYING;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController {

    private static final String BEARER = "Bearer ";

    private final JwtService jwtService;

    @GetMapping("/public/refresh-token")
    public ResponseEntity<TokenPairDto> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(header -> header.startsWith(BEARER))
                .map(header -> header.substring(BEARER.length()))
                .map(jwtService::refreshToken)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ApiException(ERROR_OCCURRED_DURING_TOKEN_VERIFYING));
    }
}
