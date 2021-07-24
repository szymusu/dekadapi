package pl.lelenet.dekadapi.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.lelenet.dekadapi.security.AuthEntryPoint;
import pl.lelenet.dekadapi.security.AuthTokenService;
import pl.lelenet.dekadapi.security.TokenAuthFilter;
import pl.lelenet.dekadapi.security.TokenBasicAuthFilter;
import pl.lelenet.dekadapi.user.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final AuthTokenService tokenService;
    private final Gson gson;

    @Autowired
    public SecurityConfig(UserService userService, AuthTokenService tokenService, Gson gson) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.gson = gson;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .userDetailsService(userService)
                .httpBasic().authenticationEntryPoint(new AuthEntryPoint(gson))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        final TokenBasicAuthFilter authFilter = new TokenBasicAuthFilter(authenticationManagerBean(), tokenService);
        http.addFilter(authFilter);

        final TokenAuthFilter tokenAuthFilter = new TokenAuthFilter(tokenService);
        http.addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
