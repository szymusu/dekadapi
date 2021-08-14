package pl.lelenet.dekadapi.security;

import org.hibernate.annotations.GenericGenerator;
import pl.lelenet.dekadapi.config.Constants;
import pl.lelenet.dekadapi.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuthToken {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private LocalDateTime lastAccessTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public AuthToken() { }

    public AuthToken(User user) {
        this.user = user;
    }

    @PrePersist
    public void onPrePersist() {
        lastAccessTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(LocalDateTime lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpireTime() {
        return lastAccessTime.plusDays(Constants.TOKEN_LIFETIME_DAYS);
    }
}
