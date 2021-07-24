package pl.lelenet.dekadapi.product;

import pl.lelenet.dekadapi.image.Image;
import pl.lelenet.dekadapi.shop.Shop;

import javax.persistence.*;

@Entity
public class Product {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(targetEntity = Image.class)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    private Integer regularPrice;
    private Integer discountPrice;
    @ManyToOne(targetEntity = Shop.class)
    @JoinColumn(name = "shop_id", referencedColumnName = "id", updatable = false)
    private Shop shop;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Image getImage() { return image; }
    public Integer getRegularPrice() { return regularPrice; }
    public Integer getDiscountPrice() { return discountPrice; }
    public Shop getShop() { return shop; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Product() { }

    public Product(String name, String description, Image image, Integer regularPrice, Integer discountPrice, Shop shop) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.regularPrice = regularPrice;
        this.discountPrice = discountPrice;
        this.shop = shop;
    }
}
