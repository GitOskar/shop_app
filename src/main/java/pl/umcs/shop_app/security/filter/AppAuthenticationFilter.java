package pl.umcs.shop_app.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.umcs.shop_app.security.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user.getUsername(),
                request.getRequestURL().toString(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toCollection((Supplier<ArrayList<String>>) ArrayList::new)));

        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), request.getRequestURL().toString());

        new ObjectMapper().writeValue(response.getOutputStream(), Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }
}
