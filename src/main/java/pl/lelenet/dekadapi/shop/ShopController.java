package pl.lelenet.dekadapi.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.lelenet.dekadapi.dto.ResponseMessage;
import pl.lelenet.dekadapi.exception.Http404Exception;

import java.util.List;

@RestController
@RequestMapping(value = "/shop", produces = "application/json; charset=utf-8")
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping()
    List<Shop> all() {
        return shopService.getAll();
    }

    @GetMapping("/{id}")
    Shop one(@PathVariable Long id) {
        return shopService.getById(id);
    }

    @ExceptionHandler(Http404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseMessage notFound(Http404Exception e) {
        return new ResponseMessage(e.getMessage());
    }
}
