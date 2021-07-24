package pl.lelenet.dekadapi.image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {
    @Id @GeneratedValue
    private Long id;
    private String url;
    private Long fileSize;
    private Integer width;
    private Integer height;
    private String format;

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getFormat() {
        return format;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Image() { }

    public Image(String url, Long fileSize, Integer width, Integer height, String format) {
        this.url = url;
        this.fileSize = fileSize;
        this.width = width;
        this.height = height;
        this.format = format;
    }
}
