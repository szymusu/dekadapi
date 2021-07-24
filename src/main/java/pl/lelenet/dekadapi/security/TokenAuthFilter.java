package pl.lelenet.dekadapi.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.lelenet.dekadapi.config.Constants;
import pl.lelenet.dekadapi.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthFilter implements Filter {

    private final AuthTokenService tokenService;

    public TokenAuthFilter(AuthTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        validateToken(request.getHeader(Constants.AUTH_TOKEN_NAME));

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void validateToken(String tokenString) {
        if (tokenString == null) return;

        Optional<AuthToken> tokenRecord = tokenService.findById(tokenString);
        if (tokenRecord.isEmpty()) return;

        AuthToken token = tokenRecord.get();
        if (tokenService.isExpired(token)) {

            tokenService.delete(token);
            return;
        }

        final User principal = token.getUser();
        var authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        tokenService.updateTimestamp(token);
    }
}
