package pl.lelenet.dekadapi.security;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import pl.lelenet.dekadapi.config.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson;

    public AuthEntryPoint(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("Content-Type", "application/json");

        response.addHeader("Access-Control-Allow-Headers", "session");
        response.addHeader("Access-Control-Allow-Methods", "HEAD,GET,PUT,POST,DELETE,PATCH");
        response.addHeader("Access-Control-Allow-Origin", "*");

        response.getWriter().println(gson.toJson(new HashMap<String, Object>() {{
                    put(Constants.RESPONSE_BODY_MESSAGE_KEY, "Invalid Credentials");
                }})
        );
    }
}
