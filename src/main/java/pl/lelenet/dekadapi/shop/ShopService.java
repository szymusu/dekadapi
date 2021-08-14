package pl.lelenet.dekadapi.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lelenet.dekadapi.exception.Http404Exception;
import pl.lelenet.dekadapi.product.Product;

import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getAll() {
        return shopRepository.findAll();
    }

    public Shop getById(Long id) {
        if (shopRepository.existsById(id)) {
            return shopRepository.getById(id);
        }
        else throw new Http404Exception("Shop with id %d not found".formatted(id));
    }
}
