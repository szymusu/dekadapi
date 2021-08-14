package pl.lelenet.dekadapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lelenet.dekadapi.shop.Shop;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("There's no user with username " + s);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean userHasShop(String username, Shop shop) {
        final User user = getUserByUsername(username);
        return user.getShops().contains(shop);
    }
}
