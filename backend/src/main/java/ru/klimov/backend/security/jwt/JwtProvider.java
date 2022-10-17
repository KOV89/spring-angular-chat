package ru.klimov.backend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.klimov.backend.exception.JwtAuthenticationException;
import ru.klimov.backend.model.entity.User;
import ru.klimov.backend.service.UserService;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

/**
 * Компонент для создания и валидации jwt токена
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String SECRET = "y3cuyTbF4G5UQXMRV02ZnbjlBNtEI6SEeY8MWhuQpy7X5TTI3orAdfdu6dGzRa6bSjfSAQFIqcGTIzsFKeb85Q==";
    private static final long TOKEN_LIFE = 12*60*60*1000;

    private final UserService userService;

    public @NotNull String createToken(@NotNull User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles());
        claims.put("id", user.getId());

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + TOKEN_LIFE);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(startDate)
                .setExpiration(endDate)
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateToken(@NotNull String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("Срок действия токена истек");
        } catch (Exception e) {
            throw new JwtAuthenticationException(e.getMessage());
        }
    }

    public @NotNull Authentication getAuthentication(@NotNull String token) {
        UserDetails userDetails = userService.findByUsername(getUsername(token));
        if (Objects.nonNull(userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Пользователь с имененем: " + getUsername(token) + " не найден.");
        }
    }

    private @NotNull SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    private @NotNull String getUsername(@NotNull String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
