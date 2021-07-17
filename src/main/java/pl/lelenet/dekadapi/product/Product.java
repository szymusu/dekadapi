package pl.lelenet.dekadapi.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imageURL;
    private Integer regularPrice;
    private Integer discountPrice;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getImageURL() { return imageURL; }
    public Integer getRegularPrice() { return regularPrice; }
    public Integer getDiscountPrice() { return discountPrice; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setRegularPrice(Integer regularPrice) {
        this.regularPrice = regularPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Product() { }

    public Product(String name, String description, String imageURL, Integer regularPrice, Integer discountPrice) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.regularPrice = regularPrice;
        this.discountPrice = discountPrice;
    }
}
