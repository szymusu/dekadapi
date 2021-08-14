package pl.lelenet.dekadapi.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.lelenet.dekadapi.config.Constants;
import pl.lelenet.dekadapi.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenBasicAuthFilter extends BasicAuthenticationFilter {

    private final AuthTokenService tokenService;

    public TokenBasicAuthFilter(AuthenticationManager authenticationManager, AuthTokenService tokenService) {
        super(authenticationManager);
        this.tokenService = tokenService;
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        AuthToken newToken = tokenService.create(user);
        request.getSession().setAttribute(Constants.AUTH_TOKEN_NAME, newToken.getId());
    }
}
