package pl.lelenet.dekadapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.lelenet.dekadapi.image.Image;
import pl.lelenet.dekadapi.image.ImageRepository;
import pl.lelenet.dekadapi.product.Product;
import pl.lelenet.dekadapi.product.ProductRepository;
import pl.lelenet.dekadapi.security.AuthToken;
import pl.lelenet.dekadapi.security.AuthTokenRepository;
import pl.lelenet.dekadapi.shop.Shop;
import pl.lelenet.dekadapi.shop.ShopRepository;
import pl.lelenet.dekadapi.user.User;
import pl.lelenet.dekadapi.user.UserRepository;
import pl.lelenet.dekadapi.user.role.UserRoleService;

import java.util.List;

import static pl.lelenet.dekadapi.user.role.UserRoleType.*;

//@Configuration
public class LoremData {
    @Bean
    CommandLineRunner loremImages(ImageRepository repository) {
        return args -> {
            repository.save(new Image(
                    "https://www.wykop.pl/cdn/c3201142/comment_1594576802zjabnefpUayCAwGAhh6p2C.jpg",
                    500L, 100, 100, "jpeg"
            ));
            repository.save(new Image(
                    "https://a.allegroimg.com/s180/11d09e/8ec185d545f58e44251d5e3d09d2/Komputer-Gamingowy-Intel-i7-GTX-1050Ti-16GB-Win10",
                    500L, 100, 100, "jpeg"
            ));
            repository.save(new Image(
                    "https://www.kindpng.com/picc/m/198-1984828_java-icon-transparent-hd-png-download.png",
                    500L, 100, 100, "jpeg"
            ));
            repository.save(new Image(
                    "https://image.flaticon.com/icons/png/512/1404/1404945.png",
                    500L, 100, 100, "png"
            ));
        };
    }

    @Bean
    CommandLineRunner loremShops(ShopRepository repository, ImageRepository imageRepository) {
        return args -> {
            List<Image> images = imageRepository.findAll();

            repository.save(new Shop("Kitku shop", "Sklep z kitkami. ".repeat(20), null));
            repository.save(new Shop("Januszex Digital", "Ja wam dam koparki na bitkojny", null));
            repository.save(new Shop("John Coffee", "Kawa i wogule", images.get(2)));
            repository.save(new Shop("Pizzeria Pizza", "mmmm", images.get(3)));
        };
    }

    @Bean
    CommandLineRunner loremProducts(ProductRepository repository, ShopRepository shopRepository, ImageRepository imageRepository) {
        return args -> {
            List<Shop> shops = shopRepository.findAll();
            List<Image> images = imageRepository.findAll();
            repository.save(new Product(
                    "Test żebrowy", "Sprawdź stan żeber u swojego kitku",
                    images.get(0),
                    2138, 2137,
                    shops.get(0)
            ));
            repository.save(new Product(
                    "Kompjuter Gejmingowy", "Procesor INTEL! Grafika DŻIFORS!",
                    images.get(1),
                    400000, 399999,
                    shops.get(1)
            ));
        };
    }

    @Bean
    CommandLineRunner loremUsers(UserRepository repository, BCryptPasswordEncoder passwordEncoder, UserRoleService roleService) {
        return args -> {
            repository.save(new User(
                    "manager", passwordEncoder.encode("pass"),
                    List.of(roleService.find(ROLE_USER), roleService.find(ROLE_SHOP_EMPLOYEE), roleService.find(ROLE_SHOP_MANAGER)),
                    null
            ));
            repository.save(new User(
                    "employee", passwordEncoder.encode("pass"),
                    List.of(roleService.find(ROLE_USER), roleService.find(ROLE_SHOP_EMPLOYEE)),
                    null
      ));
            repository.save(new User(
                    "user", passwordEncoder.encode("pass"),
                    List.of(roleService.find(ROLE_USER)),
                    null
        ));
        };
    }

    @Bean
    CommandLineRunner loremTokens(AuthTokenRepository tokenRepository, UserRepository userRepository) {
        return args -> {
            User manager = userRepository.findUserByUsername("manager");
            AuthToken managerToken = new AuthToken(manager);
            managerToken.setId("manager");
            tokenRepository.save(managerToken);
            User employee = userRepository.findUserByUsername("employee");
            AuthToken employeeToken = new AuthToken(employee);
            managerToken.setId("employee");
            tokenRepository.save(employeeToken);
            User user = userRepository.findUserByUsername("user");
            AuthToken userToken = new AuthToken(user);
            userToken.setId("user");
            tokenRepository.save(userToken);
            tokenRepository.flush();
        };
    }
}
