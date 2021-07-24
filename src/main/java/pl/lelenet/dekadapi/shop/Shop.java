package pl.lelenet.dekadapi.shop;

import pl.lelenet.dekadapi.image.Image;

import javax.persistence.*;

@Entity
public class Shop {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(targetEntity = Image.class)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Image getImage() { return image; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Shop() { }

    public Shop(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
