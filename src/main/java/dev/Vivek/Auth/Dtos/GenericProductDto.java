package dev.Vivek.Auth.Dtos;


import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class GenericProductDto {
    private Long id;
    private String title;
    private int price;
    private String description;
    private String image;
    private int stock;


    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public String getTitle() {
        return this.title;
    }

    @Generated
    public int getPrice() {
        return this.price;
    }

    @Generated
    public String getDescription() {
        return this.description;
    }


    @Generated
    public String getImage() {
        return this.image;
    }

    @Generated
    public int getStock() {
        return this.stock;
    }

    @Generated
    public void setId(final Long id) {
        this.id = id;
    }

    @Generated
    public void setTitle(final String title) {
        this.title = title;
    }

    @Generated
    public void setPrice(final int price) {
        this.price = price;
    }

    @Generated
    public void setDescription(final String description) {
        this.description = description;
    }



    @Generated
    public void setImage(final String image) {
        this.image = image;
    }

    @Generated
    public void setStock(final int stock) {
        this.stock = stock;
    }
}