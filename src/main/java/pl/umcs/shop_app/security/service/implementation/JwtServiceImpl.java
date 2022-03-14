package pl.umcs.shop_app.security.service.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.security.dto.TokenPairDto;
import pl.umcs.shop_app.security.entity.AppUser;
import pl.umcs.shop_app.security.entity.Role;
import pl.umcs.shop_app.security.properties.SecurityProperties;
import pl.umcs.shop_app.security.repository.AppUserRepository;
import pl.umcs.shop_app.security.service.JwtService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toCollection;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.ERROR_OCCURRED_DURING_TOKEN_VERIFYING;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.USER_NOT_FOUND_EXCEPTION;
import static pl.umcs.shop_app.util.TimeUtil.minutesToMilliSeconds;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String CLAIM_NAME = "roles";

    private final Algorithm jwtAlgorithm;
    private final Long accessTokenDurationMs;
    private final Long refreshTokenDurationMs;
    private final AppUserRepository userRepository;

    public JwtServiceImpl(SecurityProperties securityProperties, AppUserRepository userRepository) {
        this.jwtAlgorithm = Algorithm.HMAC256(securityProperties.getJwtSecret().getBytes());
        this.accessTokenDurationMs = minutesToMilliSeconds(securityProperties.getAccessTokenDurationMinutes());
        this.refreshTokenDurationMs = minutesToMilliSeconds(securityProperties.getRefreshTokenDurationMinutes());
        this.userRepository = userRepository;
    }

    @Override
    public String generateAccessToken(String username, String issuer, List<String> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenDurationMs))
                .withIssuer(issuer)
                .withClaim(CLAIM_NAME, authorities)
                .sign(jwtAlgorithm);
    }

    @Override
    public String generateRefreshToken(String username, String issuer) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenDurationMs))
                .withIssuer(issuer)
                .sign(jwtAlgorithm);
    }

    @Override
    public DecodedJWT decodeToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(jwtAlgorithm).build();
        return jwtVerifier.verify(token);
    }

    @Override
    public Collection<SimpleGrantedAuthority> extractAuthorities(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(CLAIM_NAME).asList(String.class).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toCollection(ArrayList::new));
    }

    @Override
    public TokenPairDto refreshToken(String refreshToken) {

        DecodedJWT decodedJWT;

        try {
            decodedJWT = decodeToken(refreshToken);
        } catch (Exception e) {
            throw new ApiException(ERROR_OCCURRED_DURING_TOKEN_VERIFYING);
        }

        String username = decodedJWT.getSubject();

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND_EXCEPTION));

        ArrayList<String> authorities = user.getRoles().stream().map(Role::getName).collect(toCollection(ArrayList::new));

        String accessToken = generateAccessToken(username, decodedJWT.getIssuer(), authorities);

        return new TokenPairDto(accessToken, refreshToken);
    }
}
