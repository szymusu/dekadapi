package pl.lelenet.dekadapi.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.lelenet.dekadapi.dto.ResponseMessage;
import pl.lelenet.dekadapi.exception.Http404Exception;
import pl.lelenet.dekadapi.shop.Shop;
import pl.lelenet.dekadapi.shop.ShopService;
import pl.lelenet.dekadapi.user.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/product", produces = "application/json; charset=utf-8")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final ShopService shopService;

    @Autowired
    public ProductController(ProductService productService, UserService userService, ShopService shopService) {
        this.productService = productService;
        this.userService = userService;
        this.shopService = shopService;
    }

    @GetMapping
    Page<Product> page(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        return productService.getPage(page, size);
    }

    @GetMapping("/{id}")
    Product one(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SHOP_MANAGER', 'ROLE_SHOP_EMPLOYEE')")
    Product add(
            @RequestParam("shopId") long shopId,
            @RequestBody Product product,
            Principal principal
    ) {
        Shop shop = shopService.getById(shopId);
        if (userService.userHasShop(principal.getName(), shop)) {
            product.setShop(shop);
            return productService.add(product);
        }
        else throw new AccessDeniedException("You can't add products for %s".formatted(shop.getName()));
    }

    @ExceptionHandler(Http404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseMessage notFound(Http404Exception e) {
        return new ResponseMessage(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseMessage accessDenied(AccessDeniedException e) {
        return new ResponseMessage(e.getMessage());
    }
}
