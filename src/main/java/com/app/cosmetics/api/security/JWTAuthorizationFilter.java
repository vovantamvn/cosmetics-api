package com.app.cosmetics.api.security;

import com.app.cosmetics.api.exception.ExpiredException;
import com.auth0.jwt.JWT;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Log4j2
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTConstant.HEADER_STRING);

        if (header == null || !header.startsWith(JWTConstant.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(header);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        String token = header.replace(JWTConstant.TOKEN_PREFIX, "");

        try {
            String username = JWT.require(JWTConstant.ALGORITHM)
                    .build()
                    .verify(token)
                    .getSubject();

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        new ArrayList<>()
                );
            }
        } catch (Exception exception) {
            log.error(exception);
            throw new ExpiredException();
        }

        return null;
    }
}
