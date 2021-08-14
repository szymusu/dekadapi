package pl.lelenet.dekadapi.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.lelenet.dekadapi.shop.Shop;
import pl.lelenet.dekadapi.user.role.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue()
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<UserRole> roles;

    @ManyToMany(targetEntity = Shop.class)
    @JoinTable(name = "shop_employees",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id")
    )
    private List<Shop> shops;

    public User() {
        roles = new ArrayList<>();
        shops = new ArrayList<>();
    }

    public User(String username, String password, List<UserRole> roles, List<Shop> shops) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.shops = shops;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public List<Shop> getShops() {
        return shops;
    }
}
