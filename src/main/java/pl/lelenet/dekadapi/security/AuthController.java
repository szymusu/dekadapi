package pl.lelenet.dekadapi.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lelenet.dekadapi.config.Constants;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @PostMapping("/authorize")
    public Map<String, Object> authorize(HttpSession session) {
        return new HashMap<>() {{
            put(Constants.AUTH_TOKEN_NAME, session.getAttribute(Constants.AUTH_TOKEN_NAME));
        }};
    }

    @GetMapping("/anon")
    @PreAuthorize("isAnonymous()")
    public Map<String, Object> anon() {
        return new HashMap<>() {{
            put(Constants.RESPONSE_BODY_MESSAGE_KEY, "Hej anon");
        }};
    }

    @GetMapping("/log")
    @PreAuthorize("isFullyAuthenticated()")
    public Map<String, Object> logged() {
        return new HashMap<>() {{
            put(Constants.RESPONSE_BODY_MESSAGE_KEY, "logged in!");
        }};
    }

    @GetMapping("/employee")
    @PreAuthorize("hasAnyAuthority('ROLE_SHOP_MANAGER', 'ROLE_SHOP_EMPLOYEE')")
    public Map<String, Object> employee() {
        return new HashMap<>() {{
            put(Constants.RESPONSE_BODY_MESSAGE_KEY, "good job");
        }};
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('ROLE_SHOP_MANAGER')")
    public Map<String, Object> manager() {
        return new HashMap<>() {{
            put(Constants.RESPONSE_BODY_MESSAGE_KEY, "ACCESS GRANTED *hacker music in the background*");
        }};
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, Object> accessDenied() {
        return new HashMap<>() {{
            put(Constants.RESPONSE_BODY_MESSAGE_KEY, "You are not allowed here");
        }};
    }
}
