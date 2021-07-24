package pl.lelenet.dekadapi.security;

import pl.lelenet.dekadapi.user.User;

import java.util.List;
import java.util.Optional;

public interface AuthTokenService {
    void updateTimestamp(AuthToken token);
    void delete(AuthToken token);
    boolean isExpired(AuthToken token);
    AuthToken create(User user);
    Optional<AuthToken> findById(String id);
    List<AuthToken> findByUser(User user);
}
