package ru.klimov.backend.security.jwt;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.klimov.backend.exception.JwtAuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Фильтр проверяющий наличие токена при всех http запросах и передающий пользователя в контекст
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest((HttpServletRequest) request);
            if (Objects.nonNull(token) && jwtProvider.validateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token));
            }
            fc.doFilter(request, response);
        } catch (JwtAuthenticationException | UsernameNotFoundException e) {
            response.getOutputStream().write(e.getMessage().getBytes());
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public @Nullable String getTokenFromRequest(@NotNull HttpServletRequest request) {
        final String bearer = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(bearer) && bearer.startsWith(TOKEN_PREFIX)) {
            return bearer.substring(7);
        }
        return null;
    }
}