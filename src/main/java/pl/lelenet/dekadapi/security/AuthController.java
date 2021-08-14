package pl.lelenet.dekadapi.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lelenet.dekadapi.config.Constants;
import pl.lelenet.dekadapi.dto.ResponseMessage;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth", produces = "application/json; charset=utf-8")
public class AuthController {

    @PostMapping("/authorize")
    public Map<String, Object> authorize(HttpSession session) {
        return new HashMap<>() {{
            put(Constants.AUTH_TOKEN_NAME, session.getAttribute(Constants.AUTH_TOKEN_NAME));
        }};
    }

    @GetMapping("/anon")
    @PreAuthorize("isAnonymous()")
    public ResponseMessage anon() {
        return new ResponseMessage("hej anon!");
    }

    @GetMapping("/log")
    @PreAuthorize("isFullyAuthenticated()")
    public ResponseMessage logged() {
        return new ResponseMessage("logged in!");
    }

    @GetMapping("/employee")
    @PreAuthorize("hasAnyAuthority('ROLE_SHOP_MANAGER', 'ROLE_SHOP_EMPLOYEE')")
    public ResponseMessage employee() {
        return new ResponseMessage("good job");
    }

    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('ROLE_SHOP_MANAGER')")
    public ResponseMessage manager() {
        return new ResponseMessage("ACCESS GRANTED *hacker music in the background*");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseMessage accessDenied() {
        return new ResponseMessage("You are not allowed here");
    }

    @ExceptionHandler(Exception.class)
    public ResponseMessage notGood(Exception e) {
        return new ResponseMessage(e.getClass().getName());
    }
}
