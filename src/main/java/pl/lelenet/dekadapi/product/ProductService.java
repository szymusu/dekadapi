package pl.lelenet.dekadapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.lelenet.dekadapi.exception.Http404Exception;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> getPage(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Product getById(Long id) {
        if (productRepository.existsById(id)) {
            return productRepository.getById(id);
        }
        else throw new Http404Exception("Product with id %d not found".formatted(id));
    }

    public Product add(Product product) {
        productRepository.save(product);
        return product;
    }
}
