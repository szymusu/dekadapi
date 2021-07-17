package pl.lelenet.dekadapi.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadLorem {
    @Bean
    CommandLineRunner insertLorem(ProductRepository repository) {
        return args -> repository.save(new Product(
                "Test żebrowy", "Sprawdź stan żeber u swojego kitku",
                "https://www.wykop.pl/cdn/c3201142/comment_1594576802zjabnefpUayCAwGAhh6p2C.jpg",
                2138, 2137
        ));
    }
}
