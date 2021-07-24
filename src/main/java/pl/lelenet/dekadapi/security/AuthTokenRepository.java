package pl.lelenet.dekadapi.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lelenet.dekadapi.user.User;

import java.util.List;

public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    List<AuthToken> findByUser(User user);
}
