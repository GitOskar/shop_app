package pl.umcs.shop_app.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.umcs.shop_app.security.dto.TokenPairDto;

import java.util.Collection;
import java.util.List;

public interface JwtService {

    String generateAccessToken(String username, String issuer, List<String> authorities);

    String generateRefreshToken(String username, String issuer);

    DecodedJWT decodeToken(String token);

    Collection<SimpleGrantedAuthority> extractAuthorities(DecodedJWT decodedJWT);

    TokenPairDto refreshToken(String refreshToken);
}