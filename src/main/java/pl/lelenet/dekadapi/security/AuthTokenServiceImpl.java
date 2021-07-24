package pl.lelenet.dekadapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lelenet.dekadapi.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository repository;

    @Autowired
    public AuthTokenServiceImpl(AuthTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateTimestamp(AuthToken token) {
        token.setLastAccessTime(LocalDateTime.now());
        repository.save(token);
    }

    @Override
    public void delete(AuthToken token) {
        repository.delete(token);
    }

    @Override
    public boolean isExpired(AuthToken token) {
        return LocalDateTime.now().isAfter(token.getExpireTime());
    }

    @Override
    public AuthToken create(User user) {
        AuthToken token = new AuthToken(user);
        return repository.saveAndFlush(token);
    }

    @Override
    public Optional<AuthToken> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<AuthToken> findByUser(User user) {
        return repository.findByUser(user);
    }
}
